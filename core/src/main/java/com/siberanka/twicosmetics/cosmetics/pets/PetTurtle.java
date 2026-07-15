package com.siberanka.twicosmetics.cosmetics.pets;

import com.siberanka.twicosmetics.TwiCosmetics;
import com.siberanka.twicosmetics.config.SettingsManager;
import com.siberanka.twicosmetics.cosmetics.type.PetType;
import com.siberanka.twicosmetics.player.UltraPlayer;
import com.siberanka.twicosmetics.util.ItemFactory;
import com.cryptomorin.xseries.XAttribute;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Turtle;

public class PetTurtle extends Pet {
    private static final Attribute SCALE = XAttribute.SCALE.get();

    public PetTurtle(UltraPlayer owner, PetType petType, TwiCosmetics ultraCosmetics) {
        super(owner, petType, ultraCosmetics);
    }

    @Override
    public void setupEntity() {
        if (SettingsManager.getConfig().getBoolean(getOptionPath("Bigger-Babies"))
                && !((Turtle) entity).isAdult() && SCALE != null) {
            AttributeModifier mod = ItemFactory.createAttributeModifier("turtle", 1, AttributeModifier.Operation.ADD_NUMBER, null);
            entity.getAttribute(SCALE).addModifier(mod);
        }
    }
}
