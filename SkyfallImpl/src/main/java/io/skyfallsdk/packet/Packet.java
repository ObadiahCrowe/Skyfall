package io.skyfallsdk.packet;

public interface Packet {

    int getId();

    PacketState getState();

    PacketDestination getDestination();
}
