package io.skyfallsdk.command.parameter.argument.parse;

import io.skyfallsdk.Server;
import io.skyfallsdk.command.CoreCommand;
import io.skyfallsdk.command.ServerCommandMap;
import io.skyfallsdk.command.options.Command;
import io.skyfallsdk.command.parameter.argument.ArgumentParseException;
import io.skyfallsdk.command.parameter.argument.CommandArgument;
import io.skyfallsdk.server.CommandSender;

import java.util.Collection;
import java.util.stream.Collectors;

public class CommandParser implements ArgumentParser<CoreCommand> {

    @Override
    public Collection<String> complete(CommandSender sender, CommandArgument<CoreCommand> argument, String value) {
        ServerCommandMap map = Server.get().getCommandMap();
        return map.getCommands().stream()
                .map(Command::name)
                .collect(Collectors.toList());
    }

    @Override
    public Class[] getTypes() {
        return new Class[]{CoreCommand.class};
    }

    @Override
    public CoreCommand parse(CommandSender sender, Class type, String value) throws ArgumentParseException {
        ServerCommandMap map = Server.get().getCommandMap();
        CoreCommand command = map.getCoreCommand(value);
        if (command == null) {
            throw new ArgumentParseException("Unknown command \"" + value + "\"!");
        }

        return command;
    }
}
