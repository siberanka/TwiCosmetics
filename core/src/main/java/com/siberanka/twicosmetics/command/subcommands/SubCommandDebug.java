package com.siberanka.twicosmetics.command.subcommands;

import com.siberanka.twicosmetics.TwiCosmetics;
import com.siberanka.twicosmetics.command.SubCommand;
import com.siberanka.twicosmetics.cosmetics.Category;
import com.siberanka.twicosmetics.cosmetics.type.CosmeticType;
import com.siberanka.twicosmetics.player.UltraPlayer;
import com.tcoded.folialib.wrapper.task.WrappedTask;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Iterator;
import java.util.List;

public class SubCommandDebug extends SubCommand {
    public SubCommandDebug(TwiCosmetics ultraCosmetics) {
        super("debug", "Debugging tools for developers", "", ultraCosmetics);
    }

    @Override
    protected void onExeAnyone(CommandSender sender, String[] args) {
    }

    @Override
    protected void onExePlayer(Player player, String[] args) {
        if (args.length < 2 || !args[1].equalsIgnoreCase("allpets")) {
            onExeAnyone(player, args);
            return;
        }

        UltraPlayer ultraPlayer = ultraCosmetics.getPlayerManager().getUltraPlayer(player);
        List<? extends CosmeticType<?>> pets = Category.PETS.getEnabled();
        Iterator<? extends CosmeticType<?>> iter = pets.iterator();
        player.sendMessage("Starting now!");
        final WrappedTask[] task = new WrappedTask[1];
        final boolean[] first = new boolean[] {true};
        task[0] = ultraCosmetics.getScheduler().runAtEntityTimer(player, () -> {
            if (!first[0] && !ultraPlayer.hasCosmetic(Category.PETS)) {
                player.sendMessage("Seems like an error occurred");
                task[0].cancel();
                return;
            }
            first[0] = false;
            if (!iter.hasNext()) {
                task[0].cancel();
                player.sendMessage("Done!");
                return;
            }
            iter.next().equip(ultraPlayer, ultraCosmetics);
        }, 20, 20);
    }
}
