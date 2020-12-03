package io.skyfallsdk.packet.login;

import io.skyfallsdk.packet.Packet;
import io.skyfallsdk.packet.PacketOut;
import io.skyfallsdk.packet.version.NetPacketOut;

public abstract class LoginOutSetCompression extends NetPacketOut {

    public LoginOutSetCompression(Class<? extends Packet> packet) {
        super(packet);
    }

    @Override
    public Class<? extends PacketOut> getGeneric() {
        return LoginOutSetCompression.class;
    }

    public abstract int getThreshold();
}
