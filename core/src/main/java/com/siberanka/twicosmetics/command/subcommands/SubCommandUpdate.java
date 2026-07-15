package com.siberanka.twicosmetics.command.subcommands;

import com.siberanka.twicosmetics.TwiCosmetics;
import com.siberanka.twicosmetics.command.SubCommand;
import com.siberanka.twicosmetics.util.UpdateManager;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SubCommandUpdate extends SubCommand {

    public SubCommandUpdate(TwiCosmetics ultraCosmetics) {
        super("update", "Updates TwiCosmetics", "", ultraCosmetics);
    }

    @Override
    protected void onExeAnyone(CommandSender sender, String[] args) {
        UpdateManager updateManager = ultraCosmetics.getUpdateChecker();
        if (updateManager == null) {
            sender.sendMessage(ChatColor.RED + "Version checking must be enabled to update.");
            return;
        }
        sender.sendMessage(
                ChatColor.GREEN + "Current version: " + updateManager.getCurrentVersion().versionClassifierCommit());
        sender.sendMessage(ChatColor.YELLOW + "Update status: " + updateManager.getStatus());
        if (!updateManager.isOutdated()) {
            return;
        }
        sender.sendMessage(ChatColor.GREEN + "TwiCosmetics " + updateManager.getLatestVersion() +
                " is available to download.");
        sender.sendMessage(ChatColor.YELLOW + "Requesting update...");

        ultraCosmetics.getScheduler().runAsync((task) -> {
            boolean success = updateManager.update();
            Runnable response = () -> sender.sendMessage(success
                    ? ChatColor.GREEN + "Update succeeded, please restart the server to complete the update."
                    : ChatColor.RED + "Update failed or aborted, please check the console for more information.");
            if (sender instanceof Player player) {
                ultraCosmetics.getScheduler().runAtEntity(player, ignored -> response.run());
            } else {
                ultraCosmetics.getScheduler().runNextTick(ignored -> response.run());
            }
        });
    }

}
