package com.siberanka.twicosmetics.events.loot;

import com.siberanka.twicosmetics.cosmetics.Category;
import com.siberanka.twicosmetics.cosmetics.type.CosmeticType;
import com.siberanka.twicosmetics.player.UltraPlayer;
import com.siberanka.twicosmetics.treasurechests.TreasureChest;
import com.siberanka.twicosmetics.treasurechests.loot.CosmeticLoot;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class UCCosmeticRewardEvent extends UCTreasureRewardEvent {
    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlerList() {
        return handlers;
    }

    private final CosmeticType<?> cosmetic;

    public UCCosmeticRewardEvent(UltraPlayer player, TreasureChest chest, CosmeticLoot loot, CosmeticType<?> cosmetic) {
        super(player, chest, loot);
        this.cosmetic = cosmetic;
    }

    @Override
    public CosmeticLoot getLoot() {
        return (CosmeticLoot) loot;
    }

    /**
     * Convenience
     *
     * @return the category of the given cosmetic
     */
    public Category getCategory() {
        return cosmetic.getCategory();
    }

    /**
     * @return the given cosmetic
     */
    public CosmeticType<?> getCosmetic() {
        return cosmetic;
    }

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
}
