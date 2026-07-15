package com.siberanka.twicosmetics.cosmetics.particleeffects;

import com.siberanka.twicosmetics.TwiCosmetics;
import com.siberanka.twicosmetics.config.MessageManager;
import com.siberanka.twicosmetics.cosmetics.Cosmetic;
import com.siberanka.twicosmetics.cosmetics.Updatable;
import com.siberanka.twicosmetics.cosmetics.type.ParticleEffectType;
import com.siberanka.twicosmetics.player.UltraPlayer;
import com.cryptomorin.xseries.particles.ParticleDisplay;
import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 * Represents an instance of a particle effect summoned by a player.
 *
 * @author iSach
 * @since 08-03-2015
 */
public abstract class ParticleEffect extends Cosmetic<ParticleEffectType> implements Updatable {

    /**
     * If false, no particle effects will appear when the player is standing.
     */
    protected boolean displayIfStanding = true;
    /**
     * If false, no particle effects will appear when the player is moving.
     */
    protected boolean displayIfMoving = true;
    /**
     * If true, display an alternative effect when the player is moving.
     */
    protected boolean useAlternativeEffect = false;

    protected Location lastLocation = null;
    protected boolean moving = true;
    protected boolean update = true;

    protected final ParticleDisplay display;
    protected final ParticleDisplay alternativeEffect;

    public ParticleEffect(UltraPlayer ultraPlayer, ParticleEffectType type, TwiCosmetics ultraCosmetics) {
        super(ultraPlayer, type, ultraCosmetics);
        display = ParticleDisplay.of(getType().getEffect());
        alternativeEffect = display.copy()
                .offset(0.2)
                .withCount(getModifiedAmount(3))
                .withLocationCaller(() -> getPlayer().getLocation().add(0, 0.2, 0));
    }

    @Override
    protected void onEquip() {
        if (getTwiCosmetics().getPaperSupport().hasParticlesDisabled(getPlayer())) {
            MessageManager.send(getPlayer(), "Particles-Minimal-In-Client");
        }
    }

    @Override
    protected void scheduleTask() {
        task = getTwiCosmetics().getScheduler().runAtEntityTimer(getPlayer(), this::run, 1, getType().getRepeatDelay());
    }

    protected boolean locEquals(Location a, Location b) {
        if (a == null || b == null) return false;
        // Manual comparison so we don't take into account pitch and yaw
        return a.getX() == b.getX() && a.getY() == b.getY() && a.getZ() == b.getZ();
    }

    @Override
    public void run() {
        Player player = getPlayer();
        if (player == null || getOwner().getCurrentParticleEffect() != this) {
            task.cancel();
            return;
        }

        // Checking whether the player has moved every tick results in false negatives
        // (meaning the player will be marked as not moving when they actually are.)
        if (update) {
            moving = !locEquals(lastLocation, player.getLocation());
            lastLocation = player.getLocation();
            if (getType().getRepeatDelay() == 1) {
                update = false;
            }
        } else {
            update = true;
        }

        // If the player is not moving, display the default particle effect.
        if (!isMoving()) {
            if (displayIfStanding) {
                onUpdate();
            }
            return;
        }
        // If the player is moving:
        if (!displayIfMoving) return;

        if (useAlternativeEffect) {
            // Display the alternative effect.
            showAlternativeEffect();
        } else {
            // Display default particle effect.
            onUpdate();
        }
    }

    protected boolean isMoving() {
        return moving;
    }

    protected int getModifiedAmount(int originalAmount) {
        // always return at least 1 so the particles work
        return Integer.max((int) (originalAmount * getType().getParticleMultiplier()), 1);
    }

    public void showAlternativeEffect() {
        alternativeEffect.spawn();
    }
}
