package com.siberanka.twicosmetics.menu.buttons;

import com.siberanka.twicosmetics.menu.Button;
import com.siberanka.twicosmetics.menu.ClickData;
import com.siberanka.twicosmetics.menu.PurchaseData;
import com.siberanka.twicosmetics.player.UltraPlayer;
import org.bukkit.inventory.ItemStack;

public class PurchaseShowcaseButton implements Button {
    private final ItemStack displayItem;

    public PurchaseShowcaseButton(PurchaseData purchaseData) {
        this.displayItem = purchaseData.getShowcaseItem();
    }

    @Override
    public ItemStack getDisplayItem(UltraPlayer ultraPlayer) {
        return displayItem;
    }

    @Override
    public void onClick(ClickData clickData) {
    }
}
