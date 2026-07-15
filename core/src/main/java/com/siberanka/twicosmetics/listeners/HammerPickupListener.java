package com.siberanka.twicosmetics.listeners;

import com.siberanka.twicosmetics.cosmetics.gadgets.GadgetThorHammer;
import org.bukkit.event.Listener;

public class HammerPickupListener implements Listener {
    private GadgetThorHammer gadget;

    public HammerPickupListener(GadgetThorHammer gadget) {
        this.gadget = gadget;
    }


}
