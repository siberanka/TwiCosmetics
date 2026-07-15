package com.siberanka.twicosmetics.permissions;

import com.siberanka.twicosmetics.TwiCosmetics;
import com.siberanka.twicosmetics.cosmetics.Category;
import com.siberanka.twicosmetics.cosmetics.type.CosmeticType;
import com.siberanka.twicosmetics.player.UltraPlayerManager;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

public class ProfilePermissions implements CosmeticPermissionGetter, CosmeticPermissionSetter {
    private final UltraPlayerManager pm;

    public ProfilePermissions(TwiCosmetics ultraCosmetics) {
        this.pm = ultraCosmetics.getPlayerManager();
    }

    @Override
    public void setPermissions(Player player, Set<CosmeticType<?>> types) {
        pm.getUltraPlayer(player).getProfile().setUnlocked(types);
    }

    @Override
    public void unsetPermissions(Player player, Set<CosmeticType<?>> types) {
        pm.getUltraPlayer(player).getProfile().setLocked(types);
    }

    @Override
    public GrantSource getGrantSource(Player player, CosmeticType<?> type) {
        return pm.getUltraPlayer(player).getProfile().hasUnlocked(type) ? GrantSource.PROFILE : null;
    }

    @Override
    public Set<CosmeticType<?>> getEnabledUnlocked(Player player) {
        return pm.getUltraPlayer(player).getProfile().getAllUnlocked();
    }

    @Override
    public Set<CosmeticType<?>> getEnabledUnlocked(Player player, Category cat) {
        Set<CosmeticType<?>> types = new HashSet<>();
        pm.getUltraPlayer(player).getProfile().getAllUnlocked().stream().filter(t -> t.getCategory() == cat && t.isEnabled()).forEach(types::add);
        return types;
    }

    @Override
    public boolean isUnsetSupported() {
        return true;
    }
}
