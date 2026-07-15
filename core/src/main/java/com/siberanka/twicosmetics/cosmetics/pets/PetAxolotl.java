package com.siberanka.twicosmetics.cosmetics.pets;

import com.siberanka.twicosmetics.TwiCosmetics;
import com.siberanka.twicosmetics.config.SettingsManager;
import com.siberanka.twicosmetics.cosmetics.type.PetType;
import com.siberanka.twicosmetics.player.UltraPlayer;
import org.bukkit.entity.Axolotl;
import org.bukkit.entity.Axolotl.Variant;

/**
 * Represents an instance of an axolotl pet summoned by a player.
 *
 * @author Chris6ix
 * @since 14-01-2022
 */
public class PetAxolotl extends Pet {
    public PetAxolotl(UltraPlayer owner, PetType type, TwiCosmetics ultraCosmetics) {
        super(owner, type, ultraCosmetics);
    }

    @Override
    public void setupEntity() {
        // For some strange reason, an axolotl has a default movement speed of 1.0, which is higher
        // than the default speed of every other entity except dolphin.
        if (!SettingsManager.getConfig().getBoolean(getOptionPath("Fast"))) {
            setMovementSpeed(0.6);
        }
    }

    @Override
    protected boolean customize(String customization) {
        return enumCustomize(Variant.class, customization, ((Axolotl) entity)::setVariant);
    }
}
