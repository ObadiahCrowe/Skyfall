package io.skyfallsdk.packet.version.v1_15_2.handshake;

import io.netty.buffer.ByteBuf;
import io.skyfallsdk.net.NetConnection;
import io.skyfallsdk.packet.Packet;
import io.skyfallsdk.packet.version.NetPacketIn;

public class HandshakeIn extends NetPacketIn {

    public HandshakeIn() {
        super(HandshakeIn.class);
    }

    @Override
    public void read(ByteBuf buf, NetConnection connection) {

    }
}
