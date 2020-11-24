package io.skyfallsdk.world;

import io.skyfallsdk.world.vector.AbstractVector;

import javax.annotation.concurrent.Immutable;

@Immutable
public class Position extends AbstractVector<Position> {

    private static final long serialVersionUID = -1062685013873692988L;

    private final World world;

    private final float yaw;
    private final float pitch;

    public Position(World world, double x, double y, double z) {
        super(x, y, z);

        this.world = world;

        this.yaw = 0.0F;
        this.pitch = 0.0F;
    }

    public World getWorld() {
        return this.world;
    }
}
