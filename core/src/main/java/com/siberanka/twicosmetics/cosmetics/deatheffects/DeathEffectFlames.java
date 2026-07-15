package com.siberanka.twicosmetics.cosmetics.deatheffects;

import com.siberanka.twicosmetics.TwiCosmetics;
import com.siberanka.twicosmetics.cosmetics.type.DeathEffectType;
import com.siberanka.twicosmetics.player.UltraPlayer;
import org.bukkit.Effect;
import org.bukkit.entity.Player;

public class DeathEffectFlames extends DeathEffect {

    public DeathEffectFlames(UltraPlayer owner, DeathEffectType type, TwiCosmetics ultraCosmetics) {
        super(owner, type, ultraCosmetics);
    }

    @Override
    public void displayParticles() {
        Player player = getPlayer();
        for (int i = 0; i < 10; i++) {
            double offsetX = (Math.random() - 0.5) * 2;
            double offsetY = Math.random() * 1.5;
            double offsetZ = (Math.random() - 0.5) * 2;
            player.getWorld().playEffect(player.getLocation(), Effect.MOBSPAWNER_FLAMES, null);
            player.getWorld().playEffect(player.getLocation().clone().add(offsetX, offsetY, offsetZ), Effect.ELECTRIC_SPARK, null);
        }
    }
}
