package io.skyfallsdk.entity;

import io.skyfallsdk.concurrent.tick.TickStage;
import io.skyfallsdk.world.Position;
import io.skyfallsdk.world.World;
import io.skyfallsdk.world.vector.Vector;

import java.util.UUID;

public class SkyfallEntity implements Entity {

    @Override
    public int getId() {
        return 0;
    }

    @Override
    public UUID getUuid() {
        return null;
    }

    @Override
    public World getWorld() {
        return null;
    }

    @Override
    public Position getPosition() {
        return null;
    }

    @Override
    public Vector getVelocity() {
        return null;
    }

    @Override
    public void onTick() {

    }

    @Override
    public TickStage getStage() {
        return null;
    }
}
