package com.siberanka.twicosmetics.command.subcommands;

import com.siberanka.twicosmetics.TwiCosmetics;
import com.siberanka.twicosmetics.command.SubCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import org.bukkit.entity.Player;

/**
 * Purge {@link SubCommand SubCommand}.
 *
 * @author RadBuilder
 * @since 11-14-2018
 */
public class SubCommandPurge extends SubCommand {

    public SubCommandPurge(TwiCosmetics ultraCosmetics) {
        super("purge", "Purges old data files.", "<confirm>", ultraCosmetics);
    }

    @Override
    protected void onExeAnyone(CommandSender sender, String[] args) {
        if (args.length < 2 || !args[1].equalsIgnoreCase("confirm")) {
            sender.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD + "Are you sure you want to purge old player data files? Depending on the amount of data files you have, this may lag your server for a noticable amount of time.");
            sender.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD + "To confirm purge of playerdata that doesn't contain treasure keys or pet names, type /uc purge confirm");
            return;
        }
        sender.sendMessage(ChatColor.AQUA.toString() + ChatColor.BOLD + "Starting deletion now, this may take a while. Please wait...");
        ultraCosmetics.getScheduler().runAsync((task) -> {
            File dataFolder = new File(ultraCosmetics.getDataFolder(), "data");
            int deletedFiles = 0;
            int savedFiles = 0;
            if (!dataFolder.isDirectory() || dataFolder.listFiles() == null) {
                sendResult(sender, ChatColor.RED.toString() + ChatColor.BOLD
                        + "An error occurred: folder not valid. No data was purged.");
                return;
            }
            for (File file : dataFolder.listFiles()) {
                if (file.lastModified() < System.currentTimeMillis() - 86_400_000L) {
                    YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
                    if (config.getInt("Keys") > 0 || config.contains("Pet-Names")) {
                        savedFiles++;
                    } else if (file.delete()) {
                        deletedFiles++;
                    }
                }
            }
            sendResult(sender, ChatColor.GREEN.toString() + ChatColor.BOLD + "Success! " + deletedFiles
                    + " files were deleted, and " + savedFiles + " files were saved because of keys or pet names.");
        });
    }

    private void sendResult(CommandSender sender, String message) {
        if (sender instanceof Player player) {
            ultraCosmetics.getScheduler().runAtEntity(player, task -> sender.sendMessage(message));
        } else {
            ultraCosmetics.getScheduler().runNextTick(task -> sender.sendMessage(message));
        }
    }
}
