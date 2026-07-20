package com.siberanka.twicosmetics.player.profile;

import com.siberanka.twicosmetics.TwiCosmetics;
import com.siberanka.twicosmetics.util.SmartLogger;

import java.time.Duration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * Coalescing, single-writer service for player YAML files.
 *
 * <p>The state monitor is also the shutdown barrier. This avoids polling on the
 * server thread and makes it impossible for a save accepted before shutdown to
 * be missed by the final flush.</p>
 */
public final class ProfileSaveService implements AutoCloseable {
    private static final Duration SHUTDOWN_TIMEOUT = Duration.ofSeconds(30);

    private final TwiCosmetics plugin;
    private final Consumer<PlayerData> persister;
    private final Object stateMonitor = new Object();
    private final Map<UUID, PlayerData> pending = new HashMap<>();
    private final ExecutorService writer = Executors.newSingleThreadExecutor(runnable -> {
        Thread thread = new Thread(runnable, "TwiCosmetics-profile-writer");
        thread.setDaemon(true);
        return thread;
    });

    private boolean workerScheduled;
    private boolean closed;
    private UUID activeProfile;

    public ProfileSaveService(TwiCosmetics plugin) {
        this(plugin, PlayerData::saveToFile);
    }

    ProfileSaveService(TwiCosmetics plugin, Consumer<PlayerData> persister) {
        this.plugin = plugin;
        this.persister = persister;
    }

    /**
     * Saves only the newest snapshot for a player when multiple updates arrive
     * before the writer reaches that player.
     */
    public boolean queue(PlayerData data) {
        PlayerData snapshot = data.copy();
        synchronized (stateMonitor) {
            if (closed) return false;
            pending.put(snapshot.getUUID(), snapshot);
            scheduleWorkerLocked();
            return true;
        }
    }

    private void scheduleWorkerLocked() {
        if (closed || workerScheduled || pending.isEmpty()) return;
        workerScheduled = true;
        writer.execute(this::drain);
    }

    private void drain() {
        try {
            while (true) {
                PlayerData snapshot;
                synchronized (stateMonitor) {
                    snapshot = pollLocked();
                    if (snapshot == null) return;
                    activeProfile = snapshot.getUUID();
                }

                try {
                    persister.accept(snapshot);
                } catch (RuntimeException exception) {
                    log(SmartLogger.LogLevel.ERROR,
                            "Failed to save profile " + snapshot.getUUID(), exception);
                } finally {
                    synchronized (stateMonitor) {
                        activeProfile = null;
                    }
                }
            }
        } finally {
            synchronized (stateMonitor) {
                workerScheduled = false;
                // A queue call can replace a snapshot while the previous one is
                // being written. Schedule exactly one follow-up worker for it.
                scheduleWorkerLocked();
                stateMonitor.notifyAll();
            }
        }
    }

    private PlayerData pollLocked() {
        Iterator<Map.Entry<UUID, PlayerData>> iterator = pending.entrySet().iterator();
        if (!iterator.hasNext()) return null;
        PlayerData snapshot = iterator.next().getValue();
        iterator.remove();
        return snapshot;
    }

    public boolean flush(Duration timeout) {
        long remainingNanos = timeout.toNanos();
        long deadline = System.nanoTime() + remainingNanos;
        synchronized (stateMonitor) {
            scheduleWorkerLocked();
            while (!pending.isEmpty() || workerScheduled) {
                if (remainingNanos <= 0) return false;
                try {
                    TimeUnit.NANOSECONDS.timedWait(stateMonitor, remainingNanos);
                } catch (InterruptedException exception) {
                    Thread.currentThread().interrupt();
                    return false;
                }
                remainingNanos = deadline - System.nanoTime();
            }
            return true;
        }
    }

    @Override
    public void close() {
        boolean flushed = closeAndFlush(SHUTDOWN_TIMEOUT);

        writer.shutdown();
        if (!flushed) {
            String detail;
            synchronized (stateMonitor) {
                detail = "pending=" + pending.size()
                        + (activeProfile == null ? "" : ", active=" + activeProfile);
            }
            log(SmartLogger.LogLevel.WARNING,
                    "Timed out while flushing player profile saves (" + detail + ").", null);
            writer.shutdownNow();
            return;
        }

        try {
            if (!writer.awaitTermination(2, TimeUnit.SECONDS)) writer.shutdownNow();
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
            writer.shutdownNow();
        }
    }

    private boolean closeAndFlush(Duration timeout) {
        long remainingNanos = timeout.toNanos();
        long deadline = System.nanoTime() + remainingNanos;
        synchronized (stateMonitor) {
            if (closed) return true;
            scheduleWorkerLocked();
            while (!pending.isEmpty() || workerScheduled) {
                if (remainingNanos <= 0) {
                    closed = true;
                    return false;
                }
                try {
                    TimeUnit.NANOSECONDS.timedWait(stateMonitor, remainingNanos);
                } catch (InterruptedException exception) {
                    Thread.currentThread().interrupt();
                    closed = true;
                    return false;
                }
                remainingNanos = deadline - System.nanoTime();
            }
            // queue() uses the same monitor, so no save can slip between the
            // idle observation and this lifecycle transition.
            closed = true;
            return true;
        }
    }

    private void log(SmartLogger.LogLevel level, String message, RuntimeException exception) {
        // The package-private constructor deliberately permits a null plugin for
        // deterministic unit tests of the concurrency boundary.
        if (plugin == null) return;
        if (exception == null) {
            plugin.getSmartLogger().write(level, message);
        } else {
            plugin.getSmartLogger().write(level, message, exception);
        }
    }
}
