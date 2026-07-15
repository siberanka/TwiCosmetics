package com.siberanka.twicosmetics.cosmetics.mounts;

import com.siberanka.twicosmetics.TwiCosmetics;
import com.siberanka.twicosmetics.cosmetics.type.MountType;
import com.siberanka.twicosmetics.player.UltraPlayer;
import com.cryptomorin.xseries.XMaterial;
import org.bukkit.entity.AbstractHorse;
import org.bukkit.inventory.ItemStack;

/**
 * Created by sacha on 11/01/17.
 */
public abstract class MountAbstractHorse extends Mount {
    private static final ItemStack SADDLE = XMaterial.SADDLE.parseItem();

    public MountAbstractHorse(UltraPlayer ultraPlayer, MountType type, TwiCosmetics ultraCosmetics) {
        super(ultraPlayer, type, ultraCosmetics);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void setupEntity() {
        AbstractHorse horse = (AbstractHorse) entity;
        horse.setTamed(true);
        horse.setDomestication(1);
        horse.getInventory().setSaddle(SADDLE);
        horse.setJumpStrength(0.7);
    }
}
