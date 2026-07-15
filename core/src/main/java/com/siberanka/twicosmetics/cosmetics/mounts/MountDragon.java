package com.siberanka.twicosmetics.cosmetics.mounts;

import com.siberanka.twicosmetics.TwiCosmetics;
import com.siberanka.twicosmetics.TwiCosmeticsData;
import com.siberanka.twicosmetics.config.SettingsManager;
import com.siberanka.twicosmetics.cosmetics.type.MountType;
import com.siberanka.twicosmetics.player.UltraPlayer;
import com.siberanka.twicosmetics.run.FallDamageManager;
import com.siberanka.twicosmetics.util.EntityMountManager;
import com.siberanka.twicosmetics.util.EntitySpawningManager;
import me.gamercoder215.mobchip.EntityBrain;
import me.gamercoder215.mobchip.ai.controller.NaturalMoveType;
import me.gamercoder215.mobchip.bukkit.BukkitBrain;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Mob;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.util.Vector;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.toRadians;

/**
 * Represents an instance of a enderdragon mount.
 *
 * @author iSach
 * @since 08-17-2015
 */
public class MountDragon extends Mount {
    private EntityBrain brain;
    private Mob boost = null;

    public MountDragon(UltraPlayer owner, MountType type, TwiCosmetics ultraCosmetics) {
        super(owner, type, ultraCosmetics);
    }

    @Override
    protected void setupEntity() {
        if (getOwner().isGeyserClient()) {
            boost = EntitySpawningManager.withBypass(() -> (Mob) getPlayer().getWorld().spawnEntity(getPlayer().getLocation(), EntityType.GHAST));
            boost.setInvisible(true);
            boost.setAware(false);
            boost.setPersistent(false);
            entity.removePassenger(getPlayer());
            EntityMountManager.withBypass(() -> boost.addPassenger(getPlayer()));
            entity.addPassenger(boost);
            entity.setMetadata("Mount", new FixedMetadataValue(TwiCosmeticsData.get().getPlugin(), "TwiCosmetics"));
        }
        brain = BukkitBrain.getBrain((Mob) entity);
        // Setting hurt time prevents dragon from pushing or damaging other entities.
        // As of 1.21.8 this works around a Folia bug so it needs to occur immediately after spawning the entity.
        brain.getBody().setHurtTime(20);
    }

    @Override
    public void onUpdate() {
        if (boost != null && boost.getPassengers().isEmpty() && boost.getTicksLived() > 10) {
            clear();
        }
        brain.getBody().setHurtTime(20);
        if (SettingsManager.getConfig().getBoolean("Mounts." + getType().getConfigName() + ".Stationary")) return;

        float yaw = getPlayer().getLocation().getYaw();
        brain.getBody().setPitch(getPlayer().getLocation().getPitch());
        brain.getBody().setYaw(yaw - 180);

        double angleInRadians = toRadians(-yaw);

        double x = sin(angleInRadians);
        double z = cos(angleInRadians);

        Vector v = entity.getLocation().getDirection();

        brain.getController().naturalMoveTo(x, v.getY(), z, NaturalMoveType.SELF);
    }

    @Override
    protected void removeEntity() {
        super.removeEntity();
        if (boost != null) {
            boost.remove();
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onClear() {
        super.onClear();
        if (!getPlayer().isOnGround()) {
            FallDamageManager.addNoFall(getPlayer());
        }
    }
}
