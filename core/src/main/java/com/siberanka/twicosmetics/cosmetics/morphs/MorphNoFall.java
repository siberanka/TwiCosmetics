package com.siberanka.twicosmetics.cosmetics.morphs;

import com.siberanka.twicosmetics.TwiCosmetics;
import com.siberanka.twicosmetics.config.SettingsManager;
import com.siberanka.twicosmetics.cosmetics.type.MorphType;
import com.siberanka.twicosmetics.player.UltraPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;

public class MorphNoFall extends Morph {
    private final boolean noFall = SettingsManager.getConfig().getBoolean(getOptionPath("No-Fall-Damage"));

    public MorphNoFall(UltraPlayer owner, MorphType type, TwiCosmetics ultraCosmetics) {
        super(owner, type, ultraCosmetics);
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (canUseSkill && noFall && event.getEntity() == getPlayer() && getOwner().getCurrentMorph() == this
                && event.getCause() == EntityDamageEvent.DamageCause.FALL) {
            event.setCancelled(true);
        }
    }
}
