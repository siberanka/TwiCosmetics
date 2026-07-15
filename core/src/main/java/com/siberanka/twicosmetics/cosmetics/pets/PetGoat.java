package com.siberanka.twicosmetics.cosmetics.pets;

import com.siberanka.twicosmetics.TwiCosmetics;
import com.siberanka.twicosmetics.cosmetics.type.PetType;
import com.siberanka.twicosmetics.player.UltraPlayer;

import org.bukkit.entity.Goat;

/**
 * Represents an instance of a goat pet summoned by a player.
 *
 * @author Chris6ix
 * @since 18-01-2022
 */

public class PetGoat extends Pet {
    public PetGoat(UltraPlayer owner, PetType type, TwiCosmetics ultraCosmetics) {
        super(owner, type, ultraCosmetics);
    }

    @Override
    protected boolean customize(String customization) {
        String[] parts = customization.split(":", 3);
        if (parts.length != 3) return false;
        Goat goat = (Goat) entity;
        goat.setLeftHorn(parts[0].equalsIgnoreCase("true"));
        goat.setRightHorn(parts[1].equalsIgnoreCase("true"));
        goat.setScreaming(parts[2].equalsIgnoreCase("true"));
        return true;
    }
}
