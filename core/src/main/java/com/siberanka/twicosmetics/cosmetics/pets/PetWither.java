package com.siberanka.twicosmetics.cosmetics.pets;

import com.siberanka.twicosmetics.TwiCosmetics;
import com.siberanka.twicosmetics.TwiCosmeticsData;
import com.siberanka.twicosmetics.config.SettingsManager;
import com.siberanka.twicosmetics.cosmetics.type.PetType;
import com.siberanka.twicosmetics.player.UltraPlayer;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Wither;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;

/**
 * Represents an instance of a wither pet summoned by a player.
 *
 * @author iSach
 * @since 08-12-2015
 */
public class PetWither extends Pet {

    public PetWither(UltraPlayer owner, PetType type, TwiCosmetics ultraCosmetics) {
        super(owner, type, ultraCosmetics);
    }

    @Override
    public void onUpdate() {
        // Do not call super.onUpdate(), wither does not drop items.
        // This runs onUpdate because if players walk in range, the bossbar reappears
        String setting = SettingsManager.getConfig().getString(getOptionPath("Bossbar"), "in range");
        BossBar bar = ((Wither) entity).getBossBar();
        if (setting.equalsIgnoreCase("owner")) {
            bar.getPlayers().stream().filter(p -> p != getPlayer()).forEach(bar::removePlayer);
        } else if (setting.equalsIgnoreCase("none")) {
            bar.getPlayers().forEach(bar::removePlayer);
        }

        if (!SettingsManager.getConfig().getBoolean("Pets-Are-Babies")) return;
        TwiCosmeticsData.get().getVersionManager().getEntityUtil().resetWitherSize((Wither) entity);
    }

    @EventHandler
    public void onShoot(ProjectileLaunchEvent event) {
        if (event.getEntity().getShooter() == entity) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockBreak(EntityChangeBlockEvent event) {
        if (event.getEntity() == entity) {
            event.setCancelled(true);
        }
    }
}
