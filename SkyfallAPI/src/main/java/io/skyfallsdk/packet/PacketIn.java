package io.skyfallsdk.packet;

public interface PacketIn extends Packet {

    @Override
    default PacketDestination getDestination() {
        return PacketDestination.IN;
    }
}
