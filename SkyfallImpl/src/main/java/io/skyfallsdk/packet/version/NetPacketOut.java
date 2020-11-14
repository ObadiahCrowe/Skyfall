package io.skyfallsdk.packet.version;

import io.netty.buffer.ByteBuf;
import io.skyfallsdk.packet.Packet;
import io.skyfallsdk.packet.PacketDestination;
import io.skyfallsdk.packet.PacketOut;
import io.skyfallsdk.packet.PacketState;

public abstract class NetPacketOut implements PacketOut {

    private final int id;
    private final PacketState state;

    public NetPacketOut(Class<? extends Packet> packet) {
        this.id = 0x00;
        this.state = PacketState.PLAY;
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
