package com.siberanka.twicosmetics.player.profile;

import com.siberanka.twicosmetics.TwiCosmetics;
import com.siberanka.twicosmetics.util.SmartLogger;

import java.time.Duration;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Coalescing, single-writer service for player YAML files.
 */
public final class ProfileSaveService implements AutoCloseable {
    private final TwiCosmetics plugin;
    private final Map<UUID, PlayerData> pending = new ConcurrentHashMap<>();
    private final AtomicBoolean draining = new AtomicBoolean();
    private final ExecutorService writer = Executors.newSingleThreadExecutor(runnable -> {
        Thread thread = new Thread(runnable, "TwiCosmetics-profile-writer");
        thread.setDaemon(true);
        return thread;
    });

    public ProfileSaveService(TwiCosmetics plugin) {
        this.plugin = plugin;
    }

    public void queue(PlayerData data) {
        pending.put(data.getUUID(), data.copy());
        scheduleDrain();
    }

    private void scheduleDrain() {
        if (draining.compareAndSet(false, true)) writer.execute(this::drain);
    }

    private void drain() {
        try {
            PlayerData snapshot;
            while ((snapshot = poll()) != null) {
                try {
                    snapshot.saveToFile();
                } catch (RuntimeException exception) {
                    plugin.getSmartLogger().write(SmartLogger.LogLevel.ERROR,
                            "Failed to save profile " + snapshot.getUUID(), exception);
                }
            }
        } finally {
            draining.set(false);
            if (!pending.isEmpty()) scheduleDrain();
        }
    }

    private PlayerData poll() {
        for (Map.Entry<UUID, PlayerData> entry : pending.entrySet()) {
            if (pending.remove(entry.getKey(), entry.getValue())) return entry.getValue();
        }
        return null;
    }

    public boolean flush(Duration timeout) {
        scheduleDrain();
        long deadline = System.nanoTime() + timeout.toNanos();
        while ((!pending.isEmpty() || draining.get()) && System.nanoTime() < deadline) {
            Thread.onSpinWait();
            try {
                Thread.sleep(5);
            } catch (InterruptedException exception) {
                Thread.currentThread().interrupt();
                return false;
            }
        }
        return pending.isEmpty() && !draining.get();
    }

    @Override
    public void close() {
        if (!flush(Duration.ofSeconds(10))) {
            plugin.getSmartLogger().write(SmartLogger.LogLevel.WARNING,
                    "Timed out while flushing player profile saves.");
        }
        writer.shutdown();
        try {
            if (!writer.awaitTermination(2, TimeUnit.SECONDS)) writer.shutdownNow();
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
            writer.shutdownNow();
        }
    }
}
