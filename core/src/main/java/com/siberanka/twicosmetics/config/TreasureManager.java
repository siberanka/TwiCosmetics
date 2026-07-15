package com.siberanka.twicosmetics.config;

import com.siberanka.twicosmetics.TwiCosmetics;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class TreasureManager {
    private static YamlConfiguration rewardFile;
    private static YamlConfiguration designFile;
    private static YamlConfiguration configFile;

    public TreasureManager(TwiCosmetics ultraCosmetics) {
        rewardFile = YamlConfiguration.loadConfiguration(new File(new File(ultraCosmetics.getDataFolder(), "/treasurechests"), "rewards.yml"));
        designFile = YamlConfiguration.loadConfiguration(new File(new File(ultraCosmetics.getDataFolder(), "/treasurechests"), "designs.yml"));
        configFile = YamlConfiguration.loadConfiguration(new File(new File(ultraCosmetics.getDataFolder(), "/treasurechests"), "config.yml"));
    }

    public static YamlConfiguration getRewardFile() {
        return rewardFile;
    }

    public static YamlConfiguration getDesignFile() {
        return designFile;
    }

    public static YamlConfiguration getConfigFile() {
        return configFile;
    }
}
