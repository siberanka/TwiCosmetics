package com.siberanka.twicosmetics.cosmetics.pets;

import com.siberanka.twicosmetics.TwiCosmetics;
import com.siberanka.twicosmetics.cosmetics.type.PetType;
import com.siberanka.twicosmetics.player.UltraPlayer;

import org.bukkit.entity.MushroomCow;
import org.bukkit.entity.MushroomCow.Variant;

/**
 * Represents an instance of a mooshroom pet summoned by a player.
 *
 * @author ataranlen
 * @since 06-26-2015
 */
public class PetMooshroom extends Pet {
    public PetMooshroom(UltraPlayer owner, PetType type, TwiCosmetics ultraCosmetics) {
        super(owner, type, ultraCosmetics);
    }

    @Override
    protected boolean customize(String customization) {
        return enumCustomize(Variant.class, customization, ((MushroomCow) entity)::setVariant);
    }
}
