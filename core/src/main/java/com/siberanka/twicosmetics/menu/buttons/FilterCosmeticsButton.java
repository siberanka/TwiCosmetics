package com.siberanka.twicosmetics.menu.buttons;

import com.siberanka.twicosmetics.config.MessageManager;
import com.siberanka.twicosmetics.menu.Button;
import com.siberanka.twicosmetics.menu.ClickData;
import com.siberanka.twicosmetics.player.UltraPlayer;
import com.siberanka.twicosmetics.util.ItemFactory;
import com.cryptomorin.xseries.XMaterial;
import net.kyori.adventure.text.Component;
import org.bukkit.inventory.ItemStack;

public class FilterCosmeticsButton implements Button {
    public FilterCosmeticsButton() {

    }

    @Override
    public ItemStack getDisplayItem(UltraPlayer ultraPlayer) {
        Component filterItemName;
        if (ultraPlayer.isFilteringByOwned()) {
            filterItemName = MessageManager.getMessage("Disable-Filter-By-Owned");
        } else {
            filterItemName = MessageManager.getMessage("Enable-Filter-By-Owned");
        }
        return ItemFactory.create(XMaterial.HOPPER, filterItemName);
    }

    @Override
    public void onClick(ClickData clickData) {
        UltraPlayer player = clickData.getClicker();
        player.setFilteringByOwned(!player.isFilteringByOwned());
        // Refresh inventory completely because it changes the layout
        clickData.getMenu().refresh(player);
    }
}
