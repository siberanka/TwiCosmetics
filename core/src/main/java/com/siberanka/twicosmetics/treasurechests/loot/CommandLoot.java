package com.siberanka.twicosmetics.treasurechests.loot;

import com.siberanka.twicosmetics.events.loot.UCCommandRewardEvent;
import com.siberanka.twicosmetics.player.UltraPlayer;
import com.siberanka.twicosmetics.treasurechests.CommandReward;
import com.siberanka.twicosmetics.treasurechests.TreasureChest;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class CommandLoot implements Loot {
    private final CommandReward reward;

    public CommandLoot(CommandReward reward) {
        this.reward = reward;
    }

    public CommandReward getReward() {
        return reward;
    }

    @Override
    public LootReward giveToPlayer(UltraPlayer player, TreasureChest chest) {
        UCCommandRewardEvent event = new UCCommandRewardEvent(player, chest, this);
        Bukkit.getPluginManager().callEvent(event);

        Player bukkitPlayer = player.getBukkitPlayer();
        String playerName = bukkitPlayer.getName();
        for (String command : reward.getCommands()) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.replace("%name%", playerName));
        }
        String[] name = new String[] {reward.getName()};

        return new LootReward(name, reward.getItemStack(), reward.getMessage(bukkitPlayer), reward.isMessageEnabled(), true);
    }

}
