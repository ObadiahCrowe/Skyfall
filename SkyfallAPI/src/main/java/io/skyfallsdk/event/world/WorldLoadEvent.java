package io.skyfallsdk.event.world;

import io.skyfallsdk.event.Cancellable;
import io.skyfallsdk.event.Event;
import io.skyfallsdk.world.World;

public class WorldLoadEvent implements Event, Cancellable {

    private final World world;

    private boolean cancelled;

    public WorldLoadEvent(World world) {
        this.world = world;

        this.cancelled = false;
    }

    public World getWorld() {
        return this.world;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }

    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }
}
