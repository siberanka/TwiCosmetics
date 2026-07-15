package com.siberanka.twicosmetics.cosmetics.particleeffects;

import com.siberanka.twicosmetics.TwiCosmetics;
import com.siberanka.twicosmetics.cosmetics.type.ParticleEffectType;
import com.siberanka.twicosmetics.player.UltraPlayer;

public class ParticleEffectCursedHalo extends ParticleEffectHalo {

    public ParticleEffectCursedHalo(UltraPlayer owner, ParticleEffectType type, TwiCosmetics ultraCosmetics) {
        super(owner, type, ultraCosmetics);
    }

    @Override
    public void onUpdate() {
        drawCircle(0.4f, 6, getPlayer().getEyeLocation().add(0, 0.7, 0));
    }
}
