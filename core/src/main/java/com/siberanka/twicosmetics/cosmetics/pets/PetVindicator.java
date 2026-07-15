package com.siberanka.twicosmetics.cosmetics.pets;

import com.siberanka.twicosmetics.TwiCosmetics;
import com.siberanka.twicosmetics.cosmetics.type.PetType;
import com.siberanka.twicosmetics.player.UltraPlayer;

/**
 * Represents an instance of a vindicator pet summoned by a player.
 *
 * @author Chris6ix
 * @since 28-09-2022
 */
public class PetVindicator extends Pet {
    public PetVindicator(UltraPlayer owner, PetType type, TwiCosmetics ultraCosmetics) {
        super(owner, type, ultraCosmetics);
    }

    @Override
    protected boolean customize(String customization) {
        return customizeHeldItem(customization);
    }
}
