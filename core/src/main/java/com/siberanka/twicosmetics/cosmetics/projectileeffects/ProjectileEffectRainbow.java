package com.siberanka.twicosmetics.cosmetics.projectileeffects;

import com.siberanka.twicosmetics.TwiCosmetics;
import com.siberanka.twicosmetics.cosmetics.type.ProjectileEffectType;
import com.siberanka.twicosmetics.player.UltraPlayer;
import com.siberanka.twicosmetics.util.ColorUtils;
import org.bukkit.entity.Projectile;

public class ProjectileEffectRainbow extends ProjectileEffect {
    public ProjectileEffectRainbow(UltraPlayer owner, ProjectileEffectType type, TwiCosmetics ultraCosmetics) {
        super(owner, type, ultraCosmetics);
    }

    @Override
    public void showParticles(Projectile projectile) {
        display.withColor(ColorUtils.getRainbowColor(0.5)).spawn(projectile.getLocation());
    }
}
