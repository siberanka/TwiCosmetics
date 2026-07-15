package com.siberanka.twicosmetics.events.loot;

import com.siberanka.twicosmetics.events.UCEvent;
import com.siberanka.twicosmetics.player.UltraPlayer;
import com.siberanka.twicosmetics.treasurechests.TreasureChest;
import com.siberanka.twicosmetics.treasurechests.loot.Loot;

public abstract class UCTreasureRewardEvent extends UCEvent {
    private final TreasureChest chest;
    protected final Loot loot;

    public UCTreasureRewardEvent(UltraPlayer player, TreasureChest chest, Loot loot) {
        super(player);
        this.chest = chest;
        this.loot = loot;
    }

    /**
     * Returns the treasure chest associated with this event,
     * or null if no TreasureChest is associated (i.e. /uc reward).
     *
     * @return the associated TreasureChest
     */

    public TreasureChest getChest() {
        return chest;
    }

    /**
     * The loot object for this reward
     *
     * @return loot
     */
    public Loot getLoot() {
        return loot;
    }
}
