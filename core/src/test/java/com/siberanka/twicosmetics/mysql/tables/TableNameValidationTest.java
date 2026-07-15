package com.siberanka.twicosmetics.mysql.tables;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TableNameValidationTest {
    @Test
    void acceptsLegacyNamesAndRejectsSqlSyntax() {
        assertDoesNotThrow(() -> table("UltraCosmetics-PlayerData"));
        assertDoesNotThrow(() -> table("twi_cosmetics"));
        assertThrows(IllegalArgumentException.class, () -> table("profiles`; DROP TABLE users; --"));
    }

    private Table table(String name) {
        return new Table(null, name) {
            @Override
            public void setupTableInfo() {
            }
        };
    }
}
