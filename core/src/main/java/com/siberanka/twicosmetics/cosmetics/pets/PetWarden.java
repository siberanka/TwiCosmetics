package com.siberanka.twicosmetics.cosmetics.pets;

import com.siberanka.twicosmetics.TwiCosmetics;
import com.siberanka.twicosmetics.config.SettingsManager;
import com.siberanka.twicosmetics.cosmetics.type.PetType;
import com.siberanka.twicosmetics.player.UltraPlayer;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityPotionEffectEvent;
import org.bukkit.event.entity.EntityPotionEffectEvent.Cause;

/**
 * Represents an instance of a warden pet summoned by a player.
 *
 * @author Chris6ix
 * @since 08-06-2022
 */
public class PetWarden extends Pet {
    private final boolean blockEffect = SettingsManager.getConfig().getBoolean(getOptionPath("Block-Effect"));

    public PetWarden(UltraPlayer owner, PetType type, TwiCosmetics ultraCosmetics) {
        super(owner, type, ultraCosmetics);
    }

    @EventHandler
    public void onDarkness(EntityPotionEffectEvent event) {
        if (!blockEffect) return;
        if (event.getEntityType() == EntityType.PLAYER && event.getCause() == Cause.WARDEN
                && event.getEntity().getLocation().distanceSquared(entity.getLocation()) < 22 * 22) {
            event.setCancelled(true);
        }
    }
}
