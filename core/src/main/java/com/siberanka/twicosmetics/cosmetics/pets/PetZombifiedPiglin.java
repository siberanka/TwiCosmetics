package com.siberanka.twicosmetics.cosmetics.pets;

import com.siberanka.twicosmetics.TwiCosmetics;
import com.siberanka.twicosmetics.cosmetics.type.PetType;
import com.siberanka.twicosmetics.player.UltraPlayer;

/**
 * Represents an instance of a zombified piglin pet summoned by a player.
 *
 * @author Chris6ix
 * @since 15-09-2022
 */
public class PetZombifiedPiglin extends Pet {
    public PetZombifiedPiglin(UltraPlayer owner, PetType type, TwiCosmetics ultraCosmetics) {
        super(owner, type, ultraCosmetics);
    }

    @Override
    protected boolean customize(String customization) {
        return customizeHeldItem(customization);
    }
}
