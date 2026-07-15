package com.siberanka.twicosmetics.permissions;

import com.siberanka.twicosmetics.TwiCosmetics;
import com.siberanka.twicosmetics.config.CustomConfiguration;
import com.siberanka.twicosmetics.config.SettingsManager;
import com.siberanka.twicosmetics.cosmetics.Category;
import com.siberanka.twicosmetics.cosmetics.type.CosmeticType;
import com.siberanka.twicosmetics.player.UltraPlayer;
import com.siberanka.twicosmetics.util.SmartLogger.LogLevel;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

public class PermissionManager {
    private CosmeticPermissionGetter cosmeticGetter;
    private RawPermissionGetter rawGetter;
    private CosmeticPermissionSetter cosmeticSetter;
    private RawPermissionSetter rawSetter;

    public PermissionManager(TwiCosmetics ultraCosmetics) {
        BukkitPermissionGetter bukkit = new BukkitPermissionGetter();
        PermissionCommand cmd = new PermissionCommand();

        CustomConfiguration config = SettingsManager.getConfig();
        String pma = config.getString("TreasureChests.Permission-Add-Command", "");
        if (pma.startsWith("!lp-api")) {
            if (Bukkit.getPluginManager().isPluginEnabled("LuckPerms")) {
                LuckPermsHook lp = new LuckPermsHook(ultraCosmetics);
                rawSetter = lp;
                if (cosmeticSetter == null) {
                    cosmeticSetter = lp;
                }
            } else {
                ultraCosmetics.getSmartLogger().write(LogLevel.WARNING, "Permission-Add-Command was set to '!lp-api' but LuckPerms is not present.");
                config.set("TreasureChests.Permission-Add-Command", "");
                pma = "";
            }
        }
        if (pma.isEmpty()) {
            ProfilePermissions profile = new ProfilePermissions(ultraCosmetics);
            // Empty command intentionally selects durable profile-backed unlocks with Bukkit permission fallback.
            cosmeticGetter = new ProfileBukkitHybridGetter(profile, bukkit);
            cosmeticSetter = profile;
        }

        if (cosmeticGetter == null) {
            cosmeticGetter = bukkit;
        }
        if (rawGetter == null) {
            rawGetter = bukkit;
        }
        if (cosmeticSetter == null) {
            cosmeticSetter = cmd;
        }
        if (rawSetter == null) {
            rawSetter = cmd;
        }
    }

    public GrantSource getGrantSource(Player player, CosmeticType<?> type) {
        return cosmeticGetter.getGrantSource(player, type);
    }

    public boolean hasPermission(Player player, CosmeticType<?> type) {
        return cosmeticGetter.hasPermission(player, type);
    }

    public boolean hasPermission(UltraPlayer player, CosmeticType<?> type) {
        return hasPermission(player.getBukkitPlayer(), type);
    }

    public boolean hasRawPermission(Player player, String permission) {
        return rawGetter.hasRawPermission(player, permission);
    }

    public Set<CosmeticType<?>> getEnabledUnlocked(Player player) {
        return cosmeticGetter.getEnabledUnlocked(player);
    }

    public Set<CosmeticType<?>> getEnabledUnlocked(Player player, Category cat) {
        return cosmeticGetter.getEnabledUnlocked(player, cat);
    }

    public void setPermissions(Player player, Set<CosmeticType<?>> types) {
        cosmeticSetter.setPermissions(player, types);
    }

    public void unsetPermissions(Player player, Set<CosmeticType<?>> types) {
        cosmeticSetter.unsetPermissions(player, types);
    }

    public void setPermission(Player player, CosmeticType<?> type) {
        Set<CosmeticType<?>> types = new HashSet<>();
        types.add(type);
        setPermissions(player, types);
    }

    public void unsetPermission(Player player, CosmeticType<?> type) {
        Set<CosmeticType<?>> types = new HashSet<>();
        types.add(type);
        unsetPermissions(player, types);
    }

    public void setPermission(UltraPlayer player, CosmeticType<?> type) {
        setPermission(player.getBukkitPlayer(), type);
    }

    public void setRawPermission(Player player, String permission) {
        rawSetter.setRawPermission(player, permission);
    }

    public boolean isUsingProfile() {
        return cosmeticGetter instanceof ProfilePermissions;
    }

    public boolean isUnsetSupported() {
        return cosmeticSetter.isUnsetSupported();
    }
}
