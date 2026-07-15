package com.siberanka.twicosmetics.menu.buttons;

import com.siberanka.twicosmetics.config.MessageManager;
import com.siberanka.twicosmetics.menu.Button;
import com.siberanka.twicosmetics.menu.ClickData;
import com.siberanka.twicosmetics.menu.PurchaseData;
import com.siberanka.twicosmetics.player.UltraPlayer;
import com.siberanka.twicosmetics.util.ItemFactory;
import com.cryptomorin.xseries.XMaterial;
import org.bukkit.inventory.ItemStack;

public class PurchaseCancelButton implements Button {
    private final PurchaseData purchaseData;

    public PurchaseCancelButton(PurchaseData purchaseData) {
        this.purchaseData = purchaseData;
    }

    @Override
    public ItemStack getDisplayItem(UltraPlayer ultraPlayer) {
        return ItemFactory.create(XMaterial.REDSTONE_BLOCK, MessageManager.getMessage("Cancel"));
    }

    @Override
    public void onClick(ClickData clickData) {
        purchaseData.runOnCancel();
    }
}
