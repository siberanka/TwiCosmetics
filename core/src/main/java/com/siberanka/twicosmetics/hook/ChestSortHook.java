package com.siberanka.twicosmetics.hook;

import com.siberanka.twicosmetics.TwiCosmetics;
import com.siberanka.twicosmetics.cosmetics.gadgets.Gadget;
import com.siberanka.twicosmetics.menu.CosmeticsInventoryHolder;
import com.siberanka.twicosmetics.menu.MenuItemHandler;
import com.siberanka.twicosmetics.player.UltraPlayerManager;
import de.jeff_media.chestsort.api.ChestSortAPI;
import de.jeff_media.chestsort.api.ChestSortEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ChestSortHook implements Listener {
    private final ItemStack menuItem = MenuItemHandler.getMenuItem();
    private final UltraPlayerManager pm;

    public ChestSortHook(TwiCosmetics ultraCosmetics) {
        pm = ultraCosmetics.getPlayerManager();
    }

    public void setUnsortable(Inventory inventory) {
        ChestSortAPI.setUnsortable(inventory);
    }

    @EventHandler
    public void onChestSort(ChestSortEvent event) {
        if (!(event.getPlayer() instanceof Player)) return;
        if (event.getInventory().getHolder() instanceof CosmeticsInventoryHolder) {
            event.setCancelled(true);
            return;
        }
        if (menuItem != null) event.setUnmovable(menuItem);
        Gadget gadget = pm.getUltraPlayer((Player) event.getPlayer()).getCurrentGadget();
        if (gadget != null) {
            gadget.updateItemStack();
            event.setUnmovable(gadget.getItemStack());
        }
    }
}
