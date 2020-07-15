package io.skyfallsdk.world;

import io.skyfallsdk.util.AbstractVector;

public class Position extends AbstractVector<Position> {

    private static final long serialVersionUID = -1062685013873692988L;

    private final World world;

    private float yaw;
    private float pitch;

    public Position(World world, double x, double y, double z) {
        super(x, y, z);

        this.world = world;
    }

    public World getWorld() {
        return this.world;
    }

    
}
