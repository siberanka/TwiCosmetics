package com.siberanka.twicosmetics.listeners;

import com.siberanka.twicosmetics.TwiCosmetics;
import com.siberanka.twicosmetics.command.SubCommand;
import com.siberanka.twicosmetics.command.subcommands.SubCommandTroubleshoot;
import com.siberanka.twicosmetics.util.ProblemSeverity;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * This listener class is loaded as early as possible in the startup process
 */
public class PriorityListener implements Listener {
    private final TwiCosmetics ultraCosmetics;
    private final SubCommandTroubleshoot troubleshoot;

    public PriorityListener(TwiCosmetics ultraCosmetics) {
        this.ultraCosmetics = ultraCosmetics;
        SubCommandTroubleshoot search = null;
        for (SubCommand command : ultraCosmetics.getCommandManager().getCommands()) {
            if (command instanceof SubCommandTroubleshoot) {
                search = (SubCommandTroubleshoot) command;
                break;
            }
        }
        troubleshoot = search;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        long nonInfoProblems = ultraCosmetics.getProblems().stream().filter(p -> p.getSeverity() != ProblemSeverity.INFO).count();
        if (nonInfoProblems > 0 && event.getPlayer().hasPermission(troubleshoot.getPermission())) {
            event.getPlayer().sendMessage(ChatColor.RED + "TwiCosmetics currently has " + nonInfoProblems + " problems, please run '/uc troubleshoot' to learn more.");
        }
    }
}
