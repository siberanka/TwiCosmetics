package com.siberanka.twicosmetics.cosmetics.suits;

import com.siberanka.twicosmetics.TwiCosmetics;
import com.siberanka.twicosmetics.cosmetics.type.SuitType;
import com.siberanka.twicosmetics.player.UltraPlayer;
import com.cryptomorin.xseries.XMaterial;

import java.util.List;

public class SuitCursed extends TrailSuit {
    public SuitCursed(UltraPlayer ultraPlayer, SuitType suitType, TwiCosmetics ultraCosmetics) {
        super(ultraPlayer, suitType, ultraCosmetics);
    }

    @Override
    protected List<XMaterial> getTrailBlocks() {
        return List.of(XMaterial.NETHERRACK);
    }
}
