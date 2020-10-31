package io.skyfallsdk.packet.version.v1_8_9.status;

import io.netty.buffer.ByteBuf;
import io.skyfallsdk.net.NetConnection;
import io.skyfallsdk.packet.version.NetPacketIn;

public class StatusInRequest extends NetPacketIn implements io.skyfallsdk.packet.status.StatusInRequest {

    public StatusInRequest() {
        super(StatusInRequest.class);
    }

    @Override
    public void read(ByteBuf buf, NetConnection connection) {

    }
}
