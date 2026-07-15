package com.siberanka.twicosmetics.cosmetics.pets;

import com.siberanka.twicosmetics.TwiCosmetics;
import com.siberanka.twicosmetics.cosmetics.type.PetType;
import com.siberanka.twicosmetics.player.UltraPlayer;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Villager.Profession;
import org.bukkit.entity.Villager.Type;

import java.util.Locale;

/**
 * Represents an instance of a villager pet summoned by a player.
 *
 * @author RadBuilder
 * @since 07-02-2017
 */
public class PetVillager extends Pet {
    public PetVillager(UltraPlayer owner, PetType type, TwiCosmetics ultraCosmetics) {
        super(owner, type, ultraCosmetics);
    }

    @SuppressWarnings("deprecation")
    @Override
    protected boolean customize(String customization) {
        Type type;
        Profession profession = Profession.NONE;
        String[] parts = customization.split(":", 2);
        try {
            type = Type.valueOf(parts[0].toUpperCase(Locale.ROOT));
            if (parts.length > 1) {
                profession = Profession.valueOf(parts[1].toUpperCase(Locale.ROOT));
            }
        } catch (IllegalArgumentException e) {
            return false;
        }
        Villager villager = (Villager) entity;
        // Required for villager to hold its profession
        villager.setVillagerExperience(1);
        villager.setVillagerType(type);
        villager.setProfession(profession);
        return true;
    }
}
