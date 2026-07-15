package com.siberanka.twicosmetics.cosmetics;

import com.siberanka.twicosmetics.TwiCosmetics;
import com.siberanka.twicosmetics.command.CommandManager;
import com.siberanka.twicosmetics.config.MessageManager;
import com.siberanka.twicosmetics.config.SettingsManager;
import com.siberanka.twicosmetics.cosmetics.type.CosmeticType;
import com.siberanka.twicosmetics.events.UCCosmeticEquipEvent;
import com.siberanka.twicosmetics.events.UCCosmeticUnequipEvent;
import com.siberanka.twicosmetics.player.UltraPlayer;
import com.siberanka.twicosmetics.util.TextUtil;
import com.siberanka.twicosmetics.worldguard.CosmeticRegionState;
import com.tcoded.folialib.wrapper.task.WrappedTask;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

import java.util.Random;
import java.util.UUID;

/**
 * A cosmetic instance summoned by a player.
 *
 * @author iSach
 * @since 07-21-2016
 */
public abstract class Cosmetic<T extends CosmeticType<?>> implements Listener {
    protected static final Random RANDOM = new Random();
    private final UltraPlayer owner;
    private final Category category;
    private final TwiCosmetics ultraCosmetics;
    protected boolean equipped;
    protected final T cosmeticType;
    private final UUID ownerUniqueId;
    private final Component typeName;

    protected WrappedTask task;

    public Cosmetic(UltraPlayer owner, T type, TwiCosmetics ultraCosmetics) {
        if (owner == null || owner.getBukkitPlayer() == null) {
            throw new IllegalArgumentException("Invalid UltraPlayer.");
        }
        this.owner = owner;
        this.ownerUniqueId = owner.getUUID();
        this.category = type.getCategory();
        this.ultraCosmetics = ultraCosmetics;
        this.cosmeticType = type;
        this.typeName = type.getName();
    }

    public final void equip() {
        Player player = getPlayer();
        if (!cosmeticType.isEnabled()) {
            MessageManager.send(player, "Cosmetic-Disabled");
            return;
        }

        if (!owner.canEquip(cosmeticType)) {
            CommandManager.sendNoPermissionMessage(player);
            return;
        }

        if (PlayerAffectingCosmetic.isVanished(player) && SettingsManager.getConfig().getBoolean("Prevent-Cosmetics-In-Vanish")) {
            owner.clear();
            MessageManager.send(player, "Not-Allowed-In-Vanish");
            return;
        }
        CosmeticRegionState state = ultraCosmetics.getWorldGuardManager().allowedCosmeticsState(player, category);
        if (state == CosmeticRegionState.BLOCKED_ALL) {
            MessageManager.send(player, "Region-Disabled");
            return;
        } else if (state == CosmeticRegionState.BLOCKED_CATEGORY) {
            TagResolver.Single placeholder = Placeholder.component("category", TextUtil.stripColor(MessageManager.getMessage("Menu." + category.getConfigPath() + ".Title")));
            MessageManager.send(player, "Region-Disabled-Category", placeholder);
            return;
        }

        if (!tryEquip()) {
            return;
        }

        UCCosmeticEquipEvent event = new UCCosmeticEquipEvent(owner, this);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled()) {
            return;
        }

        ultraCosmetics.getServer().getPluginManager().registerEvents(this, ultraCosmetics);

        unequipLikeCosmetics();

        this.equipped = true;

        if (!owner.isPreserveEquipped()) {
            TagResolver.Single typeNamePlaceholder = Placeholder.component(getCategory().getChatPlaceholder(), TextUtil.filterPlaceholderColors(typeName));
            Component activateMessage = MessageManager.getMessage(category.getConfigPath() + ".Equip", typeNamePlaceholder);
            owner.sendMessage(appendActivateMessage(activateMessage));
        }

        if (this instanceof Updatable) {
            scheduleTask();
        }

        onEquip();

        getOwner().setCosmeticEquipped(this);
    }

    public /* final */ void clear() {
        UCCosmeticUnequipEvent event = new UCCosmeticUnequipEvent(owner, this);
        Bukkit.getPluginManager().callEvent(event);

        if (!owner.isPreserveEquipped()) {
            TagResolver.Single typeNamePlaceholder = Placeholder.component(getCategory().getChatPlaceholder(), TextUtil.filterPlaceholderColors(typeName));
            Component deactivateMessage = MessageManager.getMessage(category.getConfigPath() + ".Unequip", typeNamePlaceholder);
            owner.sendMessage(deactivateMessage);
        }

        HandlerList.unregisterAll(this);

        try {
            if (task != null) task.cancel();
        } catch (IllegalStateException ignored) {
        } // not scheduled yet

        // Call untask finally. (in main thread)
        onClear();
        unsetCosmetic();
    }

    // Allows NMS modules to cancel the task without transitive dependencies.
    protected void cancelTask() {
        if (task != null) {
            task.cancel();
        }
    }

    protected void scheduleTask() {
        task = ultraCosmetics.getScheduler().runAtEntityTimer(getPlayer(), this::run, 1, 1);
    }

    protected void unsetCosmetic() {
        owner.unsetCosmetic(category);
    }

    protected void unequipLikeCosmetics() {
        getOwner().removeCosmetic(category);
    }

    protected boolean tryEquip() {
        return true;
    }

    public void run() {
        if (getPlayer() == null || getOwner().getCosmetic(category) != this) {
            return;
        }
        ((Updatable) this).onUpdate();
    }

    protected abstract void onEquip();

    protected void onClear() {
    }

    public final UltraPlayer getOwner() {
        return owner;
    }

    public final TwiCosmetics getTwiCosmetics() {
        return ultraCosmetics;
    }

    public final Category getCategory() {
        return category;
    }

    public final Player getPlayer() {
        return owner.getBukkitPlayer();
    }

    public boolean isEquipped() {
        return equipped;
    }

    public final UUID getOwnerUniqueId() {
        return ownerUniqueId;
    }

    public T getType() {
        return cosmeticType;
    }

    public Component getTypeName() {
        return typeName;
    }

    protected Component appendActivateMessage(Component base) {
        return base;
    }

    protected String getOptionPath(String key) {
        return cosmeticType.getConfigPath() + "." + key;
    }
}
