package com.siberanka.twicosmetics.menu.buttons;

import com.siberanka.twicosmetics.menu.Button;
import com.siberanka.twicosmetics.menu.ClickData;
import com.siberanka.twicosmetics.menu.CosmeticMenu;
import com.siberanka.twicosmetics.player.UltraPlayer;

public abstract class ChangePageButton implements Button {
    private final int modifier;

    public ChangePageButton(int modifier) {
        this.modifier = modifier;
    }

    @Override
    public void onClick(ClickData clickData) {
        if (!(clickData.getMenu() instanceof CosmeticMenu)) {
            return;
        }
        UltraPlayer player = clickData.getClicker();
        CosmeticMenu<?> menu = (CosmeticMenu<?>) clickData.getMenu();
        menu.open(player, menu.getCurrentPage(player) + modifier);
    }
}
