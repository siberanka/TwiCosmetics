package com.siberanka.twicosmetics.menu.menus;

import com.siberanka.twicosmetics.TwiCosmetics;
import com.siberanka.twicosmetics.cosmetics.Category;
import com.siberanka.twicosmetics.cosmetics.type.EmoteType;
import com.siberanka.twicosmetics.menu.CosmeticMenu;

/**
 * Emote {@link com.siberanka.twicosmetics.menu.Menu Menu}.
 *
 * @author iSach
 * @since 08-23-2016
 */
public class MenuEmotes extends CosmeticMenu<EmoteType> {

    public MenuEmotes(TwiCosmetics ultraCosmetics) {
        super(ultraCosmetics, Category.EMOTES);
    }
}
