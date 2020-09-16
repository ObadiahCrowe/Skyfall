package io.skyfallsdk;

import io.skyfallsdk.command.CoreCommandMap;
import io.skyfallsdk.concurrent.Scheduler;
import io.skyfallsdk.expansion.Expansion;
import io.skyfallsdk.permission.PermissionHolder;
import io.skyfallsdk.player.Player;
import io.skyfallsdk.protocol.ProtocolVersion;
import io.skyfallsdk.server.CommandSender;
import io.skyfallsdk.world.World;
import org.apache.logging.log4j.Logger;

import java.nio.file.Path;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

public interface Server extends CommandSender, PermissionHolder {

    static Server get() {
        return Impl.IMPL.get();
    }

    void shutdown();

    Path getPath();

    Logger getLogger();

    Scheduler getScheduler();

    CoreCommandMap getCommandMap();

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

    List<ProtocolVersion> getSupportedVersions();

    ProtocolVersion getBaseVersion();

    static class Impl {
        static AtomicReference<Server> IMPL = new AtomicReference<>(null);
    }
}
