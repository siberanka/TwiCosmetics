package com.siberanka.twicosmetics.mysql;

import com.siberanka.twicosmetics.TwiCosmetics;
import com.siberanka.twicosmetics.cosmetics.Category;
import com.siberanka.twicosmetics.cosmetics.type.CosmeticType;
import com.siberanka.twicosmetics.cosmetics.type.GadgetType;
import com.siberanka.twicosmetics.cosmetics.type.PetType;
import com.siberanka.twicosmetics.player.UltraPlayer;
import com.siberanka.twicosmetics.player.profile.CosmeticsProfile;
import com.siberanka.twicosmetics.player.profile.ProfileKey;
import com.tcoded.folialib.wrapper.task.WrappedTask;

import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.time.Duration;

/**
 * Package: com.siberanka.twicosmetics.mysql
 * Created by: sacha
 * Date: 15/08/15
 * Project: TwiCosmetics
 */
public class SqlCache extends CosmeticsProfile {
    private final MySqlConnectionManager sql;
    private final Queue<Runnable> queue = new ConcurrentLinkedQueue<>();
    private volatile WrappedTask updateTask = null;
    private final ReentrantLock drainLock = new ReentrantLock();

    public SqlCache(UltraPlayer ultraPlayer, TwiCosmetics ultraCosmetics) {
        super(ultraPlayer, ultraCosmetics);
        this.sql = ultraCosmetics.getMySqlConnectionManager();
    }

    @Override
    public void load() {
        data.loadFromSQL();
    }

    // Saved on write
    @Override
    public void save() {
    }

    @Override
    public void setEnabledCosmetic(Category cat, CosmeticType<?> type) {
        // Primary use: if we're setting it to null and it's already null, skip
        if (data.getEnabledCosmetics().get(cat) == type) return;
        super.setEnabledCosmetic(cat, type);
        if (sql.getEquippedTable() == null) return;
        if (type == null) {
            queueUpdate(() -> sql.getEquippedTable().unsetEquipped(uuid, cat));
        } else {
            queueUpdate(() -> sql.getEquippedTable().setEquipped(uuid, type));
        }
    }

    @Override
    public void clearAllEquipped() {
        super.clearAllEquipped();
        if (sql.getEquippedTable() == null) return;
        queueUpdate(() -> sql.getEquippedTable().clearAllEquipped(uuid));
    }

    @Override
    public void setAmmo(GadgetType type, int amount) {
        super.setAmmo(type, amount);
        if (sql.getAmmoTable() == null) return;
        queueUpdate(() -> sql.getAmmoTable().setAmmo(uuid, type, amount));
    }

    @Override
    public void setPetName(PetType type, String name) {
        super.setPetName(type, name);
        if (sql.getPetNames() == null) return;
        queueUpdate(() -> sql.getPetNames().setPetName(uuid, type, name));
    }

    @Override
    public void setKeys(int amount) {
        super.setKeys(amount);
        queueUpdate(() -> sql.getPlayerData().setKeys(uuid, amount));
    }

    @Override
    public void setGadgetsEnabled(boolean gadgetsEnabled) {
        // If the value did not change, skip the update.
        if (gadgetsEnabled == hasGadgetsEnabled()) return;
        super.setGadgetsEnabled(gadgetsEnabled);
        queueUpdate(() -> sql.getPlayerData().setSetting(uuid, ProfileKey.GADGETS_ENABLED, gadgetsEnabled));
    }

    @Override
    public void setSeeSelfMorph(boolean seeSelfMorph) {
        super.setSeeSelfMorph(seeSelfMorph);
        queueUpdate(() -> sql.getPlayerData().setSetting(uuid, ProfileKey.MORPH_VIEW, seeSelfMorph));
    }

    @Override
    public void setTreasureNotifications(boolean treasureNotifications) {
        super.setTreasureNotifications(treasureNotifications);
        queueUpdate(() -> sql.getPlayerData().setSetting(uuid, ProfileKey.TREASURE_NOTIFICATION, treasureNotifications));
    }

    @Override
    public void setFilterByOwned(boolean filterByOwned) {
        super.setFilterByOwned(filterByOwned);
        queueUpdate(() -> sql.getPlayerData().setSetting(uuid, ProfileKey.FILTER_OWNED, filterByOwned));
    }

    @Override
    public void setUnlocked(Set<CosmeticType<?>> types) {
        super.setUnlocked(types);
        if (sql.getUnlockedTable() == null) return;
        queueUpdate(() -> sql.getUnlockedTable().setUnlocked(uuid, types));
    }

    @Override
    public void setLocked(Set<CosmeticType<?>> types) {
        super.setLocked(types);
        if (sql.getUnlockedTable() == null) return;
        queueUpdate(() -> sql.getUnlockedTable().unsetUnlocked(uuid, types));
    }

    /**
     * This function runs SQl queries asynchronously
     *
     * @param update The function to run
     */
    private void queueUpdate(Runnable update) {
        // Ensure we "lock" the queue while we're adding to it AND checking if the update task is running.
        synchronized (queue) {
            queue.add(update);
            scheduleUpdateLocked();
        }
    }

    private void scheduleUpdateLocked() {
        if (updateTask != null) return;
        updateTask = ultraCosmetics.getScheduler().runLaterAsync(() -> {
            drainQueue();
            synchronized (queue) {
                updateTask = null;
                if (!queue.isEmpty()) scheduleUpdateLocked();
            }
        }, 1); // Batch changes made in the same tick without holding a lock during I/O.
    }

    private void drainQueue() {
        drainLock.lock();
        try {
            Runnable next;
            while ((next = queue.poll()) != null) {
                try {
                    next.run();
                } catch (RuntimeException exception) {
                    ultraCosmetics.getSmartLogger().write(
                            com.siberanka.twicosmetics.util.SmartLogger.LogLevel.ERROR,
                            "Failed to persist SQL profile update for " + uuid, exception);
                }
            }
        } finally {
            drainLock.unlock();
        }
    }

    public boolean flush(Duration timeout) {
        WrappedTask scheduled = updateTask;
        try {
            if (scheduled != null) scheduled.cancel();
        } catch (IllegalStateException ignored) {
        }
        try {
            if (!drainLock.tryLock(timeout.toMillis(), TimeUnit.MILLISECONDS)) return false;
            try {
                Runnable next;
                while ((next = queue.poll()) != null) {
                    try {
                        next.run();
                    } catch (RuntimeException exception) {
                        ultraCosmetics.getSmartLogger().write(
                                com.siberanka.twicosmetics.util.SmartLogger.LogLevel.ERROR,
                                "Failed to flush SQL profile update for " + uuid, exception);
                    }
                }
                updateTask = null;
                return true;
            } finally {
                drainLock.unlock();
            }
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
            return false;
        }
    }
}
