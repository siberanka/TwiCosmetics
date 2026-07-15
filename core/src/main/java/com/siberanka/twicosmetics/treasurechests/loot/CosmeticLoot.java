package com.siberanka.twicosmetics.treasurechests.loot;

import com.siberanka.twicosmetics.TwiCosmeticsData;
import com.siberanka.twicosmetics.config.MessageManager;
import com.siberanka.twicosmetics.config.SettingsManager;
import com.siberanka.twicosmetics.cosmetics.Category;
import com.siberanka.twicosmetics.cosmetics.type.CosmeticType;
import com.siberanka.twicosmetics.events.loot.UCCosmeticRewardEvent;
import com.siberanka.twicosmetics.permissions.PermissionManager;
import com.siberanka.twicosmetics.player.UltraPlayer;
import com.siberanka.twicosmetics.treasurechests.TreasureChest;
import com.siberanka.twicosmetics.util.TextUtil;
import com.siberanka.twicosmetics.util.WeightedSet;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class CosmeticLoot implements Loot {
    private final Category category;
    private final WeightedSet<CosmeticType<?>> types = new WeightedSet<>();
    private final PermissionManager pm = TwiCosmeticsData.get().getPlugin().getPermissionManager();

    public CosmeticLoot(Category category, Player player) {
        this.category = category;
        for (CosmeticType<?> type : category.getEnabled()) {
            if (!type.isEnabled() || type.getChestWeight() < 1 || pm.hasPermission(player, type)) continue;
            types.add(type, type.getChestWeight());
        }
    }

    public Category getCategory() {
        return category;
    }

    @Override
    public LootReward giveToPlayer(UltraPlayer player, TreasureChest chest) {
        CosmeticType<?> cosmetic = types.removeRandom();

        UCCosmeticRewardEvent event = new UCCosmeticRewardEvent(player, chest, this, cosmetic);
        Bukkit.getPluginManager().callEvent(event);

        String catName = category.getConfigPath();
        String[] name = MessageManager.getLegacyMessage("Treasure-Chests-Loot." + catName,
                Placeholder.component("cosmetic", cosmetic.getName())
        ).split("\n");
        pm.setPermission(player, cosmetic);
        boolean toOthers = SettingsManager.getConfig().getBoolean("TreasureChests.Loots." + catName + ".Message.enabled");
        Component message = MessageManager.getMessage("Treasure-Chests-Loot-Messages." + catName,
                Placeholder.component("cosmetic", TextUtil.filterPlaceholderColors(cosmetic.getName())),
                Placeholder.unparsed("name", player.getBukkitPlayer().getName())
        );
        return new LootReward(name, cosmetic.getItemStack(), message, toOthers, true, cosmetic);
    }

    @Override
    public boolean isEmpty() {
        return types.size() == 0;
    }
}
