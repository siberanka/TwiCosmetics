package com.siberanka.twicosmetics.cosmetics.pets;

import com.siberanka.twicosmetics.TwiCosmetics;
import com.siberanka.twicosmetics.cosmetics.type.PetType;
import com.siberanka.twicosmetics.player.UltraPlayer;

import org.bukkit.entity.Slime;

/**
 * Represents an instance of a slime pet summoned by a player.
 *
 * @author datatags
 * @since 18-01-2022
 */

public class PetSlime extends Pet {
    public PetSlime(UltraPlayer owner, PetType type, TwiCosmetics ultraCosmetics) {
        super(owner, type, ultraCosmetics);
    }

    @Override
    public void setupEntity() {
        ((Slime) entity).setSize(1);
    }

    @Override
    protected boolean customize(String customization) {
        int size;
        try {
            size = Integer.parseInt(customization);
        } catch (NumberFormatException e) {
            return false;
        }
        ((Slime) entity).setSize(size);
        return true;
    }
}
