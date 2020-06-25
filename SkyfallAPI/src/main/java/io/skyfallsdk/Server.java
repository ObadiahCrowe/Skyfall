package io.skyfallsdk;

import io.skyfallsdk.command.CoreCommandMap;
import io.skyfallsdk.concurrent.Scheduler;
import io.skyfallsdk.player.Player;
import io.skyfallsdk.server.CommandSender;
import io.skyfallsdk.world.World;

import java.nio.file.Path;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Logger;

public interface Server extends CommandSender {

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

    List<World> getWorlds();

    World getWorld(String name);

    static class Impl {
        static AtomicReference<Server> IMPL = new AtomicReference<>(null);
    }
}
