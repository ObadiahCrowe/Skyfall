package io.skyfallsdk.packet.status;

import io.skyfallsdk.packet.PacketOut;

public abstract class StatusOutResponse extends PacketOut {

    public StatusOutResponse() {
        super(StatusOutResponse.class);
    }

    public abstract int getJsonLength();

    public abstract String getJsonResponse();
}
