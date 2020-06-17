package io.skyfallsdk.event;

import javax.annotation.concurrent.NotThreadSafe;

/**
 * Represents an event.
 *
 * Not thread safe as all events of a type are executed in a specified order in accordance with their priority.
 */
@NotThreadSafe
public interface Event {

    default String getEventName() {
        return this.getClass().getSimpleName();
    }
}
