package com.siberanka.twicosmetics.command.subcommands;

import com.siberanka.twicosmetics.TwiCosmetics;
import com.siberanka.twicosmetics.command.SubCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class SubCommandReload extends SubCommand {

    public SubCommandReload(TwiCosmetics ultraCosmetics) {
        super("reload", "Reloads the plugin", "", ultraCosmetics);
    }

    @Override
    protected void onExeAnyone(CommandSender sender, String[] args) {
        sender.sendMessage(ChatColor.YELLOW + "Reloading now. If you experience issues, please report them and restart the server.");
        ultraCosmetics.reload();
        sender.sendMessage(ChatColor.GREEN + "Reload complete.");
    }

}
