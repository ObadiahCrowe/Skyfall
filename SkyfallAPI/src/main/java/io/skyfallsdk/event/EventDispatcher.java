package io.skyfallsdk.event;

public interface EventDispatcher {

    <T extends Event> T callEvent(T event);
}
