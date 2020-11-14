package io.skyfallsdk.extension;

import io.skyfallsdk.event.player.PlayerJoinEvent;
import io.skyfallsdk.expansion.Expansion;
import io.skyfallsdk.expansion.ExpansionInfo;
import io.skyfallsdk.packet.handshake.HandshakeIn;
import io.skyfallsdk.subscription.EventSubscription;
import io.skyfallsdk.subscription.PacketSubscription;
import io.skyfallsdk.subscription.SubscriptionInfo;
import io.skyfallsdk.subscription.SubscriptionPriority;

@ExpansionInfo(name = "test", version = "${project.version}", authors = { "Obadiah Crowe" })
public class TestExpansion implements Expansion {

    @Override
    public void onStartup() {

    }

    @Override
    public void onShutdown() {

    }

    @SubscriptionInfo(priority = SubscriptionPriority.FIRST)
    public void onPacketReceived(PacketSubscription<HandshakeIn> subscription) {
        HandshakeIn packet = subscription.getPacket();

        System.out.println(packet.getProtocolVersion());
    }

    @SubscriptionInfo(priority = SubscriptionPriority.MIDDLE)
    public void onEventReceived(EventSubscription<PlayerJoinEvent> subscription) {
        PlayerJoinEvent event = subscription.getEvent();

        event.setJoinMessage("test");
    }
}
