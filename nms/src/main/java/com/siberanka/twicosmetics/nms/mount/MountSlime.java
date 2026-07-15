package com.siberanka.twicosmetics.nms.mount;

import com.siberanka.twicosmetics.TwiCosmetics;
import com.siberanka.twicosmetics.cosmetics.type.MountType;
import com.siberanka.twicosmetics.nms.customentities.CustomSlime;
import com.siberanka.twicosmetics.player.UltraPlayer;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.LivingEntity;
import org.bukkit.craftbukkit.entity.CraftPlayer;

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
        return new CustomSlime(EntityTypes.SLIME, ((CraftPlayer) getPlayer()).getHandle().level());
    }
}
