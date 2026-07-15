package com.siberanka.twicosmetics.cosmetics.pets;

import com.siberanka.twicosmetics.TwiCosmetics;
import com.siberanka.twicosmetics.cosmetics.type.PetType;
import com.siberanka.twicosmetics.player.UltraPlayer;

import org.bukkit.Material;
import org.bukkit.entity.Piglin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PiglinBarterEvent;

/**
 * Represents an instance of a piglin pet summoned by a player.
 *
 * @author Chris6ix
 * @since 16-01-2022
 */
public class PetPiglin extends Pet {
    public PetPiglin(UltraPlayer owner, PetType type, TwiCosmetics ultraCosmetics) {
        super(owner, type, ultraCosmetics);
    }

    @Override
    public void setupEntity() {
        Piglin piglin = (Piglin) entity;
        piglin.setImmuneToZombification(true);
        for (Material interest : piglin.getInterestList()) {
            piglin.removeMaterialOfInterest(interest);
        }
    }

    @EventHandler
    public void onBarter(PiglinBarterEvent event) {
        if (event.getEntity() == entity) {
            event.setCancelled(true);
        }
    }
}
