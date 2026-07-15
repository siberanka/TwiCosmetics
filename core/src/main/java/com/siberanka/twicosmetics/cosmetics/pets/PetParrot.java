package com.siberanka.twicosmetics.cosmetics.pets;

import com.siberanka.twicosmetics.TwiCosmetics;
import com.siberanka.twicosmetics.cosmetics.type.PetType;
import com.siberanka.twicosmetics.player.UltraPlayer;

import org.bukkit.entity.Parrot;
import org.bukkit.entity.Parrot.Variant;

/**
 * Represents an instance of a parrot pet summoned by a player.
 *
 * @author RadBuilder
 * @since 07-02-2017
 */
public class PetParrot extends Pet {
    public PetParrot(UltraPlayer owner, PetType type, TwiCosmetics ultraCosmetics) {
        super(owner, type, ultraCosmetics);
    }

    @Override
    protected boolean customize(String customization) {
        return enumCustomize(Variant.class, customization, ((Parrot) entity)::setVariant);
    }
}
