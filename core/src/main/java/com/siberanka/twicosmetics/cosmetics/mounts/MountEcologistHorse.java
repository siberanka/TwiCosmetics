package com.siberanka.twicosmetics.cosmetics.mounts;

import com.siberanka.twicosmetics.TwiCosmetics;
import com.siberanka.twicosmetics.cosmetics.type.MountType;
import com.siberanka.twicosmetics.player.UltraPlayer;
import com.cryptomorin.xseries.particles.ParticleDisplay;
import com.cryptomorin.xseries.particles.XParticle;
import org.bukkit.entity.Horse;

/**
 * Represents an instance of an ecologist mount.
 *
 * @author iSach
 * @since 08-10-2015
 */
public class MountEcologistHorse extends MountAbstractHorse {
    private final ParticleDisplay display;

    public MountEcologistHorse(UltraPlayer owner, MountType type, TwiCosmetics ultraCosmetics) {
        super(owner, type, ultraCosmetics);
        display = ParticleDisplay.of(XParticle.HAPPY_VILLAGER).offset(0.4, 0.2, 0.4).withCount(5)
                .withLocationCaller(() -> entity.getLocation().add(0, 1, 0));
    }

    @Override
    public void setupEntity() {
        super.setupEntity();
        ((Horse) entity).setColor(Horse.Color.CHESTNUT);
    }

    @Override
    public void onUpdate() {
        display.spawn();
    }
}
