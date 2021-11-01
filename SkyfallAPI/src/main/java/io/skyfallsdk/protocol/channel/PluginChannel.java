package io.skyfallsdk.protocol.channel;

import io.skyfallsdk.Server;
import io.skyfallsdk.expansion.Expansion;
import io.skyfallsdk.player.Player;
import io.skyfallsdk.subscription.Subscribable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.Set;

public interface PluginChannel extends Subscribable {

    static @NotNull Optional<@Nullable PluginChannel> getChannel(@NotNull String channelId) {
        return Server.get().getChannel(channelId);
    }

    static @NotNull PluginChannel getOrCreate(@NotNull Expansion expansion, @NotNull String channelId) {
        return Server.get().getOrCreateChannel(expansion, channelId);
    }

    @NotNull
    String getName();

    void addPlayer(@NotNull Player player);

    default void addPlayers(@NotNull Player @NotNull... players) {
        for (Player player : players) {
            this.addPlayer(player);
        }
    }

    boolean hasPlayer(@NotNull Player player);

    void removePlayer(@NotNull Player player);

    default void removePlayers(@NotNull Player @NotNull... players) {
        for (Player player : players) {
            this.removePlayer(player);
        }
    }

    @NotNull
    Set<@NotNull Player> getPlayers();

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
