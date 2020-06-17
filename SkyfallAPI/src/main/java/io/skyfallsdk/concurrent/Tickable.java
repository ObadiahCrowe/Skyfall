package io.skyfallsdk.concurrent;

/**
 * Represents a tickable object.
 */
public interface Tickable {

    /**
     * Logic to run every tick.
     */
    void onTick();
}
