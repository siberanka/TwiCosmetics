package com.siberanka.twicosmetics.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

public class CooldownMap<K> {
    private final long delay;
    private final Map<K, Long> cooldowns = new ConcurrentHashMap<>();

    public CooldownMap(long delayMs) {
        this.delay = delayMs;
    }

    public boolean isOnCooldown(K key) {
        Long expiry = cooldowns.get(key);
        if (expiry == null) return false;
        if (expiry > System.currentTimeMillis()) return true;
        cooldowns.remove(key, expiry);
        return false;
    }

    public void setCooldown(K key) {
        cooldowns.put(key, System.currentTimeMillis() + delay);
    }

    public boolean tryAcquire(K key) {
        long now = System.currentTimeMillis();
        long next = now + delay;
        AtomicBoolean acquired = new AtomicBoolean();
        cooldowns.compute(key, (ignored, expiry) -> {
            if (expiry == null || expiry <= now) {
                acquired.set(true);
                return next;
            }
            return expiry;
        });
        return acquired.get();
    }

    public void remove(K key) {
        cooldowns.remove(key);
    }
}
