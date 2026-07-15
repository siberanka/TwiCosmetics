package com.siberanka.twicosmetics.nms.mount;

import com.siberanka.twicosmetics.TwiCosmetics;
import com.siberanka.twicosmetics.cosmetics.type.MountType;
import com.siberanka.twicosmetics.nms.customentities.RideableSpider;
import com.siberanka.twicosmetics.player.UltraPlayer;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.LivingEntity;
import org.bukkit.craftbukkit.CraftWorld;

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
        return new RideableSpider(EntityTypes.SPIDER, ((CraftWorld) getPlayer().getWorld()).getHandle());
    }
}
