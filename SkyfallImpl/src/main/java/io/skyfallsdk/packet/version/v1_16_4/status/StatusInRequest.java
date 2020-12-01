package io.skyfallsdk.packet.version.v1_16_4.status;

import io.netty.buffer.ByteBuf;
import io.skyfallsdk.net.NetClient;
import io.skyfallsdk.packet.version.NetPacketIn;
import io.skyfallsdk.util.PingResponse;

public class StatusInRequest extends NetPacketIn implements io.skyfallsdk.packet.status.StatusInRequest {

    public StatusInRequest() {
        super(StatusInRequest.class);
    }

    @Override
    public void read(ByteBuf buf, NetClient connection) {
        connection.sendPacket(new StatusOutResponse(PingResponse.createResponse(connection)));
    }
}
