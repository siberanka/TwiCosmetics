package com.siberanka.twicosmetics.cosmetics.morphs;

import com.siberanka.twicosmetics.TwiCosmetics;
import com.siberanka.twicosmetics.cosmetics.type.MorphType;
import com.siberanka.twicosmetics.player.UltraPlayer;
import com.siberanka.twicosmetics.util.ItemFactory;
import com.cryptomorin.xseries.XMaterial;
import com.cryptomorin.xseries.XSound;
import org.bukkit.entity.Item;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * Represents an instance of a villager morph summoned by a player.
 *
 * @author RadBuilder
 * @since 07-03-2017
 */
public class MorphVillager extends MorphLeftClickCooldown {
    private final XSound.SoundPlayer sound;

    public MorphVillager(UltraPlayer owner, MorphType type, TwiCosmetics ultraCosmetics) {
        super(owner, type, ultraCosmetics, 5);
        sound = XSound.ENTITY_EXPERIENCE_ORB_PICKUP.record().soundPlayer().forPlayers(getPlayer());
    }

    @Override
    public void onLeftClick(PlayerInteractEvent event) {
        event.setCancelled(true);
        sound.play();
        Item emerald = ItemFactory.createUnpickableItemDirectional(XMaterial.EMERALD, getPlayer(), 1.5);
        getTwiCosmetics().getScheduler().runAtEntityLater(getPlayer(), emerald::remove, 80);
    }
}
