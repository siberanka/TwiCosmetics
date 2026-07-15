package com.siberanka.twicosmetics.nms;

import com.siberanka.twicosmetics.cosmetics.morphs.Morph;
import com.siberanka.twicosmetics.cosmetics.mounts.Mount;
import com.siberanka.twicosmetics.cosmetics.pets.Pet;
import com.siberanka.twicosmetics.nms.customentities.CustomEntities;
import com.siberanka.twicosmetics.nms.customentities.CustomEntityFirework;
import com.siberanka.twicosmetics.nms.customentities.CustomMinecart;
import com.siberanka.twicosmetics.nms.morphs.MorphElderGuardian;
import com.siberanka.twicosmetics.nms.mount.MountSlime;
import com.siberanka.twicosmetics.nms.mount.MountSpider;
import com.siberanka.twicosmetics.nms.pets.PetPumpling;
import com.siberanka.twicosmetics.version.IModule;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityTypes;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.craftbukkit.entity.CraftEntity;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;

/**
 * @author RadBuilder
 */
public class VersionModule implements IModule {
    @Override
    public Class<? extends Mount> getSpiderClass() {
        return MountSpider.class;
    }

    @Override
    public Class<? extends Mount> getSlimeClass() {
        return MountSlime.class;
    }

    @Override
    public Class<? extends Pet> getPumplingClass() {
        return PetPumpling.class;
    }

    @Override
    public Class<? extends Morph> getElderGuardianClass() {
        return MorphElderGuardian.class;
    }

    @Override
    public org.bukkit.entity.Entity spawnCustomMinecart(Location location) {
        return CustomEntities.spawnEntity(
                new CustomMinecart(EntityTypes.MINECART, ((CraftWorld) location.getWorld()).getHandle()), location);
    }

    @Override
    public void removeCustomEntity(org.bukkit.entity.Entity entity) {
        CustomEntities.removeCustomEntity(((CraftEntity) entity).getHandle());
    }

    @Override
    public void spawnFirework(Location location, FireworkEffect effect, Player... players) {
        spawnFirework_(location, effect, players);
    }

    public static void spawnFirework_(Location location, FireworkEffect effect, Player... players) {
        CustomEntityFirework firework =
                new CustomEntityFirework(((CraftWorld) location.getWorld()).getHandle(), players);
        FireworkMeta meta = ((Firework) firework.getBukkitEntity()).getFireworkMeta();
        meta.addEffect(effect);
        ((Firework) firework.getBukkitEntity()).setFireworkMeta(meta);
        ((Entity) firework).setPos(location.getX(), location.getY(), location.getZ());

        if ((((CraftWorld) location.getWorld()).getHandle()).addFreshEntity(firework)) {
            ((Entity) firework).setInvisible(true);
        }
    }
}
