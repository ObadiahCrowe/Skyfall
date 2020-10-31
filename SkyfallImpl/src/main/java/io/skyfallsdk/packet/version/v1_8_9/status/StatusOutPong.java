package io.skyfallsdk.packet.version.v1_8_9.status;

import io.netty.buffer.ByteBuf;

public class StatusOutPong extends io.skyfallsdk.packet.status.StatusOutPong {

    private final long payload;

    public StatusOutPong(long payload) {
        super(StatusOutPong.class);

        this.payload = payload;
    }

    @Override
    public void write(ByteBuf buf) {
        buf.writeLong(this.payload);
    }

    @Override
    public long getPayload() {
        return this.payload;
    }
}
