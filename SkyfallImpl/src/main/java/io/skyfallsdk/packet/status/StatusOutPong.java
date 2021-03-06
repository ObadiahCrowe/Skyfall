package io.skyfallsdk.packet.status;

import io.skyfallsdk.packet.Packet;
import io.skyfallsdk.packet.PacketOut;
import io.skyfallsdk.packet.version.NetPacketOut;

public abstract class StatusOutPong extends NetPacketOut {

    public StatusOutPong(Class<? extends Packet> packet) {
        super(packet);
    }

    @Override
    public Class<? extends PacketOut> getGeneric() {
        return StatusOutPong.class;
    }

    public abstract long getPayload();
}
