package com.siberanka.twicosmetics.nms.customentities;

import net.minecraft.world.entity.ai.goal.Goal;

import java.util.EnumSet;

public class CustomSlimeJumpGoal extends Goal {
    public CustomSlimeJumpGoal() {
        ((Goal) this).setFlags(EnumSet.of(Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        // Deliberately disables autonomous slime jumping; mounts are moved by rider input.
        return false;
    }
}
