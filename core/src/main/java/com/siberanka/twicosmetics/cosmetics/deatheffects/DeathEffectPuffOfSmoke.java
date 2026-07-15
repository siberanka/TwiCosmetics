package com.siberanka.twicosmetics.cosmetics.deatheffects;

import com.siberanka.twicosmetics.TwiCosmetics;
import com.siberanka.twicosmetics.cosmetics.type.DeathEffectType;
import com.siberanka.twicosmetics.player.UltraPlayer;

public class DeathEffectPuffOfSmoke extends DeathEffect {
    public DeathEffectPuffOfSmoke(UltraPlayer owner, DeathEffectType type, TwiCosmetics ultraCosmetics) {
        super(owner, type, ultraCosmetics);
        display.withCount(75).offset(0.5, 0.5, 0.5).withLocationCaller(() -> getPlayer().getLocation().add(0, 1, 0));
    }
}
