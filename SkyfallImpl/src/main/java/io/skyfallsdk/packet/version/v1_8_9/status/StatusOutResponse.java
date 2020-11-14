package io.skyfallsdk.packet.version.v1_8_9.status;

import io.netty.buffer.ByteBuf;
import io.skyfallsdk.net.NetData;
import io.skyfallsdk.packet.PacketOut;
import io.skyfallsdk.util.PingResponse;

public class StatusOutResponse extends io.skyfallsdk.packet.status.StatusOutResponse {

    private final String json;

    public StatusOutResponse(PingResponse response) {
        super(StatusOutResponse.class);

        this.json = response.toJson();
    }

    @Override
    public int getJsonLength() {
        return this.json.length();
    }

    @Override
    public String getJsonResponse() {
        return this.json;
    }

    @Override
    public Class<? extends PacketOut> getGeneric() {
        return null;
    }

    @Override
    public void write(ByteBuf buf) {
        NetData.writeString(buf, this.json);
    }
}
