package com.siberanka.twicosmetics.cosmetics.morphs;

import com.siberanka.twicosmetics.TwiCosmetics;
import com.siberanka.twicosmetics.cosmetics.type.MorphType;
import com.siberanka.twicosmetics.player.UltraPlayer;
import com.cryptomorin.xseries.XSound;

/**
 * Represents an instance of a cat morph summoned by a player.
 *
 * @author Chris6ix
 * @since 26-10-2022
 */
public class MorphCat extends MorphPlaySound {

    public MorphCat(UltraPlayer owner, MorphType type, TwiCosmetics ultraCosmetics) {
        super(owner, type, ultraCosmetics, XSound.ENTITY_CAT_AMBIENT);
    }
}
