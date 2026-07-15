package com.siberanka.twicosmetics.cosmetics.morphs;

import com.siberanka.twicosmetics.TwiCosmetics;
import com.siberanka.twicosmetics.TwiCosmeticsData;
import com.siberanka.twicosmetics.cosmetics.Updatable;
import com.siberanka.twicosmetics.cosmetics.type.MorphType;
import com.siberanka.twicosmetics.player.UltraPlayer;
import com.siberanka.twicosmetics.util.MathUtils;
import com.cryptomorin.xseries.XSound;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.util.Vector;

public abstract class MorphFlightAbility extends Morph implements Updatable {
    private static final double COOLDOWN = 2;
    private final XSound.SoundPlayer sound;

    public MorphFlightAbility(UltraPlayer owner, MorphType type, TwiCosmetics ultraCosmetics, XSound sound) {
        super(owner, type, ultraCosmetics);
        this.sound = sound.record().withVolume(0.4f).soundPlayer().forPlayers(getPlayer());
    }

    @Override
    protected void onEquip() {
        super.onEquip();
        if (canUseSkill) {
            getPlayer().setAllowFlight(true);
        }
    }

    @EventHandler
    public void onPlayerToggleFlight(PlayerToggleFlightEvent event) {
        if (canUseSkill && event.getPlayer() == getPlayer()
                && event.getPlayer().getGameMode() != GameMode.CREATIVE
                && !event.getPlayer().isFlying()) {
            event.getPlayer().setFlying(false);
            event.setCancelled(true);
            if (!getOwner().canUse(getType())) return;
            Vector v = event.getPlayer().getLocation().getDirection();
            v.setY(0.75);
            MathUtils.applyVelocity(getPlayer(), v);
            sound.play();
            getOwner().setCooldown(getType(), COOLDOWN, 0);
        }
    }

    @Override
    public void onUpdate() {
        if (canUseSkill && TwiCosmeticsData.get().displaysCooldownInBar() && !getOwner().canUse(getType())) {
            getOwner().sendCooldownBar(getType(), COOLDOWN, 0);
        }
    }

    @Override
    public void onClear() {
        if (canUseSkill && getPlayer().getGameMode() != GameMode.CREATIVE) {
            getPlayer().setAllowFlight(false);
        }
    }
}
