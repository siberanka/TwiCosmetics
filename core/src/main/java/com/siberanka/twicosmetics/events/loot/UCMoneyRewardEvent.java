package com.siberanka.twicosmetics.events.loot;

import com.siberanka.twicosmetics.player.UltraPlayer;
import com.siberanka.twicosmetics.treasurechests.TreasureChest;
import com.siberanka.twicosmetics.treasurechests.loot.MoneyLoot;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class UCMoneyRewardEvent extends UCTreasureRewardEvent {
    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlerList() {
        return handlers;
    }

    private int money;

    public UCMoneyRewardEvent(UltraPlayer player, TreasureChest chest, MoneyLoot loot, int money) {
        super(player, chest, loot);
        this.money = money;
    }

    @Override
    public MoneyLoot getLoot() {
        return (MoneyLoot) loot;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
}
