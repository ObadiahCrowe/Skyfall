package io.skyfallsdk.world.vector;

import javax.annotation.concurrent.Immutable;
import java.io.Serializable;

@Immutable
public abstract class AbstractVector<T extends AbstractVector<T>> implements Serializable {

    private static final long serialVersionUID = -6747473014221445194L;

    private final double x;
    private final double y;
    private final double z;

    public AbstractVector(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public AbstractVector(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
}
