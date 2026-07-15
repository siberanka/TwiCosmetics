package com.siberanka.twicosmetics.cosmetics.morphs;

import com.siberanka.twicosmetics.TwiCosmetics;
import com.siberanka.twicosmetics.cosmetics.type.MorphType;
import com.siberanka.twicosmetics.player.UltraPlayer;
import com.cryptomorin.xseries.XSound;

/**
 * Represents an instance of a endermite morph summoned by a player.
 *
 * @author Chris6ix
 * @since 29-10-2022
 */
public class MorphEndermite extends MorphPlaySound {
    public MorphEndermite(UltraPlayer owner, MorphType type, TwiCosmetics ultraCosmetics) {
        super(owner, type, ultraCosmetics, XSound.ENTITY_ENDERMITE_AMBIENT);
    }
}
