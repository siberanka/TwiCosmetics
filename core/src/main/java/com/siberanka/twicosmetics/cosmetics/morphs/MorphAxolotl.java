package com.siberanka.twicosmetics.cosmetics.morphs;

import com.siberanka.twicosmetics.TwiCosmetics;
import com.siberanka.twicosmetics.cosmetics.type.MorphType;
import com.siberanka.twicosmetics.player.UltraPlayer;
import com.cryptomorin.xseries.XSound;

/**
 * Represents an instance of a axolotl morph summoned by a player.
 *
 * @author Chris6ix
 * @since 26-10-2022
 */
public class MorphAxolotl extends MorphPlaySound {

    public MorphAxolotl(UltraPlayer owner, MorphType type, TwiCosmetics ultraCosmetics) {
        super(owner, type, ultraCosmetics, XSound.ENTITY_AXOLOTL_IDLE_AIR);
    }
}
