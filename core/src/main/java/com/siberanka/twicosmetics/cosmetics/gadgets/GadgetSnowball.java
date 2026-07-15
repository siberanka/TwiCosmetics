package com.siberanka.twicosmetics.cosmetics.gadgets;

import com.siberanka.twicosmetics.TwiCosmetics;
import com.siberanka.twicosmetics.cosmetics.type.GadgetType;
import com.siberanka.twicosmetics.player.UltraPlayer;

import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an instance of a snowball gadget summoned by a player.
 *
 * @author iSach
 * @since 12-15-2015
 */
public class GadgetSnowball extends Gadget {

    private List<Snowball> snowballs = new ArrayList<>();

    public GadgetSnowball(UltraPlayer owner, GadgetType type, TwiCosmetics ultraCosmetics) {
        super(owner, type, ultraCosmetics);
    }

    @Override
    protected void onRightClick() {
        Snowball snowball = getPlayer().launchProjectile(Snowball.class);
        snowball.setVelocity(getPlayer().getEyeLocation().getDirection().multiply(1.85d));
        snowball.setMetadata("NO_DAMAGE", new FixedMetadataValue(getTwiCosmetics(), ""));
    }

    @Override
    public void onClear() {
        for (Snowball snowball : snowballs) {
            snowball.remove();
        }
        snowballs.clear();
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.getDamager().hasMetadata("NO_DAMAGE")) {
            event.setCancelled(true);
        }
    }
}
