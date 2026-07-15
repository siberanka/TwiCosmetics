package com.siberanka.twicosmetics.menu;

import com.siberanka.twicosmetics.TwiCosmetics;
import com.siberanka.twicosmetics.command.CommandManager;
import com.siberanka.twicosmetics.config.MessageManager;
import com.siberanka.twicosmetics.config.SettingsManager;
import com.siberanka.twicosmetics.hook.PlaceholderHook;
import com.siberanka.twicosmetics.player.UltraPlayer;
import com.siberanka.twicosmetics.util.ItemFactory;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a Menu. A menu can have multiple pages in case of cosmetics.
 * Each item in the menu will, when clicked by a player, execute a ClickRunnable.
 *
 * @author iSach
 * @since 07-05-2016
 */
public abstract class Menu implements Listener {
    public static final Permission ALL_MENUS_PERMISSION = registerAllPermission();

    private static Permission registerAllPermission() {
        Permission perm = Bukkit.getPluginManager().getPermission("ultracosmetics.menu.all");
        if (perm == null) {
            perm = new Permission("ultracosmetics.menu.all", PermissionDefault.TRUE);
            Bukkit.getPluginManager().addPermission(perm);
        }
        return perm;
    }

    private static final Map<String, Permission> REGISTERED_PERMISSIONS = new HashMap<>();

    public static List<Permission> getMenuPermissions() {
        return new ArrayList<>(REGISTERED_PERMISSIONS.values());
    }

    /**
     * TwiCosmetics Instance.
     */
    protected final TwiCosmetics ultraCosmetics;

    private final boolean fillEmpty = SettingsManager.getConfig().getBoolean("Fill-Blank-Slots-With-Item.Enabled");

    private final ItemStack fillerItem = getFillerItem();
    protected final Permission permission;

    public Menu(String name, TwiCosmetics ultraCosmetics) {
        this.ultraCosmetics = ultraCosmetics;

        ultraCosmetics.getServer().getPluginManager().registerEvents(this, ultraCosmetics);
        this.permission = registerPermission(name);
    }

    private Permission registerPermission(String strPerm) {
        return REGISTERED_PERMISSIONS.computeIfAbsent(strPerm, s -> {
            String name = "ultracosmetics.menu." + s;
            Permission perm = Bukkit.getPluginManager().getPermission(name);
            if (perm == null) {
                perm = new Permission("ultracosmetics.menu." + s);
                Bukkit.getPluginManager().addPermission(perm);
                perm.addParent(ALL_MENUS_PERMISSION, true);
            }
            return perm;
        });
    }

    public void open(UltraPlayer player) {
        if (!player.getBukkitPlayer().hasPermission(permission)) {
            CommandManager.sendNoPermissionMessage(player.getBukkitPlayer());
            return;
        }
        player.getBukkitPlayer().openInventory(getInventory(player));
    }

    public void refresh(UltraPlayer player) {
        open(player);
    }

    protected Inventory createInventory(Component name, UltraPlayer owner) {
        CosmeticsInventoryHolder holder = new CosmeticsInventoryHolder(owner.getUUID());
        Inventory inventory = Bukkit.createInventory(holder, getSize(), MessageManager.toLegacy(name));
        ((CosmeticsInventoryHolder) inventory.getHolder()).setInventory(inventory);
        return inventory;
    }

    public Inventory getInventory(UltraPlayer player) {
        Inventory inventory = createInventory(getName(), player);
        putItems(inventory, player);
        fillInventory(inventory);
        return inventory;
    }

    public Permission getPermission() {
        return permission;
    }

    protected void putItem(Inventory inventory, int slot, Button button, UltraPlayer ultraPlayer) {
        ItemStack itemStack = button.getDisplayItem(ultraPlayer);
        if (itemStack.hasItemMeta()) {
            ItemFactory.setFlags(itemStack);
            ItemMeta itemMeta = itemStack.getItemMeta();
            if (itemMeta.hasLore() && ultraCosmetics.getPlaceholderHook() != null) {
                Player player = ultraPlayer.getBukkitPlayer();
                List<String> loreList = itemMeta.getLore();
                for (int i = 0; i < loreList.size(); i++) {
                    if (loreList.get(i).contains("%")) {
                        loreList.set(i, PlaceholderHook.parsePlaceholders(player, loreList.get(i)));
                    }
                }
                itemMeta.setLore(loreList);
            }
            itemStack.setItemMeta(itemMeta);
        }

        inventory.setItem(slot, itemStack);
        CosmeticsInventoryHolder holder = (CosmeticsInventoryHolder) inventory.getHolder();
        holder.bind(slot, itemStack, button);
    }

    protected void fillInventory(Inventory inventory) {
        if (!fillEmpty) return;
        for (int i = 0; i < inventory.getSize(); i++) {
            if (inventory.getItem(i) == null || inventory.getItem(i).getType() == Material.AIR) {
                inventory.setItem(i, fillerItem);
            }
        }
    }

    private ItemStack getFillerItem() {
        ItemStack itemStack = ItemFactory.getItemStackFromConfig("Fill-Blank-Slots-With-Item.Item");
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.GRAY + "");
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) {
            return;
        }

        Inventory topInventory = event.getView().getTopInventory();
        if (!(topInventory.getHolder() instanceof CosmeticsInventoryHolder holder)) return;

        // Fail closed for every interaction while a TwiCosmetics menu is open.
        event.setCancelled(true);

        Player player = (Player) event.getWhoClicked();
        if (!holder.isActive() || !holder.belongsTo(player.getUniqueId())) {
            holder.invalidate();
            player.closeInventory();
            return;
        }

        int rawSlot = event.getRawSlot();
        if (event.getClickedInventory() != topInventory || rawSlot < 0 || rawSlot >= topInventory.getSize()) return;
        if (!holder.tryInteract()) return;

        ItemStack serverItem = topInventory.getItem(rawSlot);
        Button button = holder.resolve(rawSlot, serverItem);
        if (button == null) return;

        UltraPlayer ultraPlayer = ultraCosmetics.getPlayerManager().getUltraPlayer(player);
        button.onClick(new ClickData(this, ultraPlayer, event.getClick(), serverItem.clone(), rawSlot));
    }

    @EventHandler
    public void onDrag(InventoryDragEvent event) {
        Inventory topInventory = event.getView().getTopInventory();
        if (!(topInventory.getHolder() instanceof CosmeticsInventoryHolder holder)) return;
        if (!holder.isActive() || event.getRawSlots().stream().anyMatch(slot -> slot < topInventory.getSize())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        if (event.getView().getTopInventory().getHolder() instanceof CosmeticsInventoryHolder holder) {
            holder.invalidate();
        }
    }

    public TwiCosmetics getTwiCosmetics() {
        return ultraCosmetics;
    }

    protected abstract void putItems(Inventory inventory, UltraPlayer player);

    protected abstract int getSize();

    protected abstract Component getName();
}
