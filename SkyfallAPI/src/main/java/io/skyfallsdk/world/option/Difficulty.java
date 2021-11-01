package io.skyfallsdk.world.option;

public enum Difficulty {

    /**
     * No hostile mobs.
     */
    PEACEFUL,

    /**
     * Weak hostile mobs, moderately fast health regen.
     */
    EASY,

    /**
     * Hostile mobs, regular health regen.
     */
    NORMAL,

    /**
     * Very hostile mobs, slower health regen, quicker loss of hunger. Hunger kills.
     */
    HARD;
}
