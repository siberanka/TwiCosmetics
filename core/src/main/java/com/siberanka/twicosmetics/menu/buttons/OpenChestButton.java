package com.siberanka.twicosmetics.menu.buttons;

import com.siberanka.twicosmetics.TwiCosmetics;
import com.siberanka.twicosmetics.config.MessageManager;
import com.siberanka.twicosmetics.config.SettingsManager;
import com.siberanka.twicosmetics.menu.ClickData;
import com.siberanka.twicosmetics.player.UltraPlayer;
import com.siberanka.twicosmetics.treasurechests.TreasureChestManager;
import com.siberanka.twicosmetics.treasurechests.TreasureRandomizer;
import com.siberanka.twicosmetics.util.ItemFactory;
import com.cryptomorin.xseries.XMaterial;
import com.cryptomorin.xseries.XSound;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class OpenChestButton extends TreasureButton {
    private final Component itemName = MessageManager.getMessage("Treasure-Chests");
    private final String chestMode = SettingsManager.getConfig().getString("TreasureChests.Mode", "structure");
    private final int chestCount = SettingsManager.getConfig().getInt("TreasureChests.Count", 4);
    private final String[] noKeysLore = new String[] {"", MessageManager.getLegacyMessage("Dont-Have-Key"), buyKeyMessage};
    private final String[] hasKeysLore;
    private final TreasureChestManager treasureChestManager;
    private final XSound.SoundPlayer noKeysSound;

    public OpenChestButton(TwiCosmetics ultraCosmetics) {
        super(ultraCosmetics);
        this.treasureChestManager = ultraCosmetics.getTreasureChestManager();
        if (chestMode.equalsIgnoreCase("both")) {
            hasKeysLore = new String[] {"", MessageManager.getLegacyMessage("Left-Click-Open-Chest"),
                    MessageManager.getLegacyMessage("Right-Click-Simple"), ""};
        } else {
            hasKeysLore = new String[] {"", MessageManager.getLegacyMessage("Click-Open-Chest"), ""};
        }
        noKeysSound = XSound.BLOCK_ANVIL_LAND.record().withVolume(0.2f).withPitch(1.2f).soundPlayer();
    }

    @Override
    public ItemStack getDisplayItem(UltraPlayer ultraPlayer) {
        String[] lore = ultraPlayer.getKeys() < 1 ? noKeysLore : hasKeysLore;
        return ItemFactory.create(XMaterial.CHEST, itemName, lore);
    }

    @Override
    public void onClick(ClickData clickData) {
        UltraPlayer player = clickData.getClicker();
        Player p = player.getBukkitPlayer();
        if (!canBuyKeys && player.getKeys() < 1) {
            noKeysSound.forPlayers(p).play();
            return;
        }
        String selectedMode = chestMode;
        if (chestMode.equalsIgnoreCase("both")) {
            if (clickData.getClick().isRightClick()) {
                selectedMode = "simple";
            } else {
                selectedMode = "structure";
            }
        }
        if (player.getKeys() > 0 && selectedMode.equalsIgnoreCase("simple")) {
            if (!player.tryRemoveKey()) return;
            TreasureRandomizer tr = new TreasureRandomizer(p, p.getLocation().subtract(1, 0, 1), true);
            for (int i = 0; i < chestCount; i++) {
                tr.giveRandomThing(null, false);
            }
            // Refresh with new key count
            clickData.getMenu().open(player);
        } else {
            // Opens a standard chest, or opens the buy-a-key menu if the player doesn't have enough keys
            treasureChestManager.tryOpenChest(p);
        }
    }
}
