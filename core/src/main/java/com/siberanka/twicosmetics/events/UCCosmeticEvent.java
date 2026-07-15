package com.siberanka.twicosmetics.events;

import com.siberanka.twicosmetics.cosmetics.Category;
import com.siberanka.twicosmetics.cosmetics.Cosmetic;
import com.siberanka.twicosmetics.cosmetics.type.CosmeticType;
import com.siberanka.twicosmetics.player.UltraPlayer;

public abstract class UCCosmeticEvent extends UCEvent {

    private final Cosmetic<?> cosmetic;

    public UCCosmeticEvent(UltraPlayer player, Cosmetic<?> cosmetic) {
        super(player);
        this.cosmetic = cosmetic;
    }

    public Cosmetic<?> getCosmetic() {
        return cosmetic;
    }

    public CosmeticType<?> getCosmeticType() {
        return cosmetic.getType();
    }

    public Category getCategory() {
        return cosmetic.getCategory();
    }
}
