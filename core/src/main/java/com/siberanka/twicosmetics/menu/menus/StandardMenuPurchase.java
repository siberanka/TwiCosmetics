package com.siberanka.twicosmetics.menu.menus;

import com.siberanka.twicosmetics.TwiCosmetics;
import com.siberanka.twicosmetics.menu.MenuPurchase;
import com.siberanka.twicosmetics.menu.PurchaseData;
import com.siberanka.twicosmetics.menu.buttons.PurchaseCancelButton;
import com.siberanka.twicosmetics.menu.buttons.PurchaseConfirmButton;
import com.siberanka.twicosmetics.menu.buttons.PurchaseShowcaseButton;
import com.siberanka.twicosmetics.player.UltraPlayer;
import net.kyori.adventure.text.Component;
import org.bukkit.inventory.Inventory;

/**
 * Created by sacha on 04/04/2017.
 * <p>
 * Standard implementation of MenuPurchase
 */
public class StandardMenuPurchase extends MenuPurchase {

    public StandardMenuPurchase(TwiCosmetics ultraCosmetics, Component name, PurchaseData purchaseData) {
        super(ultraCosmetics, name, purchaseData);
    }

    @Override
    protected void putItems(Inventory inventory, UltraPlayer player) {
        // Showcase Item
        putItem(inventory, 13, new PurchaseShowcaseButton(purchaseData), player);

        // Purchase Item
        PurchaseConfirmButton confirmButton = new PurchaseConfirmButton(purchaseData, ultraCosmetics.getEconomyHandler());
        for (int i = 27; i < 30; i++) {
            for (int j = i; j <= i + 18; j += 9) {
                putItem(inventory, j, confirmButton, player);
            }
        }

        // Cancel Item
        PurchaseCancelButton cancelButton = new PurchaseCancelButton(purchaseData);
        for (int i = 33; i < 36; i++) {
            for (int j = i; j <= i + 18; j += 9) {
                putItem(inventory, j, cancelButton, player);
            }
        }
    }
}
