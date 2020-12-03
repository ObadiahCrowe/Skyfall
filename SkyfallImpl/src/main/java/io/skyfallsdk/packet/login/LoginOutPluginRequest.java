package io.skyfallsdk.packet.login;

import io.skyfallsdk.packet.Packet;
import io.skyfallsdk.packet.PacketOut;
import io.skyfallsdk.packet.version.NetPacketOut;

public abstract class LoginOutPluginRequest extends NetPacketOut {

    public LoginOutPluginRequest(Class<? extends Packet> packet) {
        super(packet);
    }

    @Override
    public Class<? extends PacketOut> getGeneric() {
        return LoginOutPluginRequest.class;
    }

    public abstract int getMessageId();

    public abstract String getChannel();

    public abstract byte[] getData();
}
