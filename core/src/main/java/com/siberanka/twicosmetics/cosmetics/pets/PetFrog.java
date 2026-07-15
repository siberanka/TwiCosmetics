package com.siberanka.twicosmetics.cosmetics.pets;

import com.siberanka.twicosmetics.TwiCosmetics;
import com.siberanka.twicosmetics.cosmetics.type.PetType;
import com.siberanka.twicosmetics.player.UltraPlayer;
import org.bukkit.entity.Frog;
import org.bukkit.entity.Frog.Variant;

/**
 * Represents an instance of a frog pet summoned by a player.
 *
 * @author Chris6ix
 * @since 08-06-2022
 */
public class PetFrog extends Pet {
    public PetFrog(UltraPlayer owner, PetType type, TwiCosmetics ultraCosmetics) {
        super(owner, type, ultraCosmetics);
    }

    @Override
    protected boolean customize(String customization) {
        return oldEnumCustomize(Variant.class, customization, ((Frog) entity)::setVariant);
    }
}
