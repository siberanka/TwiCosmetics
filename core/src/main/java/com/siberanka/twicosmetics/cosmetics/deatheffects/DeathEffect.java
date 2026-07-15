package com.siberanka.twicosmetics.cosmetics.deatheffects;

import com.siberanka.twicosmetics.TwiCosmetics;
import com.siberanka.twicosmetics.cosmetics.Cosmetic;
import com.siberanka.twicosmetics.cosmetics.type.DeathEffectType;
import com.siberanka.twicosmetics.player.UltraPlayer;
import com.cryptomorin.xseries.particles.ParticleDisplay;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DeathEffect extends Cosmetic<DeathEffectType> {
    protected ParticleDisplay display;

    public DeathEffect(UltraPlayer owner, DeathEffectType type, TwiCosmetics ultraCosmetics) {
        super(owner, type, ultraCosmetics);
        display = ParticleDisplay.of(getType().getEffect()).withEntity(getPlayer());
    }

    @Override
    protected void onEquip() {
        // Lightning and fireworks aren't really particles so we don't need to warn about disabled particles for now
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        if (event.getEntity() == getPlayer()) {
            displayParticles();
        }
    }

    public void displayParticles() {
        display.spawn();
    }
}
