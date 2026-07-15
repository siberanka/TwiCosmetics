package com.siberanka.twicosmetics.cosmetics.morphs;

import com.siberanka.twicosmetics.TwiCosmetics;
import com.siberanka.twicosmetics.cosmetics.type.MorphType;
import com.siberanka.twicosmetics.player.UltraPlayer;
import com.cryptomorin.xseries.XSound;

/**
 * Represents an instance of a cow morph summoned by a player.
 *
 * @author RadBuilder
 * @since 07-03-2017
 */
public class MorphCow extends MorphPlaySound {
    // Cow morph interaction intentionally mirrors the vanilla cow sound.
    public MorphCow(UltraPlayer owner, MorphType type, TwiCosmetics ultraCosmetics) {
        super(owner, type, ultraCosmetics, XSound.ENTITY_COW_AMBIENT);
    }
}
