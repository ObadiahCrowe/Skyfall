package io.skyfallsdk.event;

import io.skyfallsdk.subscription.type.Subscribable;

import javax.annotation.concurrent.NotThreadSafe;

/**
 * Represents an event.
 *
 * Not thread safe as all events of a type are executed in a specified order in accordance with their priority.
 */
@NotThreadSafe
public interface Event extends Subscribable {

    default String getEventName() {
        return this.getClass().getSimpleName();
    }
}
