package io.skyfallsdk.net;

import io.skyfallsdk.protocol.ProtocolVersion;

public class NetConnection {

    private final ProtocolVersion version;

    public NetConnection(ProtocolVersion version) {
        this.version = version;
    }

    public ProtocolVersion getVersion() {
        return this.version;
    }
}
