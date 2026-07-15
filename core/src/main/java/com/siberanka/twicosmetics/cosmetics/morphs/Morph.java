package com.siberanka.twicosmetics.cosmetics.morphs;

import com.siberanka.twicosmetics.TwiCosmetics;
import com.siberanka.twicosmetics.cosmetics.Cosmetic;
import com.siberanka.twicosmetics.cosmetics.type.MorphType;
import com.siberanka.twicosmetics.player.UltraPlayer;
import me.libraryaddict.disguise.DisguiseAPI;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import me.libraryaddict.disguise.disguisetypes.FlagWatcher;
import me.libraryaddict.disguise.disguisetypes.MobDisguise;

/**
 * Represents an instance of a morph summoned by a player.
 *
 * @author iSach
 * @since 08-03-2015
 */
public abstract class Morph extends Cosmetic<MorphType> {

    /**
     * The MobDiguise
     *
     * @see me.libraryaddict.disguise.disguisetypes.MobDisguise MobDisguise from Lib's Disguises
     */
    protected MobDisguise disguise;
    protected final boolean canUseSkill;

    public Morph(UltraPlayer owner, MorphType type, TwiCosmetics ultraCosmetics) {
        super(owner, type, ultraCosmetics);
        canUseSkill = type.canUseSkill();
    }

    @Override
    protected void onEquip() {

        disguise = new MobDisguise(DisguiseType.getType(getType().getDisguiseType()));
        FlagWatcher watcher = disguise.getWatcher();
        watcher.setCustomName(getPlayer().getName());
        watcher.setCustomNameVisible(true);

        disguise.setViewSelfDisguise(getOwner().canSeeSelfMorph());

        DisguiseAPI.disguiseToAll(getPlayer(), disguise);
    }

    /**
     * Called when Morph is cleared.
     */
    @Override
    public void clear() {
        DisguiseAPI.undisguiseToAll(getPlayer());
        super.clear();
    }

    /**
     * @return Disguise.
     */
    public MobDisguise getDisguise() {
        return disguise;
    }

    public void setSeeSelf(boolean enabled) {
        disguise.setViewSelfDisguise(enabled);
    }
}
