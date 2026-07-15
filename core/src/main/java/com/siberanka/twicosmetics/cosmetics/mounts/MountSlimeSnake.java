package com.siberanka.twicosmetics.cosmetics.mounts;

import com.siberanka.twicosmetics.TwiCosmetics;
import com.siberanka.twicosmetics.cosmetics.type.MountType;
import com.siberanka.twicosmetics.player.UltraPlayer;
import com.cryptomorin.xseries.XMaterial;
import org.bukkit.entity.Slime;
import org.bukkit.inventory.ItemStack;

public class MountSlimeSnake extends MountFlyingSnake {
    private static final ItemStack SLIME_BLOCK = XMaterial.SLIME_BLOCK.parseItem();

    public MountSlimeSnake(UltraPlayer owner, MountType type, TwiCosmetics ultraCosmetics) {
        super(owner, type, ultraCosmetics, SLIME_BLOCK);
    }

    @Override
    public void setupMainEntity() {
        ((Slime) entity).setSize(2);
    }
}
