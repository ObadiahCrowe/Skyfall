package io.skyfallsdk.protocol.channel;

import io.skyfallsdk.Server;
import io.skyfallsdk.player.Player;
import io.skyfallsdk.subscription.Subscribable;

import java.util.Set;

public interface PluginChannel extends Subscribable {

    static PluginChannel getChannel(String name) {
        return Server.get().getChannel(name);
    }

    static PluginChannel getOrCreate(String name) {
        return Server.get().getOrCreateChannel(name);
    }

    String getName();

    void addPlayer(Player player);

    default void addPlayers(Player... players) {
        for (Player player : players) {
            this.addPlayer(player);
        }
    }

    boolean hasPlayer(Player player);

    void removePlayer(Player player);

    default void removePlayers(Player... players) {
        for (Player player : players) {
            this.removePlayer(player);
        }
    }

    Set<Player> getPlayers();

    default void send(byte[] message) {
        for (Player player : this.getPlayers()) {
            this.send(message);
        }
    }

    void send(byte[] message, Player... players);

    void close();

    void closeFor(Player player);

    default void closeFor(Player... players) {
        for (Player player : players) {
            this.closeFor(player);
        }
    }
}
