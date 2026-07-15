package com.siberanka.twicosmetics.v1_21_R7.mount;

import com.siberanka.twicosmetics.TwiCosmetics;
import com.siberanka.twicosmetics.cosmetics.type.MountType;
import com.siberanka.twicosmetics.player.UltraPlayer;
import com.siberanka.twicosmetics.v1_21_R7.customentities.CustomSlime;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import org.bukkit.craftbukkit.v1_21_R7.entity.CraftPlayer;

/**
 * @author RadBuilder
 */
public class MountSlime extends MountCustomEntity {

    public MountSlime(UltraPlayer owner, MountType type, TwiCosmetics ultraCosmetics) {
        super(owner, type, ultraCosmetics);
    }

    @Override
    public void onUpdate() {
    }

    @Override
    public LivingEntity getNewEntity() {
        return new CustomSlime(EntityType.SLIME, ((CraftPlayer) getPlayer()).getHandle().level());
    }
}
