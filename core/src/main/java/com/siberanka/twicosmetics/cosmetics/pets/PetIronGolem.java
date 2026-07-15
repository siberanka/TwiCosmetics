package com.siberanka.twicosmetics.cosmetics.pets;

import com.siberanka.twicosmetics.TwiCosmetics;
import com.siberanka.twicosmetics.config.SettingsManager;
import com.siberanka.twicosmetics.cosmetics.type.PetType;
import com.siberanka.twicosmetics.player.UltraPlayer;
import com.siberanka.twicosmetics.util.ItemFactory;
import com.cryptomorin.xseries.XAttribute;
import com.cryptomorin.xseries.XMaterial;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;

/**
 * Represents an instance of an iron golem pet summoned by a player.
 *
 * @author RadBuilder
 * @since 07-02-2017
 */
public class PetIronGolem extends Pet {
    private static final Attribute SCALE = XAttribute.SCALE.get();

    public PetIronGolem(UltraPlayer owner, PetType type, TwiCosmetics ultraCosmetics) {
        super(owner, type, ultraCosmetics, XMaterial.RED_DYE);
    }

    @Override
    public void setupEntity() {
        if (SettingsManager.getConfig().getBoolean("Pets-Are-Babies") && SCALE != null) {
            AttributeModifier mod = ItemFactory.createAttributeModifier("scale", -0.5, AttributeModifier.Operation.ADD_NUMBER, null);
            entity.getAttribute(SCALE).addModifier(mod);
        }
    }
}
