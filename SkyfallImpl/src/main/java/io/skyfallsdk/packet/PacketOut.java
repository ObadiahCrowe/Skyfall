package io.skyfallsdk.packet;

public interface PacketOut extends Packet {

    @Override
    default PacketDestination getDestination() {
        return PacketDestination.OUT;
    }
}
