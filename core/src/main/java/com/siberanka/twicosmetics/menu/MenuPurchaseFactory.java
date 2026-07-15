package com.siberanka.twicosmetics.menu;

import com.siberanka.twicosmetics.TwiCosmetics;
import net.kyori.adventure.text.Component;

@FunctionalInterface
public interface MenuPurchaseFactory {
    public MenuPurchase createPurchaseMenu(TwiCosmetics ultraCosmetics, Component name, PurchaseData purchaseData);
}
