package com.siberanka.twicosmetics.cosmetics.pets;

import com.siberanka.twicosmetics.TwiCosmetics;
import com.siberanka.twicosmetics.cosmetics.type.PetType;
import com.siberanka.twicosmetics.player.UltraPlayer;

import org.bukkit.entity.Creeper;

/**
 * Represents an instance of a creeper pet summoned by a player.
 *
 * @author Chris6ix
 * @since 12-04-2022
 */
public class PetCreeper extends Pet {
    public PetCreeper(UltraPlayer owner, PetType type, TwiCosmetics ultraCosmetics) {
        super(owner, type, ultraCosmetics);
    }

    @Override
    protected boolean customize(String customization) {
        if (customization.equalsIgnoreCase("true")) {
            ((Creeper) entity).setPowered(true);
        }
        return true;
    }
}
