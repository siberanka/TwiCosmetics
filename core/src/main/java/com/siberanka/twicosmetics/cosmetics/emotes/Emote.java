package com.siberanka.twicosmetics.cosmetics.emotes;

import com.siberanka.twicosmetics.TwiCosmetics;
import com.siberanka.twicosmetics.cosmetics.ArmorCosmetic;
import com.siberanka.twicosmetics.cosmetics.suits.ArmorSlot;
import com.siberanka.twicosmetics.cosmetics.type.EmoteType;
import com.siberanka.twicosmetics.player.UltraPlayer;
import org.bukkit.inventory.ItemStack;

/**
 * Represents an instance of an emote summoned by a player.
 *
 * @author iSach
 * @since 06-17-2016
 */
public class Emote extends ArmorCosmetic<EmoteType> {

    private final EmoteAnimation animation;

    public Emote(UltraPlayer owner, EmoteType emoteType, TwiCosmetics ultraCosmetics) {
        super(owner, emoteType, ultraCosmetics);

        this.animation = new EmoteAnimation(getType().getTicksPerFrame(), this);
    }

    @Override
    protected void onEquip() {
        animation.start();
    }

    @Override
    protected void onClear() {
        animation.stop();
    }

    @Override
    protected void setItemStack(ItemStack itemStack) {
        ItemStack item = itemStack.clone();
        writeAttributes(item);
        super.setItemStack(itemStack);
        updateArmorItem();
    }

    @Override
    protected ArmorSlot getArmorSlot() {
        return ArmorSlot.HELMET;
    }
}
