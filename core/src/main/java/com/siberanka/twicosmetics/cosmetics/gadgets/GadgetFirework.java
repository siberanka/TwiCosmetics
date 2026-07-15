package com.siberanka.twicosmetics.cosmetics.gadgets;

import com.siberanka.twicosmetics.TwiCosmetics;
import com.siberanka.twicosmetics.cosmetics.type.GadgetType;
import com.siberanka.twicosmetics.player.UltraPlayer;
import com.cryptomorin.xseries.XEntityType;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.metadata.FixedMetadataValue;

/**
 * Represents an instance of a firework gadget summoned by a player.
 *
 * @author iSach
 * @since 11-11-2015
 */
public class GadgetFirework extends Gadget {
    private static final EntityType FIREWORK_ENTITY = XEntityType.FIREWORK_ROCKET.get();

    public GadgetFirework(UltraPlayer owner, GadgetType type, TwiCosmetics ultraCosmetics) {
        super(owner, type, ultraCosmetics);
    }

    @Override
    protected void onRightClick() {
        Firework fw = (Firework) getPlayer().getWorld().spawnEntity(getPlayer().getLocation(), FIREWORK_ENTITY);
        FireworkMeta fwm = fw.getFireworkMeta();

        int rt = RANDOM.nextInt(5);
        FireworkEffect.Type type = FireworkEffect.Type.values()[rt];

        Color c1 = Color.fromRGB(RANDOM.nextInt(256), RANDOM.nextInt(256), RANDOM.nextInt(256));
        Color c2 = Color.fromRGB(RANDOM.nextInt(256), RANDOM.nextInt(256), RANDOM.nextInt(256));

        FireworkEffect effect = FireworkEffect.builder().flicker(RANDOM.nextBoolean())
                .withColor(c1).withFade(c2).with(type)
                .trail(RANDOM.nextBoolean()).build();

        fwm.addEffect(effect);

        fwm.setPower(RANDOM.nextInt(3));

        fw.setFireworkMeta(fwm);
        fw.setMetadata("uc_firework", new FixedMetadataValue(getTwiCosmetics(), true));
    }
}
