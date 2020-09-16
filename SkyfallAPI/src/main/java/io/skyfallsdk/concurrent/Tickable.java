package io.skyfallsdk.concurrent;

/**
 * Represents a tickable object.
 */
public interface Tickable {

    default void register() {
        //
    }

    default void deregister() {
        //
    }

    /**
     * Logic to run every tick.
     */
    void onTick();
}
