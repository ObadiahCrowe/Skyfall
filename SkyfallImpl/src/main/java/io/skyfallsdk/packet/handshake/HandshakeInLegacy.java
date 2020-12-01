package io.skyfallsdk.packet.handshake;

import io.skyfallsdk.packet.PacketIn;

public interface HandshakeInLegacy extends PacketIn {

    byte getPayload();
}
