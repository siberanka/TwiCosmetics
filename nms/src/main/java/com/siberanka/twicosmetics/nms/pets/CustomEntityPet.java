package com.siberanka.twicosmetics.nms.pets;

import com.siberanka.twicosmetics.TwiCosmetics;
import com.siberanka.twicosmetics.cosmetics.pets.Pet;
import com.siberanka.twicosmetics.cosmetics.type.PetType;
import com.siberanka.twicosmetics.nms.customentities.CustomEntities;
import com.siberanka.twicosmetics.player.UltraPlayer;
import net.minecraft.world.entity.Entity;
import org.bukkit.craftbukkit.entity.CraftEntity;
import org.bukkit.entity.Mob;

/**
 * @author RadBuilder
 */
public abstract class CustomEntityPet extends Pet {

    public CustomEntityPet(UltraPlayer owner, PetType petType, TwiCosmetics ultraCosmetics) {
        super(owner, petType, ultraCosmetics);
    }

    @Override
    public Mob spawnEntity() {
        entity = (Mob) CustomEntities.spawnEntity(getNewEntity(), getPlayer().getLocation());
        return entity;
    }

    @Override
    protected void removeEntity() {
        CustomEntities.removeCustomEntity(getNMSEntity());
    }

    @Override
    public boolean isCustomEntity() {
        return true;
    }

    public Entity getNMSEntity() {
        return ((CraftEntity) entity).getHandle();
    }

    public abstract Entity getNewEntity();
}
