package com.siberanka.twicosmetics.menu.menus;

import com.siberanka.twicosmetics.TwiCosmetics;
import com.siberanka.twicosmetics.cosmetics.Category;
import com.siberanka.twicosmetics.cosmetics.type.ParticleEffectType;
import com.siberanka.twicosmetics.menu.CosmeticMenu;

/**
 * Particle Effect {@link com.siberanka.twicosmetics.menu.Menu Menu}.
 *
 * @author iSach
 * @since 08-23-2016
 */
public class MenuParticleEffects extends CosmeticMenu<ParticleEffectType> {

    public MenuParticleEffects(TwiCosmetics ultraCosmetics) {
        super(ultraCosmetics, Category.EFFECTS);
    }
}
