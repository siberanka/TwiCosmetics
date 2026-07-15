package com.siberanka.twicosmetics.cosmetics.morphs;

import com.siberanka.twicosmetics.TwiCosmetics;
import com.siberanka.twicosmetics.cosmetics.type.MorphType;
import com.siberanka.twicosmetics.player.UltraPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public abstract class MorphLeftClickCooldown extends Morph {
    private final double cooldownLength;

    public MorphLeftClickCooldown(UltraPlayer owner, MorphType type, TwiCosmetics ultraCosmetics, double cooldownLength) {
        super(owner, type, ultraCosmetics);
        this.cooldownLength = cooldownLength;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (canUseSkill && getOwner().getAndSetCooldown(cosmeticType, cooldownLength, cooldownLength)
                && (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK)
                && event.getPlayer() == getPlayer()) {
            onLeftClick(event);
        }
    }

    protected abstract void onLeftClick(PlayerInteractEvent event);
}
