package com.siberanka.twicosmetics.player.profile;

import com.siberanka.twicosmetics.TwiCosmetics;
import com.siberanka.twicosmetics.player.UltraPlayer;

/**
 * Used to save what cosmetics a player toggled.
 */
public class FileCosmeticsProfile extends CosmeticsProfile {

    public FileCosmeticsProfile(UltraPlayer ultraPlayer, TwiCosmetics ultraCosmetics) {
        super(ultraPlayer, ultraCosmetics);
    }

    @Override
    protected void load() {
        data.loadFromFile();
    }

    @Override
    public void save() {
        ultraCosmetics.getProfileSaveService().queue(data);
    }
}
