package com.siberanka.twicosmetics.cosmetics.mounts;

import com.siberanka.twicosmetics.TwiCosmetics;
import com.siberanka.twicosmetics.cosmetics.type.MountType;
import com.siberanka.twicosmetics.player.UltraPlayer;
import com.cryptomorin.xseries.XMaterial;
import org.bukkit.entity.MagmaCube;
import org.bukkit.inventory.ItemStack;

public class MountMoltenSnake extends MountFlyingSnake {
    private static final ItemStack NETHERRACK = XMaterial.NETHERRACK.parseItem();

    public MountMoltenSnake(UltraPlayer owner, MountType type, TwiCosmetics ultraCosmetics) {
        super(owner, type, ultraCosmetics, NETHERRACK);
    }

    @Override
    public void setupMainEntity() {
        ((MagmaCube) entity).setSize(2);
    }
}
