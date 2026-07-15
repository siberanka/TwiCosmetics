package com.siberanka.twicosmetics.permissions;

import com.siberanka.twicosmetics.cosmetics.type.CosmeticType;

import org.bukkit.entity.Player;

import java.util.Set;

public interface CosmeticPermissionSetter {
    public void setPermissions(Player player, Set<CosmeticType<?>> types);

    public boolean isUnsetSupported();

    public void unsetPermissions(Player player, Set<CosmeticType<?>> types);
}
