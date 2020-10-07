package io.skyfallsdk.packet.version.v1_15_2.handshake;

import io.netty.buffer.ByteBuf;
import io.skyfallsdk.net.NetConnection;
import io.skyfallsdk.packet.version.NetPacketIn;

public class HandshakeInLegacy extends NetPacketIn implements io.skyfallsdk.packet.handshake.HandshakeInLegacy {

    private static final byte PAYLOAD = 0x01;

    public HandshakeInLegacy() {
        super(HandshakeInLegacy.class);
    }

    @Override
    public void read(ByteBuf buf, NetConnection connection) {}

    @Override
    public byte getPayload() {
        return PAYLOAD;
    }
}
