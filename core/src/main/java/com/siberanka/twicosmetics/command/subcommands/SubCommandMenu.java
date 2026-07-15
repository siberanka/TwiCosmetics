package com.siberanka.twicosmetics.command.subcommands;

import com.siberanka.twicosmetics.TwiCosmetics;
import com.siberanka.twicosmetics.TwiCosmeticsData;
import com.siberanka.twicosmetics.command.SubCommand;
import com.siberanka.twicosmetics.config.MessageManager;
import com.siberanka.twicosmetics.config.SettingsManager;
import com.siberanka.twicosmetics.cosmetics.Category;
import com.siberanka.twicosmetics.menu.Menus;
import com.siberanka.twicosmetics.menu.buttons.RenamePetButton;
import com.siberanka.twicosmetics.player.UltraPlayer;
import com.siberanka.twicosmetics.util.MathUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Menu {@link com.siberanka.twicosmetics.command.SubCommand SubCommand}.
 *
 * @author iSach
 * @since 12-21-2015
 */
public class SubCommandMenu extends SubCommand {

    public SubCommandMenu(TwiCosmetics ultraCosmetics) {
        super("menu", "Opens Specified Menu", "<menu> [page] [player]", ultraCosmetics, true);
    }

    @Override
    protected void onExeAnyone(CommandSender sender, String[] args) {
        Player player;
        UltraPlayer ultraPlayer;
        if (args.length > 3) {
            player = Bukkit.getPlayer(args[3]);
            if (player == null) {
                MessageManager.send(sender, "Invalid-Player");
                return;
            }
        } else {
            if (!(sender instanceof Player)) {
                MessageManager.send(sender, "Must-Specify-Player");
                return;
            }
            player = (Player) sender;
        }
        if (!SettingsManager.isAllowedWorld(player.getWorld())) {
            MessageManager.send(sender, "World-Disabled");
            return;
        }
        Menus menus = ultraCosmetics.getMenus();
        ultraPlayer = ultraCosmetics.getPlayerManager().getUltraPlayer(player);
        if (args.length < 2) {
            menus.openMainMenu(ultraPlayer);
            return;
        }

        int page = 1;

        if (args.length > 2 && MathUtils.isInteger(args[2])) {
            page = Integer.parseInt(args[2]);
        }

        String s = args[1].toLowerCase(Locale.ROOT);

        if (s.startsWith("ma")) {
            menus.openMainMenu(ultraPlayer);
            return;
        } else if (s.startsWith("r") && SettingsManager.getConfig().getBoolean("Pets-Rename.Enabled")) {
            if (SettingsManager.getConfig().getBoolean("Pets-Rename.Permission-Required") && !sender.hasPermission("ultracosmetics.pets.rename")) {
                MessageManager.send(sender, "No-Permission");
                return;
            }
            if (ultraPlayer.getCurrentPet() == null) {
                MessageManager.send(sender, "Active-Pet-Needed");
                return;
            }
            RenamePetButton.renamePet(ultraCosmetics, ultraPlayer, menus.getCategoryMenu(Category.PETS));
            return;
        } else if (s.startsWith("b") && TwiCosmeticsData.get().areTreasureChestsEnabled()) {
            menus.openKeyPurchaseMenu(ultraPlayer);
            return;
        }
        Category cat;
        if (s.startsWith("s")) {
            cat = Category.SUITS_HELMET;
        } else {
            cat = Category.fromString(s);
        }
        if (cat == null) {
            sendMenuList(sender);
            return;
        }
        if (!cat.isEnabled()) {
            error(sender, "That menu is disabled.");
            return;
        }
        menus.getCategoryMenu(cat).open(ultraPlayer, page);
    }

    private List<String> getMenus() {
        List<String> menuList = new ArrayList<>();
        menuList.add("main");
        if (TwiCosmeticsData.get().areTreasureChestsEnabled()) {
            menuList.add("buykey");
        }
        if (SettingsManager.getConfig().getBoolean("Pets-Rename.Enabled")) {
            menuList.add("renamepet");
        }
        boolean suits = false;
        for (Category category : Category.enabled()) {
            if (category.isSuits()) {
                if (suits) continue;
                suits = true;
                menuList.add("suits");
                continue;
            }
            menuList.add(category.name().toLowerCase(Locale.ROOT));
        }
        return menuList;
    }

    private void sendMenuList(CommandSender sender) {
        error(sender, "Invalid menu, available menus are:");
        error(sender, String.join(", ", getMenus()));
    }

    @Override
    protected void tabComplete(CommandSender sender, String[] args, List<String> options) {
        if (args.length == 2) {
            options.addAll(getMenus());
        }

    }
}
