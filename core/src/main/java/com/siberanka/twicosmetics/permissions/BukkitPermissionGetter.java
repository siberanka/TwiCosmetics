package com.siberanka.twicosmetics.permissions;

import com.siberanka.twicosmetics.cosmetics.type.CosmeticType;
import org.bukkit.entity.Player;

public class BukkitPermissionGetter implements CosmeticPermissionGetter, RawPermissionGetter {

    @Override
    public boolean hasRawPermission(Player player, String permission) {
        return player.hasPermission(permission);
    }

    @Override
    public GrantSource getGrantSource(Player player, CosmeticType<?> type) {
        return hasRawPermission(player, type.getPermission().getName()) ? GrantSource.PERMISSION : null;
    }

}
