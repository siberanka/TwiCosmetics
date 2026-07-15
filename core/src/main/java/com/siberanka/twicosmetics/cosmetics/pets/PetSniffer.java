package com.siberanka.twicosmetics.cosmetics.pets;

import com.siberanka.twicosmetics.TwiCosmetics;
import com.siberanka.twicosmetics.cosmetics.type.PetType;
import com.siberanka.twicosmetics.player.UltraPlayer;

public class PetSniffer extends Pet {
    public PetSniffer(UltraPlayer owner, PetType petType, TwiCosmetics ultraCosmetics) {
        super(owner, petType, ultraCosmetics);
    }

    @Override
    protected void setupEntity() {
        setMovementSpeed(0.2);
    }
}
