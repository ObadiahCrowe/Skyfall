package io.skyfallsdk.packet.version.v1_16_1.handshake;

import io.skyfallsdk.packet.PacketIn;
import io.skyfallsdk.packet.PacketState;

public class HandshakeIn extends PacketIn {

    @Override
    public int getId() {
        return 0;
    }

    @Override
    public PacketState getState() {
        return null;
    }
}
