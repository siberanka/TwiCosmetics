package com.siberanka.twicosmetics.menu.buttons;

import com.siberanka.twicosmetics.config.MessageManager;
import com.siberanka.twicosmetics.menu.Button;
import com.siberanka.twicosmetics.menu.ClickData;
import com.siberanka.twicosmetics.player.UltraPlayer;
import com.siberanka.twicosmetics.util.ItemFactory;
import net.kyori.adventure.text.Component;
import org.bukkit.inventory.ItemStack;

public class ToggleGadgetsButton implements Button {
    @Override
    public ItemStack getDisplayItem(UltraPlayer ultraPlayer) {
        // Show "disable gadgets" string when gadgets are enabled, and vice versa
        String enabledKey = (ultraPlayer.hasGadgetsEnabled() ? "Disable" : "Enable");
        String configPath = "Categories.Gadgets-Item.When-" + enabledKey + "d";
        String key = enabledKey + "-Gadgets";
        Component msg = MessageManager.getMessage(key);
        String[] lore = MessageManager.getLegacyMessage(key + "-Lore").split("\n");
        if (lore[0].isEmpty()) {
            lore = new String[] {};
        }
        return ItemFactory.rename(ItemFactory.getItemStackFromConfig(configPath), msg, lore);
    }

    @Override
    public void onClick(ClickData clickData) {
        UltraPlayer player = clickData.getClicker();
        player.setGadgetsEnabled(!player.hasGadgetsEnabled());
        clickData.getMenu().refresh(player);
    }
}
