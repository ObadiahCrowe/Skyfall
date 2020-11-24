package io.skyfallsdk.entity;

import io.skyfallsdk.concurrent.tick.DefaultTickSpec;
import io.skyfallsdk.concurrent.tick.TickRegistry;
import io.skyfallsdk.concurrent.tick.Tickable;
import io.skyfallsdk.world.Position;
import io.skyfallsdk.world.World;
import io.skyfallsdk.world.vector.Vector;

import java.util.UUID;

public interface Entity extends Tickable<DefaultTickSpec> {

    int getId();

    UUID getUuid();

    World getWorld();

    Position getPosition();

    Vector getVelocity();

    @Override
    default TickRegistry<DefaultTickSpec> getRegistry() {
        return DefaultTickSpec.ENTITY.getRegistry();
    }

    @Override
    default long getTickableId() {
        return this.getRegistry().getId(this);
    }

    @Override
    default long getLastExecutionTime() {
        return this.getRegistry().getLastExecutionTime(this);
    }
}
