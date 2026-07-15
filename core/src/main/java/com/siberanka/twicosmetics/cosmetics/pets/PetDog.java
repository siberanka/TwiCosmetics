package com.siberanka.twicosmetics.cosmetics.pets;

import com.siberanka.twicosmetics.TwiCosmetics;
import com.siberanka.twicosmetics.cosmetics.type.PetType;
import com.siberanka.twicosmetics.player.UltraPlayer;
import org.bukkit.DyeColor;
import org.bukkit.entity.Wolf;

import java.util.Locale;

/**
 * Represents an instance of a dog pet summoned by a player.
 *
 * @author iSach
 * @since 08-12-2015
 */
public class PetDog extends Pet {
    private boolean fixedColor = false;

    public PetDog(UltraPlayer owner, PetType type, TwiCosmetics ultraCosmetics) {
        super(owner, type, ultraCosmetics);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if (!fixedColor) {
            ((Wolf) entity).setCollarColor(DyeColor.values()[RANDOM.nextInt(16)]);
        }
    }

    @Override
    protected boolean customize(String customization) {
        String[] parts = customization.split(":", 2);
        Wolf wolf = (Wolf) entity;
        fixedColor = enumCustomize(DyeColor.class, parts[0], wolf::setCollarColor);
        if (fixedColor && parts.length > 1) {
            try {
                fixedColor = oldEnumCustomize(Wolf.Variant.class, parts[1].toUpperCase(Locale.ROOT), wolf::setVariant);
            } catch (NoClassDefFoundError ignored) {
            }
        }
        return fixedColor;
    }
}
