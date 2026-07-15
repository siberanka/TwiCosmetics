package com.siberanka.twicosmetics.cosmetics.pets;

import com.siberanka.twicosmetics.TwiCosmetics;
import com.siberanka.twicosmetics.cosmetics.type.PetType;
import com.siberanka.twicosmetics.player.UltraPlayer;

import org.bukkit.entity.Hoglin;

/**
 * Represents an instance of a hoglin pet summoned by a player.
 *
 * @author Chris6ix
 * @since 24-09-2022
 */
public class PetHoglin extends Pet {
    public PetHoglin(UltraPlayer owner, PetType type, TwiCosmetics ultraCosmetics) {
        super(owner, type, ultraCosmetics);
    }

    @Override
    public void setupEntity() {
        ((Hoglin) entity).setImmuneToZombification(true);
    }
}
