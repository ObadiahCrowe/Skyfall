package io.skyfallsdk.packet.login;

import io.skyfallsdk.packet.Packet;
import io.skyfallsdk.packet.PacketOut;
import io.skyfallsdk.packet.version.NetPacketOut;

import java.util.UUID;

public abstract class LoginOutSuccess extends NetPacketOut {

    public LoginOutSuccess(Class<? extends Packet> packet) {
        super(packet);
    }

    @Override
    public Class<? extends PacketOut> getGeneric() {
        return LoginOutSuccess.class;
    }

    public abstract UUID getUuid();

    public abstract String getUsername();
}
