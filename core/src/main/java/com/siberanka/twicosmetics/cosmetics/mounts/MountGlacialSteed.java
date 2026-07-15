package com.siberanka.twicosmetics.cosmetics.mounts;

import com.siberanka.twicosmetics.TwiCosmetics;
import com.siberanka.twicosmetics.cosmetics.type.MountType;
import com.siberanka.twicosmetics.player.UltraPlayer;
import com.cryptomorin.xseries.XMaterial;
import com.cryptomorin.xseries.particles.ParticleDisplay;
import com.cryptomorin.xseries.particles.XParticle;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Horse;
import org.bukkit.inventory.ItemStack;

/**
 * Represents an instance of a glacial steed mount.
 *
 * @author iSach
 * @since 08-10-2015
 */
public class MountGlacialSteed extends MountAbstractHorse {
    private static final ItemStack BOOTS = XMaterial.LEATHER_BOOTS.parseItem();

    static {
        BOOTS.addEnchantment(Enchantment.FROST_WALKER, 2);
    }

    private final ParticleDisplay display;

    public MountGlacialSteed(UltraPlayer owner, MountType type, TwiCosmetics ultraCosmetics) {
        super(owner, type, ultraCosmetics);
        display = ParticleDisplay.of(XParticle.ITEM_SNOWBALL).offset(0.4, 0.2, 0.4).withCount(5)
                .withLocationCaller(() -> entity.getLocation().add(0, 1, 0));
    }

    @Override
    public void setupEntity() {
        super.setupEntity();
        ((Horse) entity).setColor(Horse.Color.WHITE);
        if (BOOTS != null) {
            ((Horse) entity).getEquipment().setBoots(BOOTS, true);
        }
    }

    @Override
    public void onUpdate() {
        display.spawn();
    }
}
