package com.siberanka.twicosmetics.events.loot;

import com.siberanka.twicosmetics.cosmetics.type.GadgetType;
import com.siberanka.twicosmetics.player.UltraPlayer;
import com.siberanka.twicosmetics.treasurechests.TreasureChest;
import com.siberanka.twicosmetics.treasurechests.loot.AmmoLoot;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class UCAmmoRewardEvent extends UCTreasureRewardEvent {
    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlerList() {
        return handlers;
    }

    private final GadgetType gadget;
    private int ammo;

    public UCAmmoRewardEvent(UltraPlayer player, TreasureChest chest, AmmoLoot loot, GadgetType gadget, int ammo) {
        super(player, chest, loot);
        this.gadget = gadget;
        this.ammo = ammo;
    }

    @Override
    public AmmoLoot getLoot() {
        return (AmmoLoot) loot;
    }

    public GadgetType getGadget() {
        return gadget;
    }

    public int getAmmo() {
        return ammo;
    }

    public void setAmmo(int ammo) {
        this.ammo = ammo;
    }

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
}
