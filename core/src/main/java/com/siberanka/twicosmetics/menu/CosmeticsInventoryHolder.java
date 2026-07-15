package com.siberanka.twicosmetics.menu;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class CosmeticsInventoryHolder implements InventoryHolder {
    private static final long MIN_INTERACTION_INTERVAL_NANOS = 75_000_000L;

    private final UUID sessionId = UUID.randomUUID();
    private final UUID ownerId;
    private final Map<Integer, MenuEntry> buttons = new HashMap<>();
    private Inventory inventory;
    private boolean active = true;
    private long lastInteractionNanos;

    public CosmeticsInventoryHolder(UUID ownerId) {
        this.ownerId = ownerId;
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public UUID getSessionId() {
        return sessionId;
    }

    public boolean belongsTo(UUID playerId) {
        return ownerId.equals(playerId);
    }

    public boolean isActive() {
        return active;
    }

    public void bind(int slot, ItemStack expectedItem, Button button) {
        if (!active) {
            throw new IllegalStateException("Cannot bind a button to an inactive menu session");
        }
        buttons.put(slot, new MenuEntry(expectedItem.clone(), button));
    }

    public Button resolve(int rawSlot, ItemStack actualItem) {
        if (!active || actualItem == null) return null;
        MenuEntry entry = buttons.get(rawSlot);
        if (entry == null || !entry.expectedItem().equals(actualItem)) return null;
        return entry.button();
    }

    public boolean tryInteract() {
        long now = System.nanoTime();
        if (now - lastInteractionNanos < MIN_INTERACTION_INTERVAL_NANOS) return false;
        lastInteractionNanos = now;
        return true;
    }

    public void invalidate() {
        active = false;
        buttons.clear();
        inventory = null;
    }

    private record MenuEntry(ItemStack expectedItem, Button button) {
    }
}
