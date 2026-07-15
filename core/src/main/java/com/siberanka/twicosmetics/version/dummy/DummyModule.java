package com.siberanka.twicosmetics.version.dummy;

import com.siberanka.twicosmetics.cosmetics.morphs.Morph;
import com.siberanka.twicosmetics.cosmetics.mounts.Mount;
import com.siberanka.twicosmetics.cosmetics.pets.Pet;
import com.siberanka.twicosmetics.version.IModule;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class DummyModule implements IModule {

    @Override
    public Class<? extends Mount> getSpiderClass() {
        return null;
    }

    @Override
    public Class<? extends Mount> getSlimeClass() {
        return null;
    }

    @Override
    public Class<? extends Pet> getPumplingClass() {
        return null;
    }

    @Override
    public Class<? extends Morph> getElderGuardianClass() {
        return null;
    }

    @Override
    public Entity spawnCustomMinecart(Location location) {
        return null;
    }

    @Override
    public void removeCustomEntity(Entity entity) {
    }

    @Override
    public void spawnFirework(Location location, FireworkEffect effect, Player... players) {
    }

}
