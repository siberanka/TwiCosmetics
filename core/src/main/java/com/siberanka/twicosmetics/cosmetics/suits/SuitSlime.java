package com.siberanka.twicosmetics.cosmetics.suits;

import com.siberanka.twicosmetics.TwiCosmetics;
import com.siberanka.twicosmetics.config.SettingsManager;
import com.siberanka.twicosmetics.cosmetics.Updatable;
import com.siberanka.twicosmetics.cosmetics.type.SuitType;
import com.siberanka.twicosmetics.player.UltraPlayer;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class SuitSlime extends Suit implements Updatable {
    private final boolean jumpBoost = SettingsManager.getConfig().getBoolean(getOptionPath("Jump-Boost"), true);
    private final PotionEffect effect = new PotionEffect(PotionEffectType.JUMP_BOOST, 60, 1);

    public SuitSlime(UltraPlayer ultraPlayer, SuitType suitType, TwiCosmetics ultraCosmetics) {
        super(ultraPlayer, suitType, ultraCosmetics);
    }

    @Override
    public void onUpdate() {
        if (jumpBoost && isFullSuit()) {
            getPlayer().addPotionEffect(effect);
        }
    }
}
