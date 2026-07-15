package com.siberanka.twicosmetics.cosmetics.gadgets;

import com.siberanka.twicosmetics.TwiCosmetics;
import com.siberanka.twicosmetics.cosmetics.type.GadgetType;
import com.siberanka.twicosmetics.player.UltraPlayer;
import com.siberanka.twicosmetics.util.ItemFactory;
import com.cryptomorin.xseries.XSound;
import com.cryptomorin.xseries.particles.XParticle;
import org.bukkit.Particle;
import org.bukkit.util.Vector;

/**
 * Represents an instance of a party popper gadget summoned by a player.
 *
 * @author iSach
 * @since 12-16-2015
 */
public class GadgetPartyPopper extends Gadget {
    private static final Particle ITEM_PARTICLE = XParticle.ITEM.get();
    private final XSound.SoundPlayer sound;

    public GadgetPartyPopper(UltraPlayer owner, GadgetType type, TwiCosmetics ultraCosmetics) {
        super(owner, type, ultraCosmetics);
        sound = XSound.ENTITY_CHICKEN_EGG.record().publicSound(true).soundPlayer().forPlayers(getPlayer());
    }

    @Override
    protected void onRightClick() {
        for (int i = 0; i < 30; i++) {
            Vector rand = new Vector(Math.random() - 0.5, Math.random() - 0.5, Math.random() - 0.5);
            Vector v = getPlayer().getEyeLocation().getDirection().add(rand.multiply(0.2)).multiply(3.2);
            getPlayer().getWorld().spawnParticle(ITEM_PARTICLE, getPlayer().getEyeLocation(), 10, v.getX(), v.getY(), v.getZ(), 0.2, ItemFactory.getRandomDye());
        }
        sound.atLocation(getPlayer().getLocation());
        for (int i = 0; i < 3; i++) {
            sound.play();
        }
    }
}
