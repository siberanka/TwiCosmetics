package com.siberanka.twicosmetics.cosmetics.pets;

import com.siberanka.twicosmetics.TwiCosmetics;
import com.siberanka.twicosmetics.cosmetics.type.PetType;
import com.siberanka.twicosmetics.player.UltraPlayer;

import org.bukkit.entity.Tadpole;

/**
 * Represents an instance of a tadpole pet summoned by a player.
 *
 * @author Chris6ix
 * @since 07-09-2022
 */
public class PetTadpole extends Pet {
    public PetTadpole(UltraPlayer owner, PetType type, TwiCosmetics ultraCosmetics) {
        super(owner, type, ultraCosmetics);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        ((Tadpole) entity).setAge(0);
    }
}
