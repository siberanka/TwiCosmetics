package com.siberanka.twicosmetics.cosmetics.mounts;

import com.siberanka.twicosmetics.TwiCosmetics;
import com.siberanka.twicosmetics.cosmetics.type.MountType;
import com.siberanka.twicosmetics.player.UltraPlayer;

public class MountDonkey extends MountAbstractHorse {

    public MountDonkey(UltraPlayer ultraPlayer, MountType type, TwiCosmetics ultraCosmetics) {
        super(ultraPlayer, type, ultraCosmetics);
    }

    @Override
    public void onUpdate() {
    }
}
