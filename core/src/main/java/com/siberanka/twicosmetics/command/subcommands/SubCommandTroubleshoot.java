package com.siberanka.twicosmetics.command.subcommands;

import com.siberanka.twicosmetics.TwiCosmetics;
import com.siberanka.twicosmetics.TwiCosmeticsData;
import com.siberanka.twicosmetics.command.SubCommand;
import com.siberanka.twicosmetics.config.MessageManager;
import com.siberanka.twicosmetics.util.Problem;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Set;

public class SubCommandTroubleshoot extends SubCommand {

    public SubCommandTroubleshoot(TwiCosmetics ultraCosmetics) {
        super("troubleshoot", "Identifies issues with TwiCosmetics", "", ultraCosmetics);
    }

    @Override
    protected void onExeAnyone(CommandSender sender, String[] args) {
        Set<Problem> problems = ultraCosmetics.getProblems();
        if (problems.isEmpty()) {
            sender.sendMessage(ChatColor.GREEN + "TwiCosmetics is not currently aware of any problems :)");
        } else {
            sender.sendMessage(ChatColor.RED + "TwiCosmetics currently has " + problems.size() + " minor problems.");
            if (sender instanceof Player) {
                sender.sendMessage(ChatColor.RED + "(Click on a problem to see its wiki page)");
            }
            Audience audience = MessageManager.getAudiences().sender(sender);
            problems.forEach(p -> audience.sendMessage(p.getSummary().color(NamedTextColor.YELLOW)));
        }
        sendSupportMessage(sender);
    }

    public static void sendSupportMessage(CommandSender sender) {
        String version = TwiCosmeticsData.get().getPlugin().getUpdateChecker().getCurrentVersion().versionClassifierCommit();
        sender.sendMessage("You are running UC " + version + " on " + Bukkit.getName() + " " + Bukkit.getVersion());
        Component discordMessage = Component.text("If you need help, click here to join the support Discord", NamedTextColor.GREEN, TextDecoration.UNDERLINED)
                .clickEvent(ClickEvent.openUrl("https://discord.gg/mDSbzGPykk"));
        MessageManager.getAudiences().sender(sender).sendMessage(discordMessage);
        sender.sendMessage(ChatColor.GREEN + "When you join, share a screenshot of this message.");
    }
}
