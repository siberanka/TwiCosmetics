package com.siberanka.twicosmetics.cosmetics.pets;

import com.siberanka.twicosmetics.TwiCosmetics;
import com.siberanka.twicosmetics.cosmetics.type.PetType;
import com.siberanka.twicosmetics.player.UltraPlayer;

import org.bukkit.entity.PufferFish;

/**
 * Represents an instance of a pufferfish pet summoned by a player.
 *
 * @author Chris6ix
 * @since 05-09-2022
 */
public class PetPufferfish extends Pet {
    public PetPufferfish(UltraPlayer owner, PetType type, TwiCosmetics ultraCosmetics) {
        super(owner, type, ultraCosmetics);
    }

    @Override
    protected boolean customize(String customization) {
        int value = -1;
        try {
            value = Integer.parseInt(customization);
        } catch (NumberFormatException ignored) {
        }
        if (value < 0 || value > 2) return false;
        ((PufferFish) entity).setPuffState(value);
        return true;
    }
}
