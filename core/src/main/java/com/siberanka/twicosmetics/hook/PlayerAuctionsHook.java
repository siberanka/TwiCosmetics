package com.siberanka.twicosmetics.hook;

import com.siberanka.twicosmetics.TwiCosmetics;
import com.siberanka.twicosmetics.listeners.UnmovableItemListener;
import com.olziedev.playerauctions.api.events.auction.PlayerAuctionSellEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerAuctionsHook implements Listener {
    private final UnmovableItemListener unmovableItemListener;

    public PlayerAuctionsHook(TwiCosmetics ultraCosmetics) {
        this.unmovableItemListener = ultraCosmetics.getUnmovableItemListener();
    }

    @EventHandler
    public void onSell(PlayerAuctionSellEvent event) {
        if (unmovableItemListener.isImmovable(event.getSeller(), event.getPlayerAuction().getItem())) {
            event.setCancelled(true);
        }
    }
}
