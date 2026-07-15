package com.siberanka.twicosmetics.cosmetics.deatheffects;

import com.siberanka.twicosmetics.TwiCosmetics;
import com.siberanka.twicosmetics.cosmetics.type.DeathEffectType;
import com.siberanka.twicosmetics.player.UltraPlayer;
import org.bukkit.entity.Player;
import org.bukkit.Effect;

public class DeathEffectDragonBreath extends DeathEffect {

    public DeathEffectDragonBreath(UltraPlayer owner, DeathEffectType type, TwiCosmetics ultraCosmetics) {
        super(owner, type, ultraCosmetics);
    }

    @Override
    public void displayParticles() {
        Player player = getPlayer();
        player.getWorld().playEffect(player.getLocation(), Effect.DRAGON_BREATH, null);
    }
}
