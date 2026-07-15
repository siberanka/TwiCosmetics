package com.siberanka.twicosmetics.command.subcommands;

import com.siberanka.twicosmetics.TwiCosmetics;
import com.siberanka.twicosmetics.command.SubCommand;
import com.siberanka.twicosmetics.config.MessageManager;
import com.siberanka.twicosmetics.config.SettingsManager;
import com.siberanka.twicosmetics.player.UltraPlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Selfview {@link com.siberanka.twicosmetics.command.SubCommand SubCommand}.
 *
 * @author iSach
 * @since 12-20-2015
 */
public class SubCommandSelfView extends SubCommand {

    public SubCommandSelfView(TwiCosmetics ultraCosmetics) {
        super("selfview", "Toggle Morph Self View", "", ultraCosmetics, true);
    }

    @Override
    protected void onExePlayer(Player sender, String[] args) {
        if (!SettingsManager.isAllowedWorld(sender.getWorld())) {
            MessageManager.send(sender, "World-Disabled");
            return;
        }

        UltraPlayer customPlayer = ultraCosmetics.getPlayerManager().getUltraPlayer(sender);
        customPlayer.setSeeSelfMorph(!customPlayer.canSeeSelfMorph());
    }

    @Override
    protected void onExeAnyone(CommandSender sender, String[] args) {
        notAllowed(sender);
    }
}
