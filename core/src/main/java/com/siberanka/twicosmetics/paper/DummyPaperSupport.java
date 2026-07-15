package com.siberanka.twicosmetics.paper;

import com.siberanka.twicosmetics.config.MessageManager;
import org.bukkit.Nameable;
import org.bukkit.entity.Player;

public class DummyPaperSupport implements PaperSupport {
    @Override
    public boolean hasParticlesDisabled(Player player) {
        return false;
    }

    @Override
    public void setCustomName(Nameable nameable, String serializedComponent) {
        nameable.setCustomName(MessageManager.toLegacy(MessageManager.getMiniMessage().deserialize(serializedComponent)));
    }
}
