package io.skyfallsdk.packet.version.v1_17.status;

import io.netty.buffer.ByteBuf;
import io.skyfallsdk.net.NetClient;
import io.skyfallsdk.packet.version.NetPacketIn;

public class StatusInPing extends NetPacketIn implements io.skyfallsdk.packet.status.StatusInPing {

    private long payload;

    public StatusInPing() {
        super(StatusInPing.class);
    }

    @Override
    public long getPayload() {
        return this.payload;
    }

    @Override
    public void read(ByteBuf buf, NetClient connection) {
        this.payload = buf.readLong();

        connection.sendPacket(new StatusOutPong(this.payload));
    }
}
