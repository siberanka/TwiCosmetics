package com.siberanka.twicosmetics.run;

import com.siberanka.twicosmetics.TwiCosmetics;
import com.siberanka.twicosmetics.config.MessageManager;
import com.siberanka.twicosmetics.cosmetics.PlayerAffectingCosmetic;
import com.siberanka.twicosmetics.player.UltraPlayer;
import com.siberanka.twicosmetics.player.UltraPlayerManager;
import com.siberanka.twicosmetics.task.UltraTask;
import org.bukkit.entity.Player;

/**
 * Checks for vanished players that still have cosmetics equipped.
 */
public class VanishChecker extends UltraTask {
    private final TwiCosmetics ultraCosmetics;
    private final UltraPlayerManager pm;

    public VanishChecker(TwiCosmetics ultraCosmetics) {
        this.ultraCosmetics = ultraCosmetics;
        this.pm = ultraCosmetics.getPlayerManager();
    }

    @Override
    public void run() {
        for (UltraPlayer ultraPlayer : pm.getUltraPlayers()) {
            Player player = ultraPlayer.getBukkitPlayer();
            if (player == null) continue;
            ultraCosmetics.getScheduler().runAtEntity(player, t -> {
                if (PlayerAffectingCosmetic.isVanished(player) && ultraPlayer.hasCosmeticsEquipped()) {
                    MessageManager.send(player, "Not-Allowed-In-Vanish");
                    ultraPlayer.clear();
                }
            });
        }
    }

    @Override
    public void schedule() {
        task = getScheduler().runTimer(this::run, 100, 100);
    }
}
