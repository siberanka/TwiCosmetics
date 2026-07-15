package com.siberanka.twicosmetics.cosmetics.particleeffects;

import com.siberanka.twicosmetics.TwiCosmetics;
import com.siberanka.twicosmetics.cosmetics.type.ParticleEffectType;
import com.siberanka.twicosmetics.player.UltraPlayer;
import org.bukkit.util.Vector;

/**
 * Represents an instance of green spark particles summoned by a player.
 *
 * @author iSach
 * @since 08-13-2015
 */
public class ParticleEffectGreenSparks extends ParticleEffect {

    private boolean up;
    private double height;
    private int step;

    public ParticleEffectGreenSparks(UltraPlayer owner, ParticleEffectType type, TwiCosmetics ultraCosmetics) {
        super(owner, type, ultraCosmetics);

        this.useAlternativeEffect = true;
    }

    @Override
    public void onUpdate() {
        if (up) {
            if (height < 2) {
                height += 0.05f;
            } else {
                up = false;
            }
        } else {
            if (height > 0) {
                height -= 0.05;
            } else {
                up = true;
            }
        }
        double inc = (2 * Math.PI) / 100;
        double angle = step * inc;
        Vector v = new Vector();
        v.setX(Math.cos(angle) * 1.1);
        v.setZ(Math.sin(angle) * 1.1);
        display.spawn(getPlayer().getLocation().add(v).add(0, height, 0));
        step += 4;
    }
}
