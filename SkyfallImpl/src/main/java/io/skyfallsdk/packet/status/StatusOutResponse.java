package io.skyfallsdk.packet.status;

import io.skyfallsdk.packet.Packet;
import io.skyfallsdk.packet.PacketOut;
import io.skyfallsdk.packet.version.NetPacketOut;

public abstract class StatusOutResponse extends NetPacketOut {

    public StatusOutResponse(Class<? extends Packet> packet) {
        super(packet);
    }

    @Override
    public Class<? extends PacketOut> getGeneric() {
        return StatusOutResponse.class;
    }

    public abstract int getJsonLength();

    public abstract String getJsonResponse();
}
