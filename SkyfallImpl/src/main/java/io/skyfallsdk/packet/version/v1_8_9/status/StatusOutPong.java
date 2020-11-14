package io.skyfallsdk.packet.version.v1_8_9.status;

import io.netty.buffer.ByteBuf;
import io.skyfallsdk.packet.PacketOut;
import io.skyfallsdk.packet.version.NetPacketOut;

public class StatusOutPong extends NetPacketOut {

    private final long payload;

    public StatusOutPong(long payload) {
        super(StatusOutPong.class);

        this.payload = payload;
    }

    @Override
    public Class<? extends PacketOut> getGeneric() {
        return null;
    }

    @Override
    public void write(ByteBuf buf) {
        buf.writeLong(this.payload);
    }

/*    @Override
    public long getPayload() {
        return this.payload;
    }*/
}
