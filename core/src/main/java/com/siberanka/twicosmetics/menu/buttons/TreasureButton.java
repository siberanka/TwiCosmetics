package com.siberanka.twicosmetics.menu.buttons;

import com.siberanka.twicosmetics.TwiCosmetics;
import com.siberanka.twicosmetics.config.MessageManager;
import com.siberanka.twicosmetics.config.SettingsManager;
import com.siberanka.twicosmetics.menu.Button;

public abstract class TreasureButton implements Button {
    protected final boolean canBuyKeys;
    protected final String buyKeyMessage;

    public TreasureButton(TwiCosmetics ultraCosmetics) {
        canBuyKeys = ultraCosmetics.getEconomyHandler().isUsingEconomy() && SettingsManager.getConfig().getInt("TreasureChests.Key-Price") > 0;
        if (canBuyKeys) {
            buyKeyMessage = "\n" + MessageManager.getLegacyMessage("Click-Buy-Key") + "\n";
        } else {
            buyKeyMessage = "";
        }
    }
}
