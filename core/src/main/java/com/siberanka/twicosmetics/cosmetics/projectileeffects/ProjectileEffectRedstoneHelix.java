package com.siberanka.twicosmetics.cosmetics.projectileeffects;

import com.siberanka.twicosmetics.TwiCosmetics;
import com.siberanka.twicosmetics.cosmetics.type.ProjectileEffectType;
import com.siberanka.twicosmetics.player.UltraPlayer;

import java.awt.Color;

public class ProjectileEffectRedstoneHelix extends ProjectileEffectHelix {
    public ProjectileEffectRedstoneHelix(UltraPlayer owner, ProjectileEffectType type, TwiCosmetics ultraCosmetics) {
        super(owner, type, ultraCosmetics);
        display.withColor(Color.RED);
    }
}
