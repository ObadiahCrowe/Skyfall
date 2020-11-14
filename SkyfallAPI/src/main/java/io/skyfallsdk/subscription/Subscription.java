package io.skyfallsdk.subscription;

public interface Subscription<T extends Subscribable> {

    T getSubscribable();
}
