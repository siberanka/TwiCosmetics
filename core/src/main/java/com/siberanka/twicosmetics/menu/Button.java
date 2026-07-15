package com.siberanka.twicosmetics.menu;

import com.siberanka.twicosmetics.player.UltraPlayer;
import org.bukkit.inventory.ItemStack;

public interface Button {
    public ItemStack getDisplayItem(UltraPlayer ultraPlayer);

    public void onClick(ClickData clickData);
}
