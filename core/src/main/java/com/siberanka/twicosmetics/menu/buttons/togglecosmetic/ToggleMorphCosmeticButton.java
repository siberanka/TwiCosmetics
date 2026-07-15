package com.siberanka.twicosmetics.menu.buttons.togglecosmetic;

import com.siberanka.twicosmetics.TwiCosmetics;
import com.siberanka.twicosmetics.config.MessageManager;
import com.siberanka.twicosmetics.cosmetics.type.MorphType;
import com.siberanka.twicosmetics.player.UltraPlayer;

import java.util.List;

public class ToggleMorphCosmeticButton extends ToggleCosmeticButton {
    public ToggleMorphCosmeticButton(TwiCosmetics ultraCosmetics, MorphType cosmeticType) {
        super(ultraCosmetics, cosmeticType);
    }

    @Override
    protected void modifyLore(List<String> lore, UltraPlayer ultraPlayer) {
        MorphType morphType = (MorphType) cosmeticType;
        if (morphType.canUseSkill()) {
            lore.add("");
            lore.add(MessageManager.toLegacy(morphType.getSkill()));
        }
    }
}
