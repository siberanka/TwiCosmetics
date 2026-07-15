package com.siberanka.twicosmetics.menu.buttons;

import com.siberanka.twicosmetics.config.MessageManager;
import com.siberanka.twicosmetics.menu.Button;
import com.siberanka.twicosmetics.menu.ClickData;
import com.siberanka.twicosmetics.player.UltraPlayer;
import com.siberanka.twicosmetics.util.ItemFactory;
import net.kyori.adventure.text.Component;
import org.bukkit.inventory.ItemStack;

public class ToggleMorphSelfViewButton implements Button {
    @Override
    public ItemStack getDisplayItem(UltraPlayer ultraPlayer) {
        ItemStack selfViewStack;
        boolean toggle;
        if (ultraPlayer.canSeeSelfMorph()) {
            selfViewStack = ItemFactory.getItemStackFromConfig("Categories.Self-View-Item.When-Enabled");
            toggle = false;
        } else {
            selfViewStack = ItemFactory.getItemStackFromConfig("Categories.Self-View-Item.When-Disabled");
            toggle = true;
        }
        Component msg = MessageManager.getMessage((toggle ? "Enable" : "Disable") + "-Third-Person-View");
        return ItemFactory.rename(selfViewStack, msg);
    }

    @Override
    public void onClick(ClickData clickData) {
        UltraPlayer player = clickData.getClicker();
        player.setSeeSelfMorph(!player.canSeeSelfMorph());
        clickData.getMenu().refresh(player);
    }
}
