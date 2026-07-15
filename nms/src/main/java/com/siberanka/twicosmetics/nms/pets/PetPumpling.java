package com.siberanka.twicosmetics.nms.pets;

import com.siberanka.twicosmetics.TwiCosmetics;
import com.siberanka.twicosmetics.cosmetics.type.PetType;
import com.siberanka.twicosmetics.nms.customentities.Pumpling;
import com.siberanka.twicosmetics.player.UltraPlayer;
import net.minecraft.world.entity.LivingEntity;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.ItemStack;

/**
 * @author RadBuilder
 */
public class PetPumpling extends CustomEntityPet {
    public PetPumpling(UltraPlayer owner, PetType type, TwiCosmetics ultraCosmetics) {
        super(owner, type, ultraCosmetics);
    }

    @Override
    public LivingEntity getNewEntity() {
        return new Pumpling(((CraftPlayer) getPlayer()).getHandle().level());
    }

    @Override
    public void setupEntity() {
        // use API when we can
        Zombie zombie = (Zombie) getEntity();
        zombie.setBaby();
        zombie.getEquipment().setHelmet(new ItemStack(Material.CARVED_PUMPKIN));
    }

    @Override
    public void onUpdate() {
        // Is this necesssary?
        getNMSEntity().setRemainingFireTicks(0);
        Location loc = ((Zombie) getEntity()).getEyeLocation();
        loc.getWorld().spawnParticle(Particle.FLAME, loc, 3, 0.2, 0.2, 0.2);
        // this doesn't seem to work when just in setupEntity()
        getNMSEntity().setInvisible(true);
    }
}
