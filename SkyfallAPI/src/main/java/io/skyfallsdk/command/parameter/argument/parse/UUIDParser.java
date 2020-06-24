package net.treasurewars.core.command.parameter.argument.parse;

import net.treasurewars.core.command.parameter.argument.ArgumentParseException;
import net.treasurewars.core.command.parameter.argument.CommandArgument;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;

import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

public class UUIDParser implements ArgumentParser<UUID> {

    @Override
    public Collection<String> complete(CommandSender sender, CommandArgument<UUID> argument, String value) {
        return Bukkit.getOnlinePlayers().stream().map(Entity::getUniqueId).map(UUID::toString).collect(Collectors.toList());
    }

    @Override
    public Class[] getTypes() {
        return new Class[]{UUID.class};
    }

    @Override
    public UUID parse(CommandSender sender, Class type, String value) throws ArgumentParseException {
        try {
            return UUID.fromString(value);
        } catch (Exception e) {
            throw new ArgumentParseException("Could not parse UUID, \"" + value + "\"!");
        }
    }
}
