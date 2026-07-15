package com.siberanka.twicosmetics.cosmetics.deatheffects;

import com.siberanka.twicosmetics.TwiCosmetics;
import com.siberanka.twicosmetics.config.SettingsManager;
import com.siberanka.twicosmetics.cosmetics.type.DeathEffectType;
import com.siberanka.twicosmetics.player.UltraPlayer;
import org.bukkit.entity.Player;

public class DeathEffectLightning extends DeathEffect {

    private final boolean silent = SettingsManager.getConfig().getBoolean(getOptionPath("Silent"));

    public DeathEffectLightning(UltraPlayer owner, DeathEffectType type, TwiCosmetics ultraCosmetics) {
        super(owner, type, ultraCosmetics);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void displayParticles() {
        Player player = getPlayer();
        player.getWorld().spigot().strikeLightningEffect(player.getLocation(), silent);
    }
}
