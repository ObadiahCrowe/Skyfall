package io.skyfallsdk.event;

public interface Cancellable {

    void setCancelled(boolean cancel);

    boolean isCancelled();
}
