package com.siberanka.twicosmetics.cosmetics.mounts;

import com.siberanka.twicosmetics.TwiCosmetics;
import com.siberanka.twicosmetics.cosmetics.type.MountType;
import com.siberanka.twicosmetics.player.UltraPlayer;
import org.bukkit.entity.Horse;

public class MountHorse extends MountAbstractHorse {

    public MountHorse(UltraPlayer ultraPlayer, MountType type, TwiCosmetics ultraCosmetics) {
        super(ultraPlayer, type, ultraCosmetics);
    }

    @Override
    public void setupEntity() {
        super.setupEntity();
        ((Horse) entity).setColor(Horse.Color.GRAY);
    }

    @Override
    public void onUpdate() {
    }
}
