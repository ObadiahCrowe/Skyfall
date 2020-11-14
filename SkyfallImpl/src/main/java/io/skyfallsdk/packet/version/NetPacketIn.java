package io.skyfallsdk.packet.version;

import io.netty.buffer.ByteBuf;
import io.skyfallsdk.net.NetClient;
import io.skyfallsdk.packet.Packet;
import io.skyfallsdk.packet.PacketIn;
import io.skyfallsdk.packet.PacketState;

public abstract class NetPacketIn implements PacketIn {

    private final Class<? extends Packet> packetClass;

    public NetPacketIn(Class<? extends Packet> packetClass) {
        this.packetClass = packetClass;
    }

    public abstract void read(ByteBuf buf, NetClient connection);

    @Override
    public int getId() {
        return 0;
    }

    @Override
    public PacketState getState() {
        return null;
    }
}
