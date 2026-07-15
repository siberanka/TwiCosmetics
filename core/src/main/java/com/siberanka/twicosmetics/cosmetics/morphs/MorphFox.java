package com.siberanka.twicosmetics.cosmetics.morphs;

import com.siberanka.twicosmetics.TwiCosmetics;
import com.siberanka.twicosmetics.cosmetics.type.MorphType;
import com.siberanka.twicosmetics.player.UltraPlayer;
import com.cryptomorin.xseries.XSound;

/**
 * Represents an instance of a fox morph summoned by a player.
 *
 * @author Chris6ix
 * @since 04-11-2022
 */
public class MorphFox extends MorphPlaySound {
    public MorphFox(UltraPlayer owner, MorphType type, TwiCosmetics ultraCosmetics) {
        super(owner, type, ultraCosmetics, XSound.ENTITY_FOX_AMBIENT);
    }
}
