package io.skyfallsdk.packet.status;

import io.skyfallsdk.packet.Packet;
import io.skyfallsdk.packet.version.NetPacketOut;

public abstract class StatusOutPong extends NetPacketOut {

    public StatusOutPong(Class<? extends Packet> packet) {
        super(packet);
    }

    public abstract long getPayload();
}
