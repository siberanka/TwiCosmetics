package com.siberanka.twicosmetics.menu.menus;

import com.siberanka.twicosmetics.TwiCosmetics;
import com.siberanka.twicosmetics.config.SettingsManager;
import com.siberanka.twicosmetics.cosmetics.Category;
import com.siberanka.twicosmetics.cosmetics.type.PetType;
import com.siberanka.twicosmetics.menu.CosmeticMenu;
import com.siberanka.twicosmetics.menu.buttons.RenamePetButton;
import com.siberanka.twicosmetics.player.UltraPlayer;
import org.bukkit.inventory.Inventory;

/**
 * Pet {@link com.siberanka.twicosmetics.menu.Menu Menu}.
 *
 * @author iSach
 * @since 08-23-2016
 */
public class MenuPets extends CosmeticMenu<PetType> {

    public MenuPets(TwiCosmetics ultraCosmetics) {
        super(ultraCosmetics, Category.PETS);
    }

    @Override
    protected void putItems(Inventory inventory, UltraPlayer ultraPlayer, int page) {
        addPetRenameItem(inventory, ultraPlayer);
    }

    private void addPetRenameItem(Inventory inventory, UltraPlayer player) {
        if (!SettingsManager.getConfig().getBoolean("Pets-Rename.Enabled")) return;
        if (SettingsManager.getConfig().getBoolean("Pets-Rename.Permission-Required")
                && !player.getBukkitPlayer().hasPermission("ultracosmetics.pets.rename")) {
            return;
        }
        int slot = inventory.getSize() - (getCategory().hasGoBackArrow() ? 4 : 6);
        putItem(inventory, slot, new RenamePetButton(ultraCosmetics), player);
    }
}
