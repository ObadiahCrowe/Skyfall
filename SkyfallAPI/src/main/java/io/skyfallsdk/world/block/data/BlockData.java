package io.skyfallsdk.world.block.data;

/**
 * Block data, with the type indicating how the state will be adjusted.
 *
 * @param <T> State type.
 */
public interface BlockData<T> {

    T getState();

    void setState(T state);
}
