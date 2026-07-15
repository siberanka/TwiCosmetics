package com.siberanka.twicosmetics.menu;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CosmeticsInventoryHolderTest {
    @Test
    void sessionsAreOwnedUniqueAndInvalidatable() {
        UUID owner = UUID.randomUUID();
        CosmeticsInventoryHolder first = new CosmeticsInventoryHolder(owner);
        CosmeticsInventoryHolder second = new CosmeticsInventoryHolder(owner);
        assertTrue(first.belongsTo(owner));
        assertFalse(first.belongsTo(UUID.randomUUID()));
        assertNotEquals(first.getSessionId(), second.getSessionId());
        first.invalidate();
        assertFalse(first.isActive());
    }
}
