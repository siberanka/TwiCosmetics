package com.siberanka.twicosmetics.cosmetics.mounts;

import com.siberanka.twicosmetics.TwiCosmetics;
import com.siberanka.twicosmetics.TwiCosmeticsData;
import com.siberanka.twicosmetics.cosmetics.type.MountType;
import com.siberanka.twicosmetics.player.UltraPlayer;
import com.siberanka.twicosmetics.util.EntitySpawningManager;
import com.siberanka.twicosmetics.util.PlayerUtils;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.vehicle.VehicleDestroyEvent;
import org.bukkit.util.Vector;

/**
 * Represents an instance of a hype cart mount.
 *
 * @author iSach
 * @since 08-03-2015
 */
public class MountHypeCart extends Mount {

    public MountHypeCart(UltraPlayer owner, MountType type, TwiCosmetics ultraCosmetics) {
        super(owner, type, ultraCosmetics);
    }

    @Override
    public void onUpdate() {
        if (entity.isOnGround()) {
            Vector vector = PlayerUtils.getHorizontalDirection(getPlayer(), 7.6);
            if (Math.abs(vector.getX()) > 4) {
                vector.setX(vector.getX() < 0 ? -4 : 4);
            }
            if (Math.abs(vector.getZ()) > 4) {
                vector.setZ(vector.getZ() < 0 ? -4 : 4);
            }
            entity.setVelocity(vector);
        }
    }

    @Override
    protected Entity spawnEntity() {
        entity = EntitySpawningManager.withBypass(() -> TwiCosmeticsData.get().getVersionManager().getModule().spawnCustomMinecart(getPlayer().getLocation()));
        return entity;
    }

    @Override
    protected void removeEntity() {
        TwiCosmeticsData.get().getVersionManager().getModule().removeCustomEntity(entity);
    }

    @EventHandler
    public void onDestroy(VehicleDestroyEvent event) {
        if (event.getVehicle() == entity) {
            event.setCancelled(true);
        }
    }
}
