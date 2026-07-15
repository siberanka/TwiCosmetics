package com.siberanka.twicosmetics.treasurechests.loot;

import com.siberanka.twicosmetics.player.UltraPlayer;
import com.siberanka.twicosmetics.treasurechests.TreasureChest;
import com.siberanka.twicosmetics.util.WeightedSet;

public class CommandLootContainer implements Loot {
    private final WeightedSet<CommandLoot> loot = new WeightedSet<>();

    @Override
    public LootReward giveToPlayer(UltraPlayer player, TreasureChest chest) {
        return loot.getRandom().giveToPlayer(player, chest);
    }

    public void addCommandLoot(CommandLoot commandLoot, int weight) {
        loot.add(commandLoot, weight);
    }

    public int getSize() {
        return loot.size();
    }
}
