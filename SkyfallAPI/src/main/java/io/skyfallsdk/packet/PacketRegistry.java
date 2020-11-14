package io.skyfallsdk.packet;

public interface PacketRegistry {

    int getId(Class<? extends Packet> packet);

    PacketState getState(Class<? extends Packet> packet);
}
