package io.skyfallsdk.world;

public enum Dimension {

    /**
     * The nether.
     */
    NETHER(-1),

    /**
     * The overworld.
     */
    OVERWORLD(0),

    /**
     * The end.
     */
    END(1);

    private final int id;

    Dimension(int id) {
        this.id = id;
    }

    /**
     * @return Id of the dimension.
     */
    public int getId() {
        return this.id;
    }
}
