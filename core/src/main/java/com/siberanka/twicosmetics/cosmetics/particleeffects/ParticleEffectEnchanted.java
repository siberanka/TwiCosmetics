package com.siberanka.twicosmetics.cosmetics.particleeffects;

import com.siberanka.twicosmetics.TwiCosmetics;
import com.siberanka.twicosmetics.cosmetics.type.ParticleEffectType;
import com.siberanka.twicosmetics.player.UltraPlayer;
import com.siberanka.twicosmetics.util.MathUtils;
import com.cryptomorin.xseries.particles.ParticleDisplay;
import com.cryptomorin.xseries.particles.XParticle;

/**
 * Represents an instance of enchanted particles summoned by a player.
 *
 * @author iSach
 * @since 10-12-2015
 */
public class ParticleEffectEnchanted extends ParticleEffect {
    private final ParticleDisplay display = ParticleDisplay.of(XParticle.ENCHANT)
            .withLocationCaller(() -> getPlayer().getLocation().add(0, MathUtils.randomDouble(0.1, 2), 0))
            .withCount(getModifiedAmount(60))
            .withExtra(8f);

    public ParticleEffectEnchanted(UltraPlayer owner, ParticleEffectType type, TwiCosmetics ultraCosmetics) {
        super(owner, type, ultraCosmetics);
    }

    @Override
    public void onUpdate() {
        display.spawn();
    }
}
