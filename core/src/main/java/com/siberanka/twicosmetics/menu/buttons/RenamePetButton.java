package com.siberanka.twicosmetics.menu.buttons;

import com.siberanka.twicosmetics.TwiCosmetics;
import com.siberanka.twicosmetics.TwiCosmeticsData;
import com.siberanka.twicosmetics.config.MessageManager;
import com.siberanka.twicosmetics.config.SettingsManager;
import com.siberanka.twicosmetics.menu.Button;
import com.siberanka.twicosmetics.menu.ClickData;
import com.siberanka.twicosmetics.menu.Menu;
import com.siberanka.twicosmetics.menu.MenuPurchase;
import com.siberanka.twicosmetics.menu.MenuPurchaseFactory;
import com.siberanka.twicosmetics.menu.PurchaseData;
import com.siberanka.twicosmetics.mysql.MySqlConnectionManager;
import com.siberanka.twicosmetics.player.UltraPlayer;
import com.siberanka.twicosmetics.cosmetics.type.PetType;
import com.siberanka.twicosmetics.util.ItemFactory;
import com.siberanka.twicosmetics.util.TextUtil;
import com.cryptomorin.xseries.XMaterial;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Collections;

public class RenamePetButton implements Button {
    private final Component activePetNeeded = MessageManager.getMessage("Active-Pet-Needed");
    private final ItemStack stack = ItemFactory.getItemStackFromConfig("Categories.Rename-Pet-Item");
    private final TwiCosmetics ultraCosmetics;

    public RenamePetButton(TwiCosmetics ultraCosmetics) {
        this.ultraCosmetics = ultraCosmetics;
    }

    @Override
    public ItemStack getDisplayItem(UltraPlayer ultraPlayer) {
        if (ultraPlayer.getCurrentPet() == null) {
            return ItemFactory.rename(this.stack.clone(), activePetNeeded);
        }
        Component name = MessageManager.getMessage("Menu.Rename-Pet.Button.Name",
                Placeholder.component("petname", ultraPlayer.getCurrentPet().getTypeName())
        );
        return ItemFactory.rename(this.stack.clone(), name);
    }

    @Override
    public void onClick(ClickData clickData) {
        UltraPlayer player = clickData.getClicker();
        if (player.getCurrentPet() == null) {
            player.sendMessage(activePetNeeded);
            player.getBukkitPlayer().closeInventory();
            return;
        }
        renamePet(ultraCosmetics, player, clickData.getMenu());
    }

    public static void renamePet(TwiCosmetics ultraCosmetics, final UltraPlayer ultraPlayer, Menu returnMenu) {
        String oldName = ultraPlayer.getProfile().getPetName(ultraPlayer.getCurrentPet().getType());
        if (oldName == null) {
            oldName = MessageManager.getLegacyMessage("Menu.Rename-Pet.Placeholder");
        }
        try {
            new AnvilGUI.Builder().plugin(ultraCosmetics)
                    .itemLeft(XMaterial.PAPER.parseItem())
                    .text(oldName)
                    .title(MessageManager.getLegacyMessage("Menu.Rename-Pet.Title"))
                    .onClick((slot, state) -> {
                        if (slot != AnvilGUI.Slot.OUTPUT) return Collections.emptyList();
                        String text = state.getText();
                        String stripped = MessageManager.getMiniMessage().stripTags(text);
                        int maxLength = SettingsManager.getConfig().getInt("Max-Pet-Name-Length", -1);
                        if ((maxLength != -1 && stripped.length() > maxLength) || text.length() > MySqlConnectionManager.MAX_NAME_SIZE) {
                            return Collections.singletonList(AnvilGUI.ResponseAction.replaceInputText(MessageManager.getLegacyMessage("Too-Long")));
                        }
                        if (!text.isEmpty() && ultraCosmetics.getEconomyHandler().isUsingEconomy()
                                && SettingsManager.getConfig().getBoolean("Pets-Rename.Requires-Money.Enabled")) {
                            return Collections.singletonList(AnvilGUI.ResponseAction.openInventory(buyRenamePet(ultraPlayer, stripped, returnMenu)));
                        } else {
                            ultraPlayer.setPetName(ultraPlayer.getCurrentPet().getType(), stripped);
                            return Collections.singletonList(AnvilGUI.ResponseAction.close());
                        }
                    }).open(ultraPlayer.getBukkitPlayer());
        } catch (ExceptionInInitializerError | NoClassDefFoundError e) {
            ultraPlayer.sendMessage(MessageManager.getMessage("Use-Rename-Pet-Command"));
        }
    }

    public static Inventory buyRenamePet(UltraPlayer ultraPlayer, final String name, Menu returnMenu) {
        PetType petType = ultraPlayer.getCurrentPet().getType();
        int price = SettingsManager.getConfig().getInt("Pets-Rename.Requires-Money.Price");
        int discountPrice = TwiCosmeticsData.get().getPlugin().getEconomyHandler().calculateDiscountPrice(ultraPlayer.getBukkitPlayer(), price);
        Component renameTitle = MessageManager.getMessage("Menu.Purchase-Rename.Button.Showcase",
                Placeholder.unparsed("price", TextUtil.formatNumber(discountPrice)),
                Placeholder.component("name", MessageManager.getMiniMessage().deserialize(name)));
        ItemStack showcaseItem = ItemFactory.create(XMaterial.NAME_TAG, renameTitle);

        PurchaseData purchaseData = new PurchaseData();
        purchaseData.setBasePrice(price);
        purchaseData.setShowcaseItem(showcaseItem);
        purchaseData.setCanPurchase(() -> ultraPlayer.isOnline() && ultraPlayer.getCurrentPet() != null
                && ultraPlayer.getCurrentPet().getType() == petType);
        purchaseData.setOnPurchase(() -> {
            ultraPlayer.setPetName(petType, name);
            if (returnMenu != null) {
                returnMenu.open(ultraPlayer);
            }
        });
        if (returnMenu != null) {
            purchaseData.setOnCancel(() -> returnMenu.open(ultraPlayer));
        }

        MenuPurchaseFactory mpFactory = TwiCosmeticsData.get().getPlugin().getMenus().getMenuPurchaseFactory();
        MenuPurchase menu = mpFactory.createPurchaseMenu(TwiCosmeticsData.get().getPlugin(), MessageManager.getMessage("Menu.Purchase-Rename.Title"), purchaseData);
        return menu.getInventory(ultraPlayer);
    }
}
