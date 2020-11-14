package io.skyfallsdk.packet.status;

import io.skyfallsdk.packet.PacketOut;

public abstract class StatusOutPong implements PacketOut {

    public abstract long getPayload();
}
