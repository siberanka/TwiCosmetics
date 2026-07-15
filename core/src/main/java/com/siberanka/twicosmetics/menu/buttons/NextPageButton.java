package com.siberanka.twicosmetics.menu.buttons;

import com.siberanka.twicosmetics.config.MessageManager;
import com.siberanka.twicosmetics.player.UltraPlayer;
import com.siberanka.twicosmetics.util.ItemFactory;
import org.bukkit.inventory.ItemStack;

public class NextPageButton extends ChangePageButton {
    private final ItemStack stack = ItemFactory.rename(ItemFactory.getItemStackFromConfig("Categories.Next-Page-Item"),
            MessageManager.getMessage("Menu.Misc.Button.Next-Page"));

    public NextPageButton() {
        super(1);
    }

    @Override
    public ItemStack getDisplayItem(UltraPlayer ultraPlayer) {
        return stack;
    }

}
