package net.treasurewars.core.command.parameter.argument.parse;

import net.treasurewars.core.command.parameter.argument.ArgumentParseException;
import net.treasurewars.core.command.parameter.argument.CommandArgument;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.stream.Collectors;

public class WorldParser implements ArgumentParser<World> {

    @Override
    public Collection<String> complete(CommandSender sender, CommandArgument<World> argument, String value) {
        return Bukkit.getWorlds().stream().map(World::getName).collect(Collectors.toList());
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

        World world = Bukkit.getWorld(value);
        if (world == null) {
            throw new ArgumentParseException("Unknown world \"" + value + "\"!");
        }

        return world;
    }
}
