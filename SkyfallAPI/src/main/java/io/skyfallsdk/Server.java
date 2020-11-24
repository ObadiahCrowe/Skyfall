package io.skyfallsdk;

import io.skyfallsdk.command.CommandMap;
import io.skyfallsdk.concurrent.Scheduler;
import io.skyfallsdk.concurrent.tick.TickRegistry;
import io.skyfallsdk.concurrent.tick.TickSpec;
import io.skyfallsdk.expansion.Expansion;
import io.skyfallsdk.expansion.ExpansionInfo;
import io.skyfallsdk.expansion.ExpansionRegistry;
import io.skyfallsdk.packet.PacketRegistry;
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
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

public interface Server extends ServerCommandSender, PermissionHolder {

    static Server get() {
        return Impl.IMPL.get();
    }

    void shutdown();

    Path getPath();

    Logger getLogger();

    Logger getLogger(Expansion expansion);

    Scheduler getScheduler();

    CommandMap getCommandMap();

    PacketRegistry getPacketRegistry();

    Player getPlayer(String username);

    Player getPlayer(UUID uuid);

    List<Player> getPlayers();

    Collection<World> getWorlds();

    World getWorld(String name);

    String getMotd();

    void setMotd(String motd);

    int getMaxPlayers();

    void setMaxPlayers(int maxPlayers);

    ExpansionRegistry getExpansionRegistry();

    <T extends Expansion> T getExpansion(Class<T> expansionClass);

    default ExpansionInfo getExpansionInfo(Expansion expansion) {
        return this.getExpansionInfo(expansion.getClass());
    }

    ExpansionInfo getExpansionInfo(Class<? extends Expansion> expansionClass);

    List<ProtocolVersion> getSupportedVersions();

    ProtocolVersion getBaseVersion();

    WorldLoader getWorldLoader();

    <T extends TickSpec<T>> TickRegistry<T> getTickRegistry(T spec);

    static class Impl {
        static AtomicReference<Server> IMPL = new AtomicReference<>(null);
    }
}
