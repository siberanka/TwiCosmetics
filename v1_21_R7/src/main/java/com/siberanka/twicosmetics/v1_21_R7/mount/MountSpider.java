package com.siberanka.twicosmetics.v1_21_R7.mount;

import com.siberanka.twicosmetics.TwiCosmetics;
import com.siberanka.twicosmetics.cosmetics.type.MountType;
import com.siberanka.twicosmetics.player.UltraPlayer;
import com.siberanka.twicosmetics.v1_21_R7.customentities.RideableSpider;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import org.bukkit.craftbukkit.v1_21_R7.CraftWorld;

/**
 * @author RadBuilder
 */
public class MountSpider extends MountCustomEntity {
    public MountSpider(UltraPlayer owner, MountType type, TwiCosmetics ultraCosmetics) {
        super(owner, type, ultraCosmetics);
    }

    @Override
    public void onUpdate() {
    }

    @Override
    public LivingEntity getNewEntity() {
        return new RideableSpider(EntityType.SPIDER, ((CraftWorld) getPlayer().getWorld()).getHandle());
    }
}
