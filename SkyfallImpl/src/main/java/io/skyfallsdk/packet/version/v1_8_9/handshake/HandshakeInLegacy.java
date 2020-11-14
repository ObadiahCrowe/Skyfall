package io.skyfallsdk.packet.version.v1_8_9.handshake;

import io.netty.buffer.ByteBuf;
import io.skyfallsdk.net.NetClient;
import io.skyfallsdk.packet.version.NetPacketIn;

public class HandshakeInLegacy extends NetPacketIn implements io.skyfallsdk.packet.handshake.HandshakeInLegacy {

    private static final byte PAYLOAD = 0x01;

    public HandshakeInLegacy() {
        super(HandshakeInLegacy.class);
    }

    @Override
    public void read(ByteBuf buf, NetClient connection) {}

    @Override
    public byte getPayload() {
        return PAYLOAD;
    }
}
