package io.skyfallsdk.world.option;

public enum Gamemode {

    /**
     * Survival mode - Regular gameplay.
     */
    SURVIVAL(0),

    /**
     * Creative mode - Flight, invincibility, unlimited items.
     */
    CREATIVE(1),

    /**
     * Adventure mode - Survival but cannot break blocks.
     */
    ADVENTURE(2),

    /**
     * Spectator mode - Flight, camera context switching, invisibility, invincibility.
     */
    SPECTATOR(3);

    private final int id;

    Gamemode(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }
}
