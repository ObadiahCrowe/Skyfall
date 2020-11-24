package io.skyfallsdk.packet.login;

import io.netty.buffer.ByteBuf;
import io.skyfallsdk.packet.Packet;
import io.skyfallsdk.packet.PacketOut;
import io.skyfallsdk.packet.version.NetPacketOut;

public class LoginOutLoginSuccess extends NetPacketOut {

    public LoginOutLoginSuccess(Class<? extends Packet> packet) {
        super(packet);
    }

    @Override
    public Class<? extends PacketOut> getGeneric() {
        return LoginOutLoginSuccess.class;
    }

    @Override
    public void write(ByteBuf buf) {

    }
}
