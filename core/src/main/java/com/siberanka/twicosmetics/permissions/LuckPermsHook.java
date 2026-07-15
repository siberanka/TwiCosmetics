package com.siberanka.twicosmetics.permissions;

import com.siberanka.twicosmetics.TwiCosmetics;
import com.siberanka.twicosmetics.config.SettingsManager;
import com.siberanka.twicosmetics.cosmetics.type.CosmeticType;
import com.siberanka.twicosmetics.util.SmartLogger.LogLevel;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.context.ImmutableContextSet;
import net.luckperms.api.node.Node;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Set;

public class LuckPermsHook implements CosmeticPermissionSetter, RawPermissionSetter {
    private final TwiCosmetics ultraCosmetics;
    private final LuckPerms api;
    private final ImmutableContextSet context;
    private boolean log = true;

    public LuckPermsHook(TwiCosmetics ultraCosmetics) {
        this.ultraCosmetics = ultraCosmetics;
        api = Bukkit.getServicesManager().getRegistration(LuckPerms.class).getProvider();
        String[] contexts = SettingsManager.getConfig().getString("TreasureChests.Permission-Add-Command").split(" ");
        ImmutableContextSet.Builder contextBuilder = ImmutableContextSet.builder();
        for (int i = 1; i < contexts.length; i++) {
            if (contexts[i].equals("nolog")) {
                log = false;
                continue;
            }
            String[] kv = contexts[i].split("=");
            if (kv.length != 2) {
                ultraCosmetics.getSmartLogger().write(LogLevel.WARNING, "Invalid LuckPerms context: " + contexts[i]);
                continue;
            }
            contextBuilder.add(kv[0], kv[1]);
        }
        context = contextBuilder.build();
    }

    @Override
    public void setRawPermission(Player player, String permission) {
        String playerName = player.getName();
        if (log) {
            ultraCosmetics.getSmartLogger().write("Setting permission '" + permission + "' for user " + playerName);
        }

        api.getUserManager().modifyUser(player.getUniqueId(), user -> {
            Node node = Node.builder(permission).value(true).context(context).build();
            user.data().add(node);
        });
    }

    @Override
    public void setPermissions(Player player, Set<CosmeticType<?>> types) {
        types.forEach(t -> setRawPermission(player, t.getPermission().getName()));
    }

    @Override
    public void unsetPermissions(Player player, Set<CosmeticType<?>> types) {
        throw new UnsupportedOperationException("Cannot unset permission using LuckPerms API");
    }

    @Override
    public boolean isUnsetSupported() {
        return false;
    }
}
