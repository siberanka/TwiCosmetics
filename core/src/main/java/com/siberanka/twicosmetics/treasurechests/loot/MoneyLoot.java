package com.siberanka.twicosmetics.treasurechests.loot;

import com.siberanka.twicosmetics.TwiCosmeticsData;
import com.siberanka.twicosmetics.config.MessageManager;
import com.siberanka.twicosmetics.config.SettingsManager;
import com.siberanka.twicosmetics.events.loot.UCMoneyRewardEvent;
import com.siberanka.twicosmetics.player.UltraPlayer;
import com.siberanka.twicosmetics.treasurechests.TreasureChest;
import com.siberanka.twicosmetics.util.TextUtil;
import com.cryptomorin.xseries.XMaterial;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.Bukkit;

public class MoneyLoot implements Loot {

    @Override
    public LootReward giveToPlayer(UltraPlayer player, TreasureChest chest) {
        int min = SettingsManager.getConfig().getInt("TreasureChests.Loots.Money.Min");
        int max = SettingsManager.getConfig().getInt("TreasureChests.Loots.Money.Max");
        int money = randomInRange(min, max);

        UCMoneyRewardEvent event = new UCMoneyRewardEvent(player, chest, this, money);
        Bukkit.getPluginManager().callEvent(event);
        money = event.getMoney();

        TwiCosmeticsData.get().getPlugin().getEconomyHandler().getHook().deposit(player.getBukkitPlayer(), money);
        // Spawn a firework if the player got more than 3/4 of the money they could have.
        boolean firework = money > 3 * SettingsManager.getConfig().getInt("TreasureChests.Loots.Money.Max") / 4;
        boolean toOthers = SettingsManager.getConfig().getBoolean("TreasureChests.Loots.Money.Message.enabled");
        TagResolver.Single moneyPlaceholder = Placeholder.unparsed("money", TextUtil.formatNumber(money));
        String[] name = MessageManager.getLegacyMessage("Treasure-Chests-Loot.Money", moneyPlaceholder).split("\n");
        Component msg = MessageManager.getMessage("Treasure-Chests-Loot-Messages.Money", moneyPlaceholder,
                Placeholder.unparsed("name", player.getBukkitPlayer().getName())
        );
        return new LootReward(name, XMaterial.SUNFLOWER.parseItem(), msg, toOthers, firework);
    }
}
