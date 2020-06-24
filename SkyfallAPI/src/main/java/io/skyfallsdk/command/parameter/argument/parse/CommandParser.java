package io.skyfallsdk.command.parameter.argument.parse;

import io.skyfallsdk.command.parameter.argument.CommandArgument;
import io.skyfallsdk.server.CommandSender;
import net.treasurewars.core.command.CoreCommand;
import net.treasurewars.core.command.CoreCommandAdapter;
import net.treasurewars.core.command.CoreCommandMap;

import java.util.Collection;
import java.util.stream.Collectors;

public class CommandParser implements ArgumentParser<CoreCommand> {

    @Override
    public Collection<String> complete(CommandSender sender, CommandArgument<CoreCommand> argument, String value) {
        CoreCommandMap map = TreasureCore.getInstance().getCommandMap();
        return map.getCommands().stream()
                .filter(command -> command instanceof CoreCommandAdapter)
                .map(Command::getName)
                .collect(Collectors.toList());
    }

    @Override
    public Class[] getTypes() {
        return new Class[]{CoreCommand.class};
    }

    @Override
    public CoreCommand parse(CommandSender sender, Class type, String value) throws ArgumentParseException {
        CoreCommandMap map = TreasureCore.getInstance().getCommandMap();
        CoreCommand command = map.getCoreCommand(value);
        if (command == null) {
            throw new ArgumentParseException("Unknown command \"" + value + "\"!");
        }

        return command;
    }
}
