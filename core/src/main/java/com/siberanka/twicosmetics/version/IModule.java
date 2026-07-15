package com.siberanka.twicosmetics.version;

import com.siberanka.twicosmetics.cosmetics.morphs.Morph;
import com.siberanka.twicosmetics.cosmetics.mounts.Mount;
import com.siberanka.twicosmetics.cosmetics.pets.Pet;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public interface IModule {

    public Class<? extends Mount> getSpiderClass();

    public Class<? extends Mount> getSlimeClass();

    Class<? extends Pet> getPumplingClass();

    Class<? extends Morph> getElderGuardianClass();

    Entity spawnCustomMinecart(Location location);

    void removeCustomEntity(Entity entity);

    void spawnFirework(Location location, FireworkEffect effect, Player... players);
}
