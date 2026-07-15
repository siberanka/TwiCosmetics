package com.siberanka.twicosmetics.cosmetics.morphs;

import com.siberanka.twicosmetics.TwiCosmetics;
import com.siberanka.twicosmetics.cosmetics.type.MorphType;
import com.siberanka.twicosmetics.player.UltraPlayer;
import org.bukkit.entity.Snowball;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * Represents an instance of a snowman morph summoned by a player.
 *
 * @author iSach
 * @since 11-29-2015
 */
public class MorphSnowman extends MorphLeftClickCooldown {
    public MorphSnowman(UltraPlayer owner, MorphType type, TwiCosmetics ultraCosmetics) {
        super(owner, type, ultraCosmetics, 0.5);
    }

    @Override
    public void onLeftClick(PlayerInteractEvent event) {
        event.setCancelled(true);
        event.getPlayer().launchProjectile(Snowball.class);
    }
}
