package com.siberanka.twicosmetics.menu.menus;

import com.siberanka.twicosmetics.TwiCosmetics;
import com.siberanka.twicosmetics.cosmetics.Category;
import com.siberanka.twicosmetics.cosmetics.type.DeathEffectType;
import com.siberanka.twicosmetics.menu.CosmeticMenu;

public class MenuDeathEffects extends CosmeticMenu<DeathEffectType> {

    public MenuDeathEffects(TwiCosmetics ultraCosmetics) {
        super(ultraCosmetics, Category.DEATH_EFFECTS);
    }

}
