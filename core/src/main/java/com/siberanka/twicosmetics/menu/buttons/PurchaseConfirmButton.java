package com.siberanka.twicosmetics.menu.buttons;

import com.siberanka.twicosmetics.config.MessageManager;
import com.siberanka.twicosmetics.economy.EconomyHandler;
import com.siberanka.twicosmetics.menu.Button;
import com.siberanka.twicosmetics.menu.ClickData;
import com.siberanka.twicosmetics.menu.PurchaseData;
import com.siberanka.twicosmetics.player.UltraPlayer;
import com.siberanka.twicosmetics.util.ItemFactory;
import com.cryptomorin.xseries.XMaterial;
import org.bukkit.inventory.ItemStack;
import org.bukkit.entity.Player;

public class PurchaseConfirmButton implements Button {
    private final PurchaseData purchaseData;
    private final EconomyHandler eh;

    public PurchaseConfirmButton(PurchaseData purchaseData, EconomyHandler eh) {
        this.purchaseData = purchaseData;
        this.eh = eh;
    }

    @Override
    public ItemStack getDisplayItem(UltraPlayer ultraPlayer) {
        return ItemFactory.create(XMaterial.EMERALD_BLOCK, MessageManager.getMessage("Purchase"));
    }

    @Override
    public void onClick(ClickData clickData) {
        UltraPlayer player = clickData.getClicker();
        if (!purchaseData.tryBeginPurchase()) return;
        Player payer = player.getBukkitPlayer();
        if (payer == null) return;
        payer.closeInventory();
        int charge = purchaseData.isFinalPrice() ? purchaseData.getBasePrice()
                : eh.calculateDiscountPrice(payer, purchaseData.getBasePrice());
        eh.withdrawExact(payer, charge, () -> {
            Player onlinePlayer = player.getBukkitPlayer();
            if (onlinePlayer == null || !onlinePlayer.isOnline()) {
                eh.refund(payer, charge);
                return;
            }
            clickData.getMenu().getTwiCosmetics().getScheduler().runAtEntity(onlinePlayer, task -> {
                if (!player.isOnline()) {
                    eh.refund(payer, charge);
                    return;
                }
                player.sendMessage(MessageManager.getMessage("Successful-Purchase"));
                purchaseData.runOnPurchase();
            });
        }, () -> {
            Player onlinePlayer = player.getBukkitPlayer();
            if (onlinePlayer == null) return;
            clickData.getMenu().getTwiCosmetics().getScheduler().runAtEntity(onlinePlayer, task -> {
                if (player.isOnline()) player.sendMessage(MessageManager.getMessage("Not-Enough-Money"));
            });
        });
    }
}
