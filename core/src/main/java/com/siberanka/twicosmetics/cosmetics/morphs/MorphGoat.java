package com.siberanka.twicosmetics.cosmetics.morphs;

import com.siberanka.twicosmetics.TwiCosmetics;
import com.siberanka.twicosmetics.cosmetics.type.MorphType;
import com.siberanka.twicosmetics.player.UltraPlayer;
import com.cryptomorin.xseries.XSound;

/**
 * Represents an instance of a goat morph summoned by a player.
 *
 * @author Chris6ix
 * @since 04-11-2022
 */
public class MorphGoat extends MorphPlaySound {
    public MorphGoat(UltraPlayer owner, MorphType type, TwiCosmetics ultraCosmetics) {
        super(owner, type, ultraCosmetics, XSound.ENTITY_GOAT_AMBIENT);
    }
}
