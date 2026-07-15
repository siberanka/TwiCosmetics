package com.siberanka.twicosmetics.cosmetics.pets;

import com.siberanka.twicosmetics.TwiCosmetics;
import com.siberanka.twicosmetics.cosmetics.type.PetType;
import com.siberanka.twicosmetics.player.UltraPlayer;
import com.siberanka.twicosmetics.util.ItemFactory;

import org.bukkit.DyeColor;
import org.bukkit.entity.Sheep;

import com.cryptomorin.xseries.XTag;

/**
 * Represents an instance of a sheep pet summoned by a player.
 *
 * @author iSach
 * @since 08-12-2015
 */
public class PetSheep extends Pet {
    public PetSheep(UltraPlayer owner, PetType type, TwiCosmetics ultraCosmetics) {
        super(owner, type, ultraCosmetics);
    }

    @Override
    public void onUpdate() {
        dropItem = ItemFactory.randomItemFromTag(XTag.WOOL);
        super.onUpdate();
    }

    @Override
    protected boolean customize(String customization) {
        return enumCustomize(DyeColor.class, customization, ((Sheep) entity)::setColor);
    }
}
