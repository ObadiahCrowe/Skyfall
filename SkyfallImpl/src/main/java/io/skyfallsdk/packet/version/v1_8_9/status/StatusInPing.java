package io.skyfallsdk.packet.version.v1_8_9.status;

import io.netty.buffer.ByteBuf;
import io.skyfallsdk.net.NetConnection;
import io.skyfallsdk.packet.version.NetPacketIn;

public class StatusInPing extends NetPacketIn implements io.skyfallsdk.packet.status.StatusInPing {

    private long payload;

    public StatusInPing() {
        super(StatusInPing.class);
    }

    @Override
    public void read(ByteBuf buf, NetConnection connection) {
        this.payload = buf.readLong();
    }

    @Override
    public long getPayload() {
        return this.payload;
    }
}
