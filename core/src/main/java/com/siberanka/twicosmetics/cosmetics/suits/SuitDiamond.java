package com.siberanka.twicosmetics.cosmetics.suits;

import com.siberanka.twicosmetics.TwiCosmetics;
import com.siberanka.twicosmetics.cosmetics.type.SuitType;
import com.siberanka.twicosmetics.player.UltraPlayer;
import com.cryptomorin.xseries.XMaterial;

import java.util.List;

public class SuitDiamond extends TrailSuit {
    public SuitDiamond(UltraPlayer ultraPlayer, SuitType suitType, TwiCosmetics ultraCosmetics) {
        super(ultraPlayer, suitType, ultraCosmetics);
    }

    @Override
    protected List<XMaterial> getTrailBlocks() {
        return List.of(XMaterial.DIAMOND_BLOCK);
    }
}
