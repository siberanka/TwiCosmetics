package com.siberanka.twicosmetics.menu.menus;

import com.siberanka.twicosmetics.TwiCosmetics;
import com.siberanka.twicosmetics.config.SettingsManager;
import com.siberanka.twicosmetics.cosmetics.Category;
import com.siberanka.twicosmetics.cosmetics.type.GadgetType;
import com.siberanka.twicosmetics.menu.CosmeticMenu;
import com.siberanka.twicosmetics.menu.buttons.ToggleGadgetsButton;
import com.siberanka.twicosmetics.player.UltraPlayer;
import org.bukkit.inventory.Inventory;

/**
 * Gadget {@link com.siberanka.twicosmetics.menu.Menu Menu}.
 *
 * @author iSach
 * @since 07-23-2016
 */
public class MenuGadgets extends CosmeticMenu<GadgetType> {

    public MenuGadgets(TwiCosmetics ultraCosmetics) {
        super(ultraCosmetics, Category.GADGETS);
    }

    @Override
    protected void putItems(Inventory inventory, UltraPlayer player, int page) {
        if (SettingsManager.getConfig().getBoolean("Categories.Gadgets.Allow-Disable-Gadgets", true)) {
            int slot = inventory.getSize() - (getCategory().hasGoBackArrow() ? 4 : 6);
            putItem(inventory, slot, new ToggleGadgetsButton(), player);
        }
    }
}
