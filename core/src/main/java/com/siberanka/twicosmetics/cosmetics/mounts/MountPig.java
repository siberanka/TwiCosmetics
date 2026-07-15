package com.siberanka.twicosmetics.cosmetics.mounts;

import com.siberanka.twicosmetics.TwiCosmetics;
import com.siberanka.twicosmetics.cosmetics.type.MountType;
import com.siberanka.twicosmetics.player.UltraPlayer;
import com.cryptomorin.xseries.XMaterial;
import org.bukkit.Material;
import org.bukkit.entity.Pig;

public class MountPig extends MountHeldItem {
    private static final Material CARROT_ON_A_STICK = XMaterial.CARROT_ON_A_STICK.get();

    public MountPig(UltraPlayer ultraPlayer, MountType type, TwiCosmetics ultraCosmetics) {
        super(ultraPlayer, type, ultraCosmetics);
    }

    @Override
    public void setupEntity() {
        ((Pig) entity).setSaddle(true);
    }

    @Override
    public Material getHeldItemMaterial() {
        return CARROT_ON_A_STICK;
    }
}
