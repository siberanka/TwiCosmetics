package com.siberanka.twicosmetics.cosmetics.hats;

import com.siberanka.twicosmetics.TwiCosmetics;
import com.siberanka.twicosmetics.cosmetics.ArmorCosmetic;
import com.siberanka.twicosmetics.cosmetics.suits.ArmorSlot;
import com.siberanka.twicosmetics.cosmetics.type.HatType;
import com.siberanka.twicosmetics.player.UltraPlayer;

/**
 * Represents an instance of a hat summoned by a player.
 *
 * @author iSach
 * @since 08-23-2016
 */
public class Hat extends ArmorCosmetic<HatType> {

    public Hat(UltraPlayer owner, HatType type, TwiCosmetics ultraCosmetics) {
        super(owner, type, ultraCosmetics);
        setItemStack(type.getItemStack());
    }

    @Override
    protected void onEquip() {
        // Setting the item is done in ArmorCosmetic#tryEquip
    }

    @Override
    protected ArmorSlot getArmorSlot() {
        return ArmorSlot.HELMET;
    }
}
