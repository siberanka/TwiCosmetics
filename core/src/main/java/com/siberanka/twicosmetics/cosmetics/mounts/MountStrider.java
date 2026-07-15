package com.siberanka.twicosmetics.cosmetics.mounts;

import com.siberanka.twicosmetics.TwiCosmetics;
import com.siberanka.twicosmetics.cosmetics.type.MountType;
import com.siberanka.twicosmetics.player.UltraPlayer;
import org.bukkit.Material;
import org.bukkit.entity.Strider;

public class MountStrider extends MountHeldItem {

    public MountStrider(UltraPlayer ultraPlayer, MountType type, TwiCosmetics ultraCosmetics) {
        super(ultraPlayer, type, ultraCosmetics);
    }

    @Override
    public void setupEntity() {
        ((Strider) entity).setSaddle(true);
    }

    @Override
    public Material getHeldItemMaterial() {
        return Material.WARPED_FUNGUS_ON_A_STICK;
    }
}
