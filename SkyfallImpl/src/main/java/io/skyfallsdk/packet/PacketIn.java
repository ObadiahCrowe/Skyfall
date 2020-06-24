package io.skyfallsdk.packet;

import io.netty.buffer.ByteBuf;
import io.skyfallsdk.net.NetConnection;

public abstract class PacketIn implements Packet {

    private final int id;
    private final PacketState state;

    public PacketIn(Class<? extends Packet> packet) {
        this.id = 0x00;
        this.state = PacketState.PLAY;
    }

    public abstract void read(ByteBuf buf, NetConnection connection);

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public PacketState getState() {
        return this.state;
    }

    @Override
    public PacketDestination getDestination() {
        return PacketDestination.IN;
    }
}
