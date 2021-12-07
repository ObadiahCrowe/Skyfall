package io.skyfallsdk.extension;

import io.skyfallsdk.event.player.PlayerJoinEvent;
import io.skyfallsdk.expansion.Expansion;
import io.skyfallsdk.expansion.ExpansionInfo;
import io.skyfallsdk.subscription.*;

@ExpansionInfo(name = "test", version = "${project.version}", authors = { "Obadiah Crowe" })
public class TestExpansion implements Expansion {

    @Override
    public void onStartup() {

    }

    @Override
    public void onShutdown() {

    }

/*    @SubscriptionInfo(priority = SubscriptionPriority.FIRST)
    public void onPacketReceived(Subscription<HandshakeIn> subscription) {
        HandshakeIn packet = subscription.getSubscribable();

        System.out.println(packet.getProtocolVersion());
    }*/

    @Subscriber
    public void onEventReceived(Subscription<PlayerJoinEvent> subscription) {
        PlayerJoinEvent event = subscription.getSubscribable();

        event.setJoinMessage("test");
    }
}
