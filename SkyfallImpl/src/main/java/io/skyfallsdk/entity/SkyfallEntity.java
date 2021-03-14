package io.skyfallsdk.entity;

import io.skyfallsdk.concurrent.tick.TickStage;
import io.skyfallsdk.world.Position;
import io.skyfallsdk.world.World;
import io.skyfallsdk.world.vector.Vector;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class SkyfallEntity implements Entity {

    private static final AtomicInteger ENTITY_ID = new AtomicInteger(0);

    private final int id;
    private final UUID uuid;

    public SkyfallEntity() {
        this.id = ENTITY_ID.incrementAndGet();
        this.uuid = UUID.randomUUID();
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public UUID getUuid() {
        return this.uuid;
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
    public void setPosition(Position position) {

    }

    @Override
    public Vector getVelocity() {
        return null;
    }

    @Override
    public void setVelocity(Vector velocity) {

    }

    @Override
    public void onTick() {

    }

    @Override
    public TickStage getStage() {
        return null;
    }
}
