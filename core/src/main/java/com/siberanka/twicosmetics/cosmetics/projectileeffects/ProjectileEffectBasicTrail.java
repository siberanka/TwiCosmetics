package com.siberanka.twicosmetics.cosmetics.projectileeffects;

import com.siberanka.twicosmetics.TwiCosmetics;
import com.siberanka.twicosmetics.cosmetics.type.ProjectileEffectType;
import com.siberanka.twicosmetics.player.UltraPlayer;
import org.bukkit.entity.Projectile;

public class ProjectileEffectBasicTrail extends ProjectileEffect {

    public ProjectileEffectBasicTrail(UltraPlayer owner, ProjectileEffectType type, TwiCosmetics ultraCosmetics) {
        super(owner, type, ultraCosmetics);
    }

    @Override
    public void showParticles(Projectile projectile) {
        display.spawn(projectile.getLocation());
    }

}
