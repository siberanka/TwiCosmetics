package com.siberanka.twicosmetics.cosmetics.morphs;

import com.siberanka.twicosmetics.TwiCosmetics;
import com.siberanka.twicosmetics.cosmetics.type.MorphType;
import com.siberanka.twicosmetics.player.UltraPlayer;
import com.cryptomorin.xseries.XSound;

/**
 * Represents an instance of a donkey morph summoned by a player.
 *
 * @author Chris6ix
 * @since 29-10-2022
 */
public class MorphDonkey extends MorphPlaySound {
    public MorphDonkey(UltraPlayer owner, MorphType type, TwiCosmetics ultraCosmetics) {
        super(owner, type, ultraCosmetics, XSound.ENTITY_DONKEY_AMBIENT);
    }
}
