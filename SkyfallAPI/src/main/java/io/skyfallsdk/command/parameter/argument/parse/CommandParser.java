package io.skyfallsdk.command.parameter.argument.parse;

import io.skyfallsdk.Server;
import io.skyfallsdk.command.CommandMap;
import io.skyfallsdk.command.Command;
import io.skyfallsdk.command.parameter.argument.ArgumentParseException;
import io.skyfallsdk.command.parameter.argument.CommandArgument;
import io.skyfallsdk.command.CommandSender;

import java.util.Collection;
import java.util.stream.Collectors;

public class CommandParser implements ArgumentParser<Command> {

    @Override
    public Collection<String> complete(CommandSender sender, CommandArgument<Command> argument, String value) {
        CommandMap map = Server.get().getCommandMap();
        return map.getCommands().stream()
                .map(Command::getName)
                .collect(Collectors.toList());
    }

    @Override
    public Class[] getTypes() {
        return new Class[]{Command.class};
    }

    @Override
    public Command parse(CommandSender sender, Class type, String value) throws ArgumentParseException {
        CommandMap map = Server.get().getCommandMap();
        Command command = map.getCommand(value);
        if (command == null) {
            throw new ArgumentParseException("Unknown command \"" + value + "\"!");
        }

        return command;
    }
}
