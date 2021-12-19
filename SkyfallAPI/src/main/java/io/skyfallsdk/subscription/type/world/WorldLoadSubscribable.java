package io.skyfallsdk.subscription.type.world;

import io.skyfallsdk.subscription.type.Subscribable;
import io.skyfallsdk.world.World;
import org.jetbrains.annotations.NotNull;

public class WorldLoadSubscribable implements Subscribable {

    private final World world;

    public WorldLoadSubscribable(@NotNull World world) {
        this.world = world;
    }

    public @NotNull World getWorld() {
        return this.world;
    }
}
