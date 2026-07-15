package com.siberanka.twicosmetics.menu.buttons.togglecosmetic;

import com.siberanka.twicosmetics.TwiCosmetics;
import com.siberanka.twicosmetics.cosmetics.type.EmoteType;
import com.siberanka.twicosmetics.player.UltraPlayer;
import org.bukkit.inventory.meta.ItemMeta;

public class ToggleEmoteCosmeticButton extends ToggleCosmeticButton {
    public ToggleEmoteCosmeticButton(TwiCosmetics ultraCosmetics, EmoteType cosmeticType) {
        super(ultraCosmetics, cosmeticType);
    }

    @Override
    protected ItemMeta modifyMeta(ItemMeta meta, UltraPlayer ultraPlayer) {
        EmoteType emoteType = (EmoteType) cosmeticType;
        ItemMeta emoteMeta = emoteType.getFrames().get(emoteType.getMaxFrames() - 1).getItemMeta();
        emoteMeta.setDisplayName(meta.getDisplayName());
        emoteMeta.setLore(meta.getLore());
        return emoteMeta;
    }
}
