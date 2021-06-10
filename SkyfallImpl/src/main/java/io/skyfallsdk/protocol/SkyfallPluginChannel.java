package io.skyfallsdk.protocol;

import com.google.common.collect.Sets;
import io.skyfallsdk.Server;
import io.skyfallsdk.player.Player;
import io.skyfallsdk.protocol.channel.PluginChannel;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class SkyfallPluginChannel implements PluginChannel {

    private final String name;
    private final Set<UUID> recipients;

    public SkyfallPluginChannel(String name) {
        this.name = name;
        this.recipients = Sets.newConcurrentHashSet();
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void addPlayer(Player player) {

    }

    @Override
    public boolean hasPlayer(Player player) {
        return this.recipients.contains(player.getUuid());
    }

    @Override
    public void removePlayer(Player player) {
        if (!this.recipients.contains(player.getUuid())) {
            throw new IllegalStateException("Player, " + player.getUsername() + ", is not registered in this plugin channel.");
        }
    }

    @Override
    public Set<Player> getPlayers() {
        return this.recipients.stream()
          .map(uuid -> Server.get().getPlayer(uuid))
          .collect(Collectors.toUnmodifiableSet());
    }

    @Override
    public void send(byte[] message, Player... players) {

    }

    @Override
    public void close() {

    }

    @Override
    public void closeFor(Player player) {

    }
}
