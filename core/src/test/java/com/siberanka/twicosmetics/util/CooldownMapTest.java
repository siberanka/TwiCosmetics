package com.siberanka.twicosmetics.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CooldownMapTest {
    @Test
    void acquireIsAtomicAndRemovable() {
        CooldownMap<String> cooldowns = new CooldownMap<>(1_000);
        assertTrue(cooldowns.tryAcquire("player"));
        assertFalse(cooldowns.tryAcquire("player"));
        cooldowns.remove("player");
        assertTrue(cooldowns.tryAcquire("player"));
    }
}
