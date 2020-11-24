package io.skyfallsdk.packet.version;

import io.netty.buffer.ByteBuf;
import io.skyfallsdk.Server;
import io.skyfallsdk.packet.*;

public abstract class NetPacketOut implements PacketOut {

    private final int id;
    private final PacketState state;

    public NetPacketOut(Class<? extends Packet> packet) {
        this.id = NetPacketRegistry.getId(packet);
        this.state = NetPacketRegistry.getState(packet);
    }

    public abstract Class<? extends PacketOut> getGeneric();

    public abstract void write(ByteBuf buf);

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public PacketState getState() {
        return this.state;
    }
}
