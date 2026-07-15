package com.siberanka.twicosmetics.cosmetics.pets;

import com.siberanka.twicosmetics.TwiCosmetics;
import com.siberanka.twicosmetics.cosmetics.type.PetType;
import com.siberanka.twicosmetics.player.UltraPlayer;

import org.bukkit.entity.Donkey;

/**
 * Represents an instance of a donkey pet summoned by a player.
 *
 * @author Chris6ix
 * @since 05-09-2022
 */
public class PetDonkey extends Pet {
    public PetDonkey(UltraPlayer owner, PetType type, TwiCosmetics ultraCosmetics) {
        super(owner, type, ultraCosmetics);
    }

    @Override
    protected boolean customize(String customization) {
        ((Donkey) entity).setCarryingChest(customization.equalsIgnoreCase("true"));
        return true;
    }
}
