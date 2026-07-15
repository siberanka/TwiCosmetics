package com.siberanka.twicosmetics.menu.menus;

import com.siberanka.twicosmetics.TwiCosmetics;
import com.siberanka.twicosmetics.cosmetics.Category;
import com.siberanka.twicosmetics.cosmetics.type.HatType;
import com.siberanka.twicosmetics.menu.CosmeticMenu;

/**
 * Hat {@link com.siberanka.twicosmetics.menu.Menu Menu}.
 *
 * @author iSach
 * @since 08-23-2016
 */
public class MenuHats extends CosmeticMenu<HatType> {

    public MenuHats(TwiCosmetics ultraCosmetics) {
        super(ultraCosmetics, Category.HATS);
    }
}
