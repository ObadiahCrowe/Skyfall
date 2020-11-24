package io.skyfallsdk.packet;

import io.skyfallsdk.Server;
import io.skyfallsdk.subscription.Subscribable;

public interface Packet extends Subscribable {

    default int getId() {
        return Server.get().getPacketRegistry().getId(this.getClass());
    }

    default PacketState getState() {
        return Server.get().getPacketRegistry().getState(this.getClass());
    }

    PacketDestination getDestination();
}
