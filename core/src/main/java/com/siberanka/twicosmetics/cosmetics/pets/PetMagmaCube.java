package com.siberanka.twicosmetics.cosmetics.pets;

import com.siberanka.twicosmetics.TwiCosmetics;
import com.siberanka.twicosmetics.cosmetics.type.PetType;
import com.siberanka.twicosmetics.player.UltraPlayer;

import org.bukkit.entity.MagmaCube;

/**
 * Represents an instance of a magma cube pet summoned by a player.
 *
 * @author Chris6ix
 * @since 25-09-2022
 */
public class PetMagmaCube extends Pet {
    public PetMagmaCube(UltraPlayer owner, PetType type, TwiCosmetics ultraCosmetics) {
        super(owner, type, ultraCosmetics);
    }

    @Override
    public void setupEntity() {
        ((MagmaCube) entity).setSize(1);
    }

    @Override
    protected boolean customize(String customization) {
        int size;
        try {
            size = Integer.parseInt(customization);
        } catch (NumberFormatException e) {
            return false;
        }
        ((MagmaCube) entity).setSize(size);
        return true;
    }
}
