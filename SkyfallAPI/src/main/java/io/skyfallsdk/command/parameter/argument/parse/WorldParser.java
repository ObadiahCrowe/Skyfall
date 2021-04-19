package io.skyfallsdk.command.parameter.argument.parse;

import io.skyfallsdk.Server;
import io.skyfallsdk.command.parameter.argument.ArgumentParseException;
import io.skyfallsdk.command.parameter.argument.CommandArgument;
import io.skyfallsdk.player.Player;
import io.skyfallsdk.server.CommandSender;
import io.skyfallsdk.world.World;

import java.util.Collection;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

public class WorldParser implements ArgumentParser<World> {

    @Override
    public Collection<String> complete(CommandSender sender, CommandArgument<World> argument, String value) {
        return Server.get().getWorlds().stream().map(World::getName).collect(Collectors.toList());
    }

    @Override
    public Class[] getTypes() {
        return new Class[]{World.class};
    }

    @Override
    public World parse(CommandSender sender, Class type, String value) throws ArgumentParseException {
        // "functions"
        switch (value) {
            case "self()":
                if (!(sender instanceof Player)) {
                    throw new ArgumentParseException("Please provide a world!");
                }

                return ((Player) sender).getWorld();
        }

        World world = null;

        try {
            world = Server.get().getWorld(value).get().orElse(null);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        if (world == null) {
            throw new ArgumentParseException("Unknown world \"" + value + "\"!");
        }

        return world;
    }
}
