package com.siberanka.twicosmetics.cosmetics.pets;

import com.siberanka.twicosmetics.TwiCosmetics;
import com.siberanka.twicosmetics.cosmetics.type.PetType;
import com.siberanka.twicosmetics.player.UltraPlayer;

import org.bukkit.entity.Snowman;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.EntityBlockFormEvent;

/**
 * Represents an instance of a snowman pet summoned by a player.
 *
 * @author RadBuilder
 * @since 07-02-2017
 */
public class PetSnowman extends Pet {
    public PetSnowman(UltraPlayer owner, PetType type, TwiCosmetics ultraCosmetics) {
        super(owner, type, ultraCosmetics);
    }

    @EventHandler
    public void onTrail(EntityBlockFormEvent event) {
        if (event.getEntity() == entity) {
            event.setCancelled(true);
        }
    }

    @Override
    protected boolean customize(String customization) {
        if (customization.equalsIgnoreCase("true")) {
            ((Snowman) entity).setDerp(true);
        }
        return true;
    }
}
