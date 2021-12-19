package io.skyfallsdk.packet;

import io.skyfallsdk.subscription.type.Subscribable;

public interface Packet extends Subscribable {

    default int getId() {
        return PacketRegistry.getId(this.getClass());
    }

    default PacketState getState() {
        return PacketRegistry.getState(this.getClass());
    }

    PacketDestination getDestination();
}
