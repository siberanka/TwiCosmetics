package com.siberanka.twicosmetics.cosmetics.pets;

import com.siberanka.twicosmetics.TwiCosmetics;
import com.siberanka.twicosmetics.config.SettingsManager;
import com.siberanka.twicosmetics.cosmetics.type.PetType;
import com.siberanka.twicosmetics.player.UltraPlayer;
import com.cryptomorin.xseries.XPotion;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityPotionEffectEvent;
import org.bukkit.event.entity.EntityPotionEffectEvent.Cause;
import org.bukkit.potion.PotionEffectType;

/**
 * Represents an instance of a elder guardian pet summoned by a player.
 *
 * @author Chris6ix
 * @since 15-09-2022
 */
public class PetElderGuardian extends Pet {
    private static final PotionEffectType MINING_FATIGUE = XPotion.MINING_FATIGUE.getPotionEffectType();
    private final boolean blockEffect = SettingsManager.getConfig().getBoolean(getOptionPath("Block-Effect"));

    public PetElderGuardian(UltraPlayer owner, PetType type, TwiCosmetics ultraCosmetics) {
        super(owner, type, ultraCosmetics);
    }

    @EventHandler
    public void onFatigue(EntityPotionEffectEvent event) {
        if (!blockEffect || event.getEntityType() != EntityType.PLAYER) {
            return;
        }
        if (event.getEntity().getWorld() != entity || event.getEntity().getLocation().distanceSquared(entity.getLocation()) > 52 * 52) {
            return;
        }
        if (event.getCause() == Cause.ATTACK && event.getNewEffect().getType().equals(MINING_FATIGUE)) {
            event.setCancelled(true);
        }
    }
}
