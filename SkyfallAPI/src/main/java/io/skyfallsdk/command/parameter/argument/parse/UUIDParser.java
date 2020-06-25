package io.skyfallsdk.command.parameter.argument.parse;

import io.skyfallsdk.command.parameter.argument.ArgumentParseException;
import io.skyfallsdk.command.parameter.argument.CommandArgument;
import io.skyfallsdk.server.CommandSender;

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
