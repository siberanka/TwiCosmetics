package com.siberanka.twicosmetics.cosmetics.pets;

import com.siberanka.twicosmetics.TwiCosmetics;
import com.siberanka.twicosmetics.cosmetics.type.PetType;
import com.siberanka.twicosmetics.player.UltraPlayer;

import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEntityEvent;

/**
 * Represents an instance of a allay pet summoned by a player.
 *
 * @author Chris6ix
 * @since 08-06-2022
 */
public class PetAllay extends Pet {
    public PetAllay(UltraPlayer owner, PetType type, TwiCosmetics ultraCosmetics) {
        super(owner, type, ultraCosmetics);
    }

    @EventHandler
    public void onClick(PlayerInteractEntityEvent event) {
        if (event.getRightClicked() == entity) {
            event.setCancelled(true);
        }
    }

    @Override
    protected boolean customize(String customization) {
        return customizeHeldItem(customization);
    }
}
