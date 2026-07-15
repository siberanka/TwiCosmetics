package com.siberanka.twicosmetics.hook;

import com.siberanka.twicosmetics.TwiCosmetics;
import com.siberanka.twicosmetics.config.MessageManager;
import com.siberanka.twicosmetics.cosmetics.Category;
import com.siberanka.twicosmetics.cosmetics.Cosmetic;
import com.siberanka.twicosmetics.cosmetics.type.CosmeticType;
import com.siberanka.twicosmetics.cosmetics.type.GadgetType;
import com.siberanka.twicosmetics.player.UltraPlayer;
import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;

/**
 * PlaceholderAPI hook.
 *
 * @author RadBuilder
 * @since 2.5
 */
public class PlaceholderHook extends PlaceholderExpansion {

    private final TwiCosmetics ultraCosmetics;

    public PlaceholderHook(TwiCosmetics ultraCosmetics) {
        this.ultraCosmetics = ultraCosmetics;
    }

    @Override
    public String onPlaceholderRequest(Player player, String identifier) {
        UltraPlayer ultraPlayer = ultraCosmetics.getPlayerManager().getUltraPlayer(player);
        if (identifier.startsWith("ammo_")) {
            String gadget = identifier.substring(5);
            GadgetType type = CosmeticType.valueOf(Category.GADGETS, gadget);
            if (type == null) return null;
            return String.valueOf(ultraPlayer.getAmmo(type));
        }
        if (identifier.startsWith("current_")) {
            identifier = identifier.substring(8);
            if (identifier.startsWith("suit_")) {
                identifier = "suits_" + identifier.substring(5);
            }
            identifier = identifier.replace("particleeffect", "effect");
            Category category = Category.fromString(identifier);
            if (category == null) return null;
            Cosmetic<?> cosmetic = ultraPlayer.getCosmetic(category);
            if (cosmetic == null) return "None";
            return MessageManager.toLegacy(cosmetic.getTypeName());
        }
        switch (identifier) {
            // Keys, and user-specific settings
            case "keys":
                return "" + ultraPlayer.getKeys();
            case "gadgets_enabled":
                return "" + ultraPlayer.hasGadgetsEnabled();
            case "morph_selfview":
                return "" + ultraPlayer.canSeeSelfMorph();
            case "treasurechest_active":
                return "" + (ultraPlayer.getCurrentTreasureChest() != null);
        }
        return null;
    }

    private String currentName(UltraPlayer ultraPlayer, Category category) {
        Cosmetic<?> cosmetic = ultraPlayer.getCosmetic(category);
        if (cosmetic == null) return "None";
        return MessageManager.toLegacy(cosmetic.getTypeName());
    }

    @Override
    public String getIdentifier() {
        return "ultracosmetics";
    }

    @Override
    public String getAuthor() {
        return ultraCosmetics.getDescription().getAuthors().toString();
    }

    @Override
    public String getVersion() {
        return ultraCosmetics.getDescription().getVersion();
    }

    @Override
    public boolean persist() {
        return true;
    }

    public static String parsePlaceholders(Player player, String str) {
        return PlaceholderAPI.setPlaceholders(player, str);
    }
}
