package com.siberanka.twicosmetics.cosmetics.pets;

import com.siberanka.twicosmetics.TwiCosmetics;
import com.siberanka.twicosmetics.cosmetics.type.PetType;
import com.siberanka.twicosmetics.player.UltraPlayer;

import org.bukkit.entity.PiglinBrute;

/**
 * Represents an instance of a piglin brute pet summoned by a player.
 *
 * @author Chris6ix
 * @since 25-09-2022
 */
public class PetPiglinBrute extends Pet {
    public PetPiglinBrute(UltraPlayer owner, PetType type, TwiCosmetics ultraCosmetics) {
        super(owner, type, ultraCosmetics);
    }

    @Override
    public void setupEntity() {
        ((PiglinBrute) entity).setImmuneToZombification(true);
    }
}
