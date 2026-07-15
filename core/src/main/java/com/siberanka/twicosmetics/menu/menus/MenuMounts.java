package com.siberanka.twicosmetics.menu.menus;

import com.siberanka.twicosmetics.TwiCosmetics;
import com.siberanka.twicosmetics.cosmetics.Category;
import com.siberanka.twicosmetics.cosmetics.type.MountType;
import com.siberanka.twicosmetics.menu.CosmeticMenu;

/**
 * Mount {@link com.siberanka.twicosmetics.menu.Menu Menu}.
 *
 * @author iSach
 * @since 08-23-2016
 */
public class MenuMounts extends CosmeticMenu<MountType> {

    public MenuMounts(TwiCosmetics ultraCosmetics) {
        super(ultraCosmetics, Category.MOUNTS);
    }

}
