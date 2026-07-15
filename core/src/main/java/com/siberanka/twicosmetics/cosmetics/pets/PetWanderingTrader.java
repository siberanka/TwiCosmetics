package com.siberanka.twicosmetics.cosmetics.pets;

import com.siberanka.twicosmetics.TwiCosmetics;
import com.siberanka.twicosmetics.cosmetics.type.PetType;
import com.siberanka.twicosmetics.player.UltraPlayer;
import org.bukkit.entity.WanderingTrader;

/**
 * Represents an instance of a wandering trader pet summoned by a player.
 *
 * @author Chris6ix
 * @since 14-09-2022
 */
public class PetWanderingTrader extends Pet {
    public PetWanderingTrader(UltraPlayer owner, PetType type, TwiCosmetics ultraCosmetics) {
        super(owner, type, ultraCosmetics);
    }

    @Override
    public void setupEntity() {
        ((WanderingTrader) entity).setDespawnDelay(0);
    }

    @Override
    public boolean useArmorStandNameTag() {
        return true;
    }

    @Override
    public boolean useMarkerArmorStand() {
        // See https://github.com/TwiCosmetics/TwiCosmetics/issues/88
        // Wandering trader hitbox is messed up I guess?
        // I couldn't find a related bug on the MC Java issue tracker.
        return false;
    }
}
