package io.skyfallsdk.subscription;

import io.skyfallsdk.event.Event;

public interface EventSubscription<T extends Event> {

    T getEvent();
}
