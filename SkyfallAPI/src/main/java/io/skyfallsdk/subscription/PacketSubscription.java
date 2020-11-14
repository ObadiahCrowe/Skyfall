package io.skyfallsdk.subscription;

import io.skyfallsdk.packet.Packet;
import io.skyfallsdk.player.Player;

public interface PacketSubscription<T extends Packet> {

    Player getCorrespondent();

    T getPacket();
}
