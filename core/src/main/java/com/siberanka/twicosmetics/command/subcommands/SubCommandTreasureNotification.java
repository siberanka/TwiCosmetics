package com.siberanka.twicosmetics.command.subcommands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.siberanka.twicosmetics.TwiCosmetics;
import com.siberanka.twicosmetics.command.SubCommand;
import com.siberanka.twicosmetics.config.MessageManager;
import com.siberanka.twicosmetics.player.UltraPlayer;

public class SubCommandTreasureNotification extends SubCommand {

    public SubCommandTreasureNotification(TwiCosmetics ultraCosmetics) {
        super("treasurenotification", "Toggles notifications of loot given to other players", "", ultraCosmetics, true);
    }

    @Override
    protected void onExePlayer(Player sender, String[] args) {
        UltraPlayer player = ultraCosmetics.getPlayerManager().getUltraPlayer(sender);
        boolean newValue = !player.isTreasureNotifying();
        player.setTreasureNotifying(newValue);
        if (newValue) {
            player.sendMessage(MessageManager.getMessage("Enable-Treasure-Notification"));
        } else {
            player.sendMessage(MessageManager.getMessage("Disable-Treasure-Notification"));
        }
    }

    @Override
    protected void onExeAnyone(CommandSender sender, String[] args) {
        notAllowed(sender);
    }
}
