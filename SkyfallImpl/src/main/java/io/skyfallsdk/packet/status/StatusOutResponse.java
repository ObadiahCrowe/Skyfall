package io.skyfallsdk.packet.status;

import io.skyfallsdk.packet.Packet;
import io.skyfallsdk.packet.version.NetPacketOut;

public abstract class StatusOutResponse extends NetPacketOut {

    public StatusOutResponse(Class<? extends Packet> packet) {
        super(packet);
    }

    public abstract int getJsonLength();

    public abstract String getJsonResponse();
}
