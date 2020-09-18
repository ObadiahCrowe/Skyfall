package io.skyfallsdk;

import io.skyfallsdk.command.CommandMap;
import io.skyfallsdk.concurrent.Scheduler;
import io.skyfallsdk.expansion.Expansion;
import io.skyfallsdk.expansion.ExpansionInfo;
import io.skyfallsdk.permission.PermissionHolder;
import io.skyfallsdk.player.Player;
import io.skyfallsdk.protocol.ProtocolVersion;
import io.skyfallsdk.server.ServerCommandSender;
import io.skyfallsdk.world.World;

import java.nio.file.Path;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Logger;

public interface Server extends ServerCommandSender, PermissionHolder {

    static Server get() {
        return Impl.IMPL.get();
    }

    void shutdown();

    Path getPath();

    Logger getLogger();

    Scheduler getScheduler();

    CommandMap getCommandMap();

    Player getPlayer(String username);

    Player getPlayer(UUID uuid);

    List<Player> getPlayers();

    List<World> getWorlds();

    World getWorld(String name);

    String getMotd();

    void setMotd(String motd);

    int getMaxPlayers();

    void setMaxPlayers(int maxPlayers);

    <T extends Expansion> T getExpansion(Class<T> expansionClass);

    default ExpansionInfo getExpansionInfo(Expansion expansion) {
        return this.getExpansionInfo(expansion.getClass());
    }

    ExpansionInfo getExpansionInfo(Class<? extends Expansion> expansionClass);

    List<ProtocolVersion> getSupportedVersions();

    ProtocolVersion getBaseVersion();

    static class Impl {
        static AtomicReference<Server> IMPL = new AtomicReference<>(null);
    }
}
