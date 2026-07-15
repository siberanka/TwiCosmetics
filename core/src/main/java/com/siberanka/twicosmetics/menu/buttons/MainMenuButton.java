package com.siberanka.twicosmetics.menu.buttons;

import com.siberanka.twicosmetics.TwiCosmetics;
import com.siberanka.twicosmetics.config.MessageManager;
import com.siberanka.twicosmetics.menu.Button;
import com.siberanka.twicosmetics.menu.ClickData;
import com.siberanka.twicosmetics.player.UltraPlayer;
import com.siberanka.twicosmetics.util.ItemFactory;
import org.bukkit.inventory.ItemStack;

public class MainMenuButton implements Button {
    private final ItemStack stack = ItemFactory.rename(ItemFactory.getItemStackFromConfig("Categories.Back-Main-Menu-Item"),
            MessageManager.getMessage("Menu.Main.Button.Name"));
    private final TwiCosmetics ultraCosmetics;

    public MainMenuButton(TwiCosmetics ultraCosmetics) {
        this.ultraCosmetics = ultraCosmetics;
    }

    @Override
    public ItemStack getDisplayItem(UltraPlayer ultraPlayer) {
        return stack;
    }

    @Override
    public void onClick(ClickData clickData) {
        ultraCosmetics.getMenus().openMainMenu(clickData.getClicker());
    }
}
