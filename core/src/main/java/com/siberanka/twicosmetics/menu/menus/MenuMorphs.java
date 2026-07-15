package com.siberanka.twicosmetics.menu.menus;

import com.siberanka.twicosmetics.TwiCosmetics;
import com.siberanka.twicosmetics.cosmetics.Category;
import com.siberanka.twicosmetics.cosmetics.type.MorphType;
import com.siberanka.twicosmetics.menu.CosmeticMenu;
import com.siberanka.twicosmetics.menu.buttons.ToggleMorphSelfViewButton;
import com.siberanka.twicosmetics.player.UltraPlayer;
import org.bukkit.inventory.Inventory;

/**
 * Morph {@link com.siberanka.twicosmetics.menu.Menu Menu}.
 *
 * @author iSach
 * @since 08-23-2016
 */
public class MenuMorphs extends CosmeticMenu<MorphType> {

    public MenuMorphs(TwiCosmetics ultraCosmetics) {
        super(ultraCosmetics, Category.MORPHS);
    }

    @Override
    protected void putItems(Inventory inventory, UltraPlayer player, int page) {
        int slot = inventory.getSize() - (getCategory().hasGoBackArrow() ? 4 : 6);
        putItem(inventory, slot, new ToggleMorphSelfViewButton(), player);
    }
}
