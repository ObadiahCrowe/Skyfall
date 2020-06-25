package io.skyfallsdk.packet.exception;

import io.skyfallsdk.packet.Packet;

public class PacketException extends RuntimeException {

    private final Class<? extends Packet> packet;

    public PacketException(Class<? extends Packet> packet) {
        super("Error in packet: " + packet.getName());

        this.packet = packet;
    }

    public PacketException(Class<? extends Packet> packet, String message) {
        super("Error in packet: " + packet.getName() + " - " + message);

        this.packet = packet;
    }

    public PacketException(Packet packet) {
        this(packet.getClass());
    }

    public PacketException(Packet packet, String message) {
        this(packet.getClass(), message);
    }

    public Class<? extends Packet> getPacket() {
        return this.packet;
    }
}
