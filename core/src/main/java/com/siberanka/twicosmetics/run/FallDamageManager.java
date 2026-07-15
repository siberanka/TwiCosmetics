package com.siberanka.twicosmetics.run;

import com.siberanka.twicosmetics.TwiCosmeticsData;
import com.tcoded.folialib.wrapper.task.WrappedTask;
import org.bukkit.entity.Entity;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Tracks temporary fall-damage immunity on the owning entity scheduler.
 */
public final class FallDamageManager {
    private static final int WAITING_FOR_GROUND = Integer.MAX_VALUE;
    private static final int COUNTDOWN_TICKS = 5;
    private static final Map<UUID, Integer> PROTECTED = new ConcurrentHashMap<>();

    private FallDamageManager() {
    }

    public static void addNoFall(Entity entity) {
        UUID uuid = entity.getUniqueId();
        Integer previous = PROTECTED.put(uuid, WAITING_FOR_GROUND);
        if (previous != null) return;

        WrappedTask[] task = {null};
        task[0] = TwiCosmeticsData.get().getPlugin().getScheduler().runAtEntityTimer(entity, () -> {
            Integer state = PROTECTED.get(uuid);
            if (state == null || !entity.isValid()) {
                PROTECTED.remove(uuid);
                task[0].cancel();
                return;
            }
            if (state == WAITING_FOR_GROUND) {
                if (entity.isOnGround()) PROTECTED.put(uuid, COUNTDOWN_TICKS);
                return;
            }
            if (state <= 0) {
                PROTECTED.remove(uuid);
                task[0].cancel();
                return;
            }
            PROTECTED.put(uuid, state - 1);
        }, 1, 1);
    }

    public static boolean shouldBeProtected(Entity entity) {
        return PROTECTED.containsKey(entity.getUniqueId());
    }

    public static void clear() {
        PROTECTED.clear();
    }
}
