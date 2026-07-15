package com.siberanka.twicosmetics.task;

import com.siberanka.twicosmetics.TwiCosmeticsData;
import com.tcoded.folialib.impl.PlatformScheduler;
import com.tcoded.folialib.wrapper.task.WrappedTask;
import org.bukkit.Location;
import org.bukkit.entity.Entity;

public abstract class UltraTask {
    protected WrappedTask task = null;

    public abstract void run();

    public abstract void schedule();

    public void cancel() {
        if (task != null) {
            task.cancel();
        }
    }

    protected PlatformScheduler getScheduler() {
        return TwiCosmeticsData.get().getPlugin().getScheduler();
    }

    // These methods allow tasks to be scheduled in NMS modules without transitive dependencies.
    public static void runLater(Runnable runnable, long delay) {
        TwiCosmeticsData.get().getPlugin().getScheduler().runLater(t -> runnable.run(), delay);
    }

    public static void runAtEntityLater(Entity entity, Runnable runnable, long delay) {
        TwiCosmeticsData.get().getPlugin().getScheduler().runAtEntityLater(entity, t -> runnable.run(), delay);
    }

    public static void runAtLocationLater(Location location, Runnable runnable, long delay) {
        TwiCosmeticsData.get().getPlugin().getScheduler().runAtLocationLater(location, t -> runnable.run(), delay);
    }
}
