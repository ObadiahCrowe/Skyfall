package io.skyfallsdk.packet;

public enum PacketDestination {

    /**
     * For all packets that are sent to a client, out-bound packets.
     */
    OUT,

    /**
     * For all packets that are received from a client, in-bound packets.
     */
    IN;
}
