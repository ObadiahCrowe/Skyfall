package io.skyfallsdk.packet.status;

import io.skyfallsdk.packet.Packet;
import io.skyfallsdk.packet.PacketOut;

public abstract class StatusOutResponse extends PacketOut {

    public StatusOutResponse(Class<? extends Packet> packet) {
        super(packet);
    }

    public abstract int getJsonLength();

    public abstract String getJsonResponse();
}
