package com.siberanka.twicosmetics.util;

import com.siberanka.twicosmetics.TwiCosmeticsData;
import com.siberanka.twicosmetics.config.SettingsManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;

/**
 * Package: com.siberanka.twicosmetics.util
 * Created by: sachalewin
 * Date: 5/08/16
 * Project: TwiCosmetics
 */
public class TextUtil {

    public static Component filterPlaceholderColors(Component placeholder) {
        if (TwiCosmeticsData.get().arePlaceholdersColored()) return placeholder;
        return stripColor(placeholder);
    }

    public static Component stripColor(Component component) {
        return Component.text(PlainTextComponentSerializer.plainText().serialize(component));
    }

    public static String formatNumber(long number) {
        String separator = SettingsManager.getConfig().getString("Thousands-Separator", "");
        if (separator.isEmpty()) return String.valueOf(number);
        // Replacing commas allows separators to be longer than one character, if that's desired
        return String.format("%,d", number).replace(",", separator);
    }
}
