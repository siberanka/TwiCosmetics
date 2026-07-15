package com.siberanka.twicosmetics.cosmetics.particleeffects;

import com.siberanka.twicosmetics.TwiCosmetics;
import com.siberanka.twicosmetics.cosmetics.type.ParticleEffectType;
import com.siberanka.twicosmetics.player.UltraPlayer;

/**
 * Represents an instance of in love particles summoned by a player.
 *
 * @author iSach
 * @since 08-13-2015
 */
public class ParticleEffectInLove extends ParticleEffect {

    public ParticleEffectInLove(UltraPlayer owner, ParticleEffectType type, TwiCosmetics ultraCosmetics) {
        super(owner, type, ultraCosmetics);

        this.useAlternativeEffect = true;
        display.offset(0.5).withCount(getModifiedAmount(2))
                .withLocationCaller(() -> getPlayer().getLocation().add(0, 1, 0));
    }

    @Override
    public void onUpdate() {
        display.spawn();
    }
}
