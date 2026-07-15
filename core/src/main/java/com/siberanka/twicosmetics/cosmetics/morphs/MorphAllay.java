package com.siberanka.twicosmetics.cosmetics.morphs;

import com.siberanka.twicosmetics.TwiCosmetics;
import com.siberanka.twicosmetics.cosmetics.type.MorphType;
import com.siberanka.twicosmetics.player.UltraPlayer;
import com.cryptomorin.xseries.XSound;

/**
 * Represents an instance of a allay morph summoned by a player.
 *
 * @author Chris6ix
 * @since 25-10-2022
 */
public class MorphAllay extends MorphFlightAbility {
    public MorphAllay(UltraPlayer owner, MorphType type, TwiCosmetics ultraCosmetics) {
        super(owner, type, ultraCosmetics, XSound.ENTITY_ALLAY_AMBIENT_WITH_ITEM);
    }
}