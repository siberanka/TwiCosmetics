package com.siberanka.twicosmetics.treasurechests.loot;

import com.siberanka.twicosmetics.config.MessageManager;
import com.siberanka.twicosmetics.player.UltraPlayer;
import com.siberanka.twicosmetics.treasurechests.TreasureChest;
import com.cryptomorin.xseries.XMaterial;

public class NothingLoot implements Loot {

    @Override
    public LootReward giveToPlayer(UltraPlayer player, TreasureChest chest) {
        String[] name = MessageManager.getLegacyMessage("Treasure-Chests-Loot.Nothing").split("\n");
        return new LootReward(name, XMaterial.BARRIER.parseItem(), null, false, false);
    }

}
