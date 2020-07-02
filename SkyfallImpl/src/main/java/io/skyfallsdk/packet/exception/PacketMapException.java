package io.skyfallsdk.packet.exception;

import io.skyfallsdk.packet.Packet;
import io.skyfallsdk.protocol.ProtocolVersion;

public class PacketMapException extends PacketException {

    private final ProtocolVersion mappingTo;

    public PacketMapException(Class<? extends Packet> packet, ProtocolVersion mappingTo) {
        super(packet, "Error mapping packet: " + packet.getName() + " to version: " + mappingTo.name());

        this.mappingTo = mappingTo;
    }

    public PacketMapException(Class<? extends Packet> packet, ProtocolVersion mappingTo, String message) {
        super(packet, "Error mapping packet: " + packet.getName() + " to version: " + mappingTo.name() + " - " + message);

        this.mappingTo = mappingTo;
    }

    public ProtocolVersion getMappingTo() {
        return this.mappingTo;
    }
}
