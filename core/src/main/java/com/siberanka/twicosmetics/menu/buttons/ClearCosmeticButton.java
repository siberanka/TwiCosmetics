package com.siberanka.twicosmetics.menu.buttons;

import com.siberanka.twicosmetics.config.MessageManager;
import com.siberanka.twicosmetics.cosmetics.Category;
import com.siberanka.twicosmetics.menu.Button;
import com.siberanka.twicosmetics.menu.ClickData;
import com.siberanka.twicosmetics.player.UltraPlayer;
import com.siberanka.twicosmetics.util.ItemFactory;
import org.bukkit.inventory.ItemStack;

public class ClearCosmeticButton implements Button {
    private final ItemStack stack;
    private final Category category;

    public ClearCosmeticButton(Category category) {
        this.category = category;
        stack = ItemFactory.rename(ItemFactory.getItemStackFromConfig("Categories.Clear-Cosmetic-Item"),
                MessageManager.getMessage("Clear." + (category == null ? "Cosmetics" : category.getConfigPath())));
    }

    public ClearCosmeticButton() {
        this(null);
    }

    @Override
    public ItemStack getDisplayItem(UltraPlayer ultraPlayer) {
        return stack;
    }

    @Override
    public void onClick(ClickData clickData) {
        UltraPlayer clicker = clickData.getClicker();
        if (category == null) {
            clicker.clear();
            return;
        }
        if (category.isSuits()) {
            for (Category cat : Category.values()) {
                if (cat.isSuits()) {
                    clicker.removeCosmetic(cat);
                }
            }
        } else {
            clicker.removeCosmetic(category);
        }
        clickData.getMenu().refresh(clicker);
    }
}
