package com.siberanka.twicosmetics.cosmetics.pets;

import com.siberanka.twicosmetics.TwiCosmetics;
import com.siberanka.twicosmetics.cosmetics.type.PetType;
import com.siberanka.twicosmetics.player.UltraPlayer;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Enderman;

/**
 * Represents an instance of a enderman pet summoned by a player.
 *
 * @author Chris6ix
 * @since 12-04-2022
 */
public class PetEnderman extends Pet {
    public PetEnderman(UltraPlayer owner, PetType type, TwiCosmetics ultraCosmetics) {
        super(owner, type, ultraCosmetics);
    }

    @Override
    protected boolean customize(String customization) {
        Material mat = Material.matchMaterial(customization);
        if (mat == null || !mat.isBlock()) {
            return false;
        }
        ((Enderman) entity).setCarriedBlock(Bukkit.createBlockData(mat));
        return true;
    }
}
