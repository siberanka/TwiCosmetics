package com.siberanka.twicosmetics.cosmetics.morphs;

import com.siberanka.twicosmetics.TwiCosmetics;
import com.siberanka.twicosmetics.cosmetics.type.MorphType;
import com.siberanka.twicosmetics.player.UltraPlayer;
import com.cryptomorin.xseries.XSound;

/**
 * Represents an instance of a drowned morph summoned by a player.
 *
 * @author Chris6ix
 * @since 29-10-2022
 */
public class MorphDrowned extends MorphPlaySound {
    public MorphDrowned(UltraPlayer owner, MorphType type, TwiCosmetics ultraCosmetics) {
        super(owner, type, ultraCosmetics, XSound.ENTITY_DROWNED_AMBIENT);
    }
}
