package io.skyfallsdk.packet.login;

import io.skyfallsdk.packet.Packet;
import io.skyfallsdk.packet.PacketOut;
import io.skyfallsdk.packet.version.NetPacketOut;

public abstract class LoginOutEncryptionRequest extends NetPacketOut {

    public LoginOutEncryptionRequest(Class<? extends Packet> packet) {
        super(packet);
    }

    @Override
    public Class<? extends PacketOut> getGeneric() {
        return LoginOutEncryptionRequest.class;
    }
}
