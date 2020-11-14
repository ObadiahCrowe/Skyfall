package io.skyfallsdk.packet.version.v1_16_1.handshake;

import io.netty.buffer.ByteBuf;
import io.skyfallsdk.net.NetClient;
import io.skyfallsdk.packet.exception.PacketException;
import io.skyfallsdk.packet.version.NetPacketIn;

public class HandshakeInLegacy extends NetPacketIn implements io.skyfallsdk.packet.handshake.HandshakeInLegacy {

    public HandshakeInLegacy() {
        super(HandshakeInLegacy.class);
    }

    @Override
    public void read(ByteBuf buf, NetClient connection) {
        if (buf.readUnsignedByte() != 1) {
            throw new PacketException(this, "Using wrong packet schema!");
        }
    }

    @Override
    public byte getPayload() {
        return 0x01;
    }
}
