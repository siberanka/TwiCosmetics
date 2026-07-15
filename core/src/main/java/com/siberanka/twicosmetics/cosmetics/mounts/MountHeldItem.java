package com.siberanka.twicosmetics.cosmetics.mounts;

import com.siberanka.twicosmetics.TwiCosmetics;
import com.siberanka.twicosmetics.TwiCosmeticsData;
import com.siberanka.twicosmetics.config.MessageManager;
import com.siberanka.twicosmetics.config.SettingsManager;
import com.siberanka.twicosmetics.cosmetics.Category;
import com.siberanka.twicosmetics.cosmetics.type.MountType;
import com.siberanka.twicosmetics.player.UltraPlayer;
import com.siberanka.twicosmetics.util.ItemFactory;
import com.siberanka.twicosmetics.util.UnmovableItemProvider;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.Arrays;

public abstract class MountHeldItem extends Mount implements UnmovableItemProvider {
    private static final NamespacedKey TAG = new NamespacedKey(TwiCosmeticsData.get().getPlugin(), "HELDITEM");
    private final ItemStack heldItem;
    private final int slot = SettingsManager.getConfig().getInt("Gadget-Slot");
    private final boolean removeWithDrop = SettingsManager.getConfig().getBoolean("Remove-Gadget-With-Drop");

    public MountHeldItem(UltraPlayer ultraPlayer, MountType type, TwiCosmetics ultraCosmetics) {
        super(ultraPlayer, type, ultraCosmetics);

        heldItem = new ItemStack(getHeldItemMaterial());
        ItemMeta meta = heldItem.getItemMeta();
        String loreString = MessageManager.getLegacyMessage(getOptionPath("Held-Item-Lore"));
        meta.setLore(Arrays.asList(loreString.split("\n")));
        meta.getPersistentDataContainer().set(TAG, PersistentDataType.BYTE, (byte) 1);
        heldItem.setItemMeta(meta);
        ItemFactory.applyCosmeticMarker(heldItem);
    }

    @Override
    public void onEquip() {
        super.onEquip();
        getTwiCosmetics().getUnmovableItemListener().addProvider(this);
    }

    @Override
    public void clear() {
        super.clear();
        getPlayer().getInventory().setItem(slot, null);
        getTwiCosmetics().getUnmovableItemListener().removeProvider(this);
    }

    @Override
    public boolean tryEquip() {
        getOwner().removeCosmetic(Category.GADGETS);
        getOwner().removeCosmetic(Category.MOUNTS);
        ItemStack current = getPlayer().getInventory().getItem(slot);
        if (current != null && current.getType() != Material.AIR) {
            MessageManager.send(getPlayer(), "Must-Remove.Mounts",
                    Placeholder.unparsed("slot", String.valueOf(slot + 1))
            );
            return false;
        }
        getPlayer().getInventory().setItem(slot, heldItem);
        getPlayer().getInventory().setHeldItemSlot(slot);
        return true;
    }

    @Override
    public void onUpdate() {
    }

    @Override
    public void handleDrop(PlayerDropItemEvent event) {
        if (removeWithDrop) {
            clear();
            event.getItemDrop().remove();
        } else {
            event.setCancelled(true);
        }
    }

    @Override
    public boolean itemMatches(ItemStack stack) {
        return stack != null && stack.getItemMeta() != null && stack.getItemMeta().getPersistentDataContainer().has(TAG, PersistentDataType.BYTE);
    }

    @Override
    public int getSlot() {
        return slot;
    }

    @Override
    public void moveItem(int slot, Player player) {
        clear();
    }

    @Override
    public void handleInteract(PlayerInteractEvent event) {
        event.setCancelled(true);
    }

    public ItemStack getHeldItem() {
        return heldItem.clone();
    }

    public abstract Material getHeldItemMaterial();
}
