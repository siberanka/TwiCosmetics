package com.siberanka.twicosmetics.run;

import com.siberanka.twicosmetics.TwiCosmetics;
import com.siberanka.twicosmetics.config.MessageManager;
import com.siberanka.twicosmetics.config.SettingsManager;
import com.siberanka.twicosmetics.player.UltraPlayer;
import com.siberanka.twicosmetics.task.UltraTask;
import org.bukkit.entity.Player;

/**
 * Project: TwiCosmetics
 * Package: com.siberanka.twicosmetics.tick
 * Created by: Sacha
 * Created on: 21th June, 2016
 * at 14:03
 */
public class InvalidWorldChecker extends UltraTask {

    private TwiCosmetics ultraCosmetics;

    public InvalidWorldChecker(TwiCosmetics ultraCosmetics) {
        this.ultraCosmetics = ultraCosmetics;
    }

    @Override
    public void run() {
        for (UltraPlayer ultraPlayer : ultraCosmetics.getPlayerManager().getUltraPlayers()) {
            Player p = ultraPlayer.getBukkitPlayer();
            // not sure what causes p to be null, but it happens in some circumstances apparently
            // https://mcpaste.io/1bbcbf856c5e503b
            if (p == null) continue;
            ultraCosmetics.getScheduler().runAtEntity(p, task -> {
                if (!SettingsManager.isAllowedWorld(p.getWorld())) {
                    ultraPlayer.removeMenuItem();
                    ultraPlayer.withPreserveEquipped(() -> {
                        if (ultraPlayer.clear()) MessageManager.send(p, "World-Disabled");
                    });
                }
            });
        }
    }

    @Override
    public void schedule() {
        task = getScheduler().runTimer(this::run, 1, 5);
    }
}
