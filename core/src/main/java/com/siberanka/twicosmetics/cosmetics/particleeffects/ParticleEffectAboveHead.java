package com.siberanka.twicosmetics.cosmetics.particleeffects;

import com.siberanka.twicosmetics.TwiCosmetics;
import com.siberanka.twicosmetics.cosmetics.type.ParticleEffectType;
import com.siberanka.twicosmetics.player.UltraPlayer;

public class ParticleEffectAboveHead extends ParticleEffect {

    public ParticleEffectAboveHead(UltraPlayer owner, ParticleEffectType type, TwiCosmetics ultraCosmetics) {
        super(owner, type, ultraCosmetics);
        display.withExtra(1).withLocationCaller(() -> getPlayer().getEyeLocation().add(0, 0.8, 0));
    }

    @Override
    public void onUpdate() {
        display.spawn();
    }
}
