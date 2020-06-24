package io.skyfallsdk.packet;

import io.netty.buffer.ByteBuf;

public abstract class PacketOut implements Packet {

    private final int id;
    private final PacketState state;

    public PacketOut(Class<? extends Packet> packet) {
        this.id = 0x00;
        this.state = PacketState.PLAY;
    }

    public abstract void write(ByteBuf buf);

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
        return PacketDestination.OUT;
    }
}
