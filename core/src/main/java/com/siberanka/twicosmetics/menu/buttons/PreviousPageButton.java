package com.siberanka.twicosmetics.menu.buttons;

import com.siberanka.twicosmetics.config.MessageManager;
import com.siberanka.twicosmetics.player.UltraPlayer;
import com.siberanka.twicosmetics.util.ItemFactory;
import org.bukkit.inventory.ItemStack;

public class PreviousPageButton extends ChangePageButton {
    private final ItemStack stack = ItemFactory.rename(ItemFactory.getItemStackFromConfig("Categories.Previous-Page-Item"),
            MessageManager.getMessage("Menu.Misc.Button.Previous-Page"));

    public PreviousPageButton() {
        super(-1);
    }

    @Override
    public ItemStack getDisplayItem(UltraPlayer ultraPlayer) {
        return stack;
    }
}
