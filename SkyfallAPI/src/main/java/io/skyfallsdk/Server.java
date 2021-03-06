package io.skyfallsdk;

import io.skyfallsdk.command.CommandMap;
import io.skyfallsdk.concurrent.Scheduler;
import io.skyfallsdk.concurrent.tick.TickRegistry;
import io.skyfallsdk.concurrent.tick.TickSpec;
import io.skyfallsdk.expansion.Expansion;
import io.skyfallsdk.expansion.ExpansionInfo;
import io.skyfallsdk.expansion.ExpansionRegistry;
import io.skyfallsdk.protocol.channel.PluginChannel;
import io.skyfallsdk.server.ServerState;
import io.skyfallsdk.util.http.MojangAPI;
import io.skyfallsdk.permission.PermissionHolder;
import io.skyfallsdk.player.Player;
import io.skyfallsdk.protocol.ProtocolVersion;
import io.skyfallsdk.server.ServerCommandSender;
import io.skyfallsdk.world.World;
import io.skyfallsdk.world.WorldLoader;
import org.apache.logging.log4j.Logger;

import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;

public interface Server extends ServerCommandSender, PermissionHolder {

    static Server get() {
        return Impl.IMPL.get();
    }

    void shutdown();

    ServerState getState();

    Path getPath();

    Logger getLogger();

    Logger getLogger(Class<? extends Expansion> expansion);

    default Logger getLogger(Expansion expansion) {
        return this.getLogger(expansion.getClass());
    }

    Scheduler getScheduler();

    CommandMap getCommandMap();

    Player getPlayer(String username);

    Player getPlayer(UUID uuid);

    List<Player> getPlayers();

    Collection<World> getWorlds();

    CompletableFuture<Optional<World>> getWorld(String name);

    String getMotd();

    void setMotd(String motd);

    Path getServerIcon();

    boolean isOnlineMode();

    void setOnlineMode(boolean onlineMode);

    int getMaxPlayers();

    void setMaxPlayers(int maxPlayers);

    ExpansionRegistry getExpansionRegistry();

    <T extends Expansion> T getExpansion(Class<T> expansionClass);

    default ExpansionInfo getExpansionInfo(Expansion expansion) {
        return this.getExpansionInfo(expansion.getClass());
    }

    ExpansionInfo getExpansionInfo(Class<? extends Expansion> expansionClass);

    MojangAPI getMojangApi();

    List<ProtocolVersion> getSupportedVersions();

    ProtocolVersion getBaseVersion();

    WorldLoader getWorldLoader();

    <T extends TickSpec<T>> TickRegistry<T> getTickRegistry(T spec);

    PluginChannel getChannel(String name);

    PluginChannel getOrCreateChannel(String name);


    static class Impl {
        static AtomicReference<Server> IMPL = new AtomicReference<>(null);
    }
}
