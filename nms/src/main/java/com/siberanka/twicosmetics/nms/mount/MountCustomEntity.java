package com.siberanka.twicosmetics.nms.mount;

import com.siberanka.twicosmetics.TwiCosmetics;
import com.siberanka.twicosmetics.cosmetics.mounts.Mount;
import com.siberanka.twicosmetics.cosmetics.type.MountType;
import com.siberanka.twicosmetics.nms.customentities.CustomEntities;
import com.siberanka.twicosmetics.player.UltraPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.bukkit.craftbukkit.entity.CraftEntity;

/**
 * @author RadBuilder
 */
public abstract class MountCustomEntity extends Mount {

    public MountCustomEntity(UltraPlayer owner, MountType type, TwiCosmetics ultraCosmetics) {
        super(owner, type, ultraCosmetics);
    }

    @Override
    public org.bukkit.entity.Entity spawnEntity() {
        entity = CustomEntities.spawnEntity(getNewEntity(), getPlayer().getLocation());
        // must refer to entity as a LivingEntity
        ((LivingEntity) getCustomEntity()).setSpeed((float) getType().getMovementSpeed());
        return getEntity();
    }

    @Override
    protected void removeEntity() {
        CustomEntities.removeCustomEntity(getCustomEntity());
    }

    public Entity getCustomEntity() {
        return ((CraftEntity) entity).getHandle();
    }

    public abstract Entity getNewEntity();
}
