package com.siberanka.twicosmetics.menu.buttons.togglecosmetic;

import com.siberanka.twicosmetics.TwiCosmetics;
import com.siberanka.twicosmetics.cosmetics.type.PetType;
import com.siberanka.twicosmetics.player.UltraPlayer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public class TogglePetCosmeticButton extends ToggleCosmeticButton {
    public TogglePetCosmeticButton(TwiCosmetics ultraCosmetics, PetType cosmeticType) {
        super(ultraCosmetics, cosmeticType);
    }

    @Override
    protected Component modifyName(Component base, UltraPlayer ultraPlayer) {
        Component petName = ultraPlayer.getPetName((PetType) cosmeticType);
        if (petName == null) return base;
        return Component.empty().append(base).appendSpace().append(Component.text("(", NamedTextColor.GRAY))
                .append(petName).append(Component.text(")", NamedTextColor.GRAY));
    }
}
