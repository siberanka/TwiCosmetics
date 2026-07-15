package com.siberanka.twicosmetics.cosmetics.mounts;

import com.siberanka.twicosmetics.TwiCosmetics;
import com.siberanka.twicosmetics.cosmetics.type.MountType;
import com.siberanka.twicosmetics.player.UltraPlayer;
import com.cryptomorin.xseries.particles.ParticleDisplay;
import com.cryptomorin.xseries.particles.XParticle;

import java.awt.Color;

/**
 * Created by sacha on 1/03/17.
 */
public class MountWalkingDead extends MountAbstractHorse {
    private final ParticleDisplay enchantedDisplay;
    private final ParticleDisplay entityEffectDisplay;

    public MountWalkingDead(UltraPlayer owner, MountType type, TwiCosmetics ultraCosmetics) {
        super(owner, type, ultraCosmetics);
        enchantedDisplay = ParticleDisplay.of(XParticle.ENCHANTED_HIT).offset(0.4, 0.2, 0.4).withCount(5)
                .withLocationCaller(() -> entity.getLocation().add(0, 1, 0));
        entityEffectDisplay = enchantedDisplay.copy().withParticle(XParticle.ENTITY_EFFECT);
    }

    @Override
    public void onUpdate() {
        enchantedDisplay.spawn();
        entityEffectDisplay.withColor(new Color(RANDOM.nextInt(256), RANDOM.nextInt(256), RANDOM.nextInt(256))).spawn();
    }
}
