package io.skyfallsdk.subscription;

import io.skyfallsdk.subscription.type.Subscribable;

public interface Subscription<T extends Subscribable> {

    T getSubscribable();

    void publish(T subscribable);
}
