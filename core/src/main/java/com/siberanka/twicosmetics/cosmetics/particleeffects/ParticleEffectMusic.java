package com.siberanka.twicosmetics.cosmetics.particleeffects;

import com.siberanka.twicosmetics.TwiCosmetics;
import com.siberanka.twicosmetics.cosmetics.type.ParticleEffectType;
import com.siberanka.twicosmetics.player.UltraPlayer;
import org.bukkit.Location;

/**
 * Represents an instance of music particles summoned by a player.
 *
 * @author iSach
 * @since 10-12-2015
 */
public class ParticleEffectMusic extends ParticleEffect {

    public ParticleEffectMusic(UltraPlayer owner, ParticleEffectType type, TwiCosmetics ultraCosmetics) {
        super(owner, type, ultraCosmetics);
        display.offset(0.2).withExtra(1).withLocationCaller(() -> getPlayer().getLocation().add(0, 1.25, 0));
    }

    @Override
    public void onUpdate() {
        Location loc = getPlayer().getLocation().add(0, 1.25, 0);
        for (int i = 0; i < getModifiedAmount(12); i++) {
            display.withNoteColor(RANDOM.nextInt(25)).spawn();
        }
    }
}
