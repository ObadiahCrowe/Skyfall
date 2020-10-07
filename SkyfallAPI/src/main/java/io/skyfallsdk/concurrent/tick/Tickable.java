package io.skyfallsdk.concurrent.tick;

/**
 * Represents a tickable object.
 */
public interface Tickable<T extends TickSpec> {

    default long getTickableId() {
        return this.getRegistry().getId(this);
    }

    default long getLastExecutionTime() {
        return this.getRegistry().getLastExecutionTime(this.getTickableId());
    }

    /**
     * Logic to run every tick.
     */
    void onTick();

    /**
     * @return The parent registry holding the containerised
     */
    TickRegistry<T> getRegistry();

    /**
     * @return The stage of a tick in which this tickable should be executed at.
     */
    TickStage getStage();
}
