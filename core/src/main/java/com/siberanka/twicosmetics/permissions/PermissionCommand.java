package com.siberanka.twicosmetics.permissions;

import com.siberanka.twicosmetics.config.SettingsManager;
import com.siberanka.twicosmetics.cosmetics.type.CosmeticType;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Set;

public class PermissionCommand implements CosmeticPermissionSetter, RawPermissionSetter {
    private final String commandTemplate;

    public PermissionCommand() {
        commandTemplate = SettingsManager.getConfig().getString("TreasureChests.Permission-Add-Command");
    }

    @Override
    public void setRawPermission(Player player, String permission) {
        String command = commandTemplate.replace("%name%", player.getName()).replace("%permission%", permission);
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
    }

    @Override
    public void setPermissions(Player player, Set<CosmeticType<?>> type) {
        type.forEach(t -> setRawPermission(player, t.getPermission().getName()));
    }

    @Override
    public void unsetPermissions(Player player, Set<CosmeticType<?>> cosmeticType) {
        throw new UnsupportedOperationException("Cannot unset permission with permission command");
    }

    @Override
    public boolean isUnsetSupported() {
        return false;
    }
}
