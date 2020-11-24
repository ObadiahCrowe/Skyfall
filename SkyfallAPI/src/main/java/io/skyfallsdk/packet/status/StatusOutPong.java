package io.skyfallsdk.packet.status;

import io.skyfallsdk.packet.PacketOut;

public class StatusOutPong implements PacketOut {

    private final long payload;

    public StatusOutPong(long payload) {
        this.payload = payload;
    }

    public long getPayload() {
        return this.payload;
    }
}
