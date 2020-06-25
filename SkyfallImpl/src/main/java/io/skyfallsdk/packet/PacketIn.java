package io.skyfallsdk.packet;

import io.netty.buffer.ByteBuf;
import io.skyfallsdk.net.NetConnection;

public interface PacketIn extends Packet {

    void read(ByteBuf buf, NetConnection connection);

    @Override
    default PacketDestination getDestination() {
        return PacketDestination.IN;
    }
}
