package net.treasurewars.core.command.parameter.argument.parse;

import net.treasurewars.core.command.parameter.argument.ArgumentParseException;
import net.treasurewars.core.command.parameter.argument.CommandArgument;
import net.treasurewars.core.command.util.Duration;
import org.bukkit.command.CommandSender;

import java.util.Collection;
import java.util.Collections;

public class DurationParser implements ArgumentParser<Duration> {

    @Override
    public Collection<String> complete(CommandSender sender, CommandArgument<Duration> argument, String value) {
        return Collections.emptyList();
    }

    @Override
    public Collection<String> complete(CommandSender sender, CommandArgument<Duration> argument, String... values) {
        return Collections.emptyList();
    }

    @Override
    public Class[] getTypes() {
        return new Class[]{Duration.class};
    }

    @Override
    public Duration parse(CommandSender sender, Class type, String value) throws ArgumentParseException {
        try {
            return new Duration(value);
        } catch (IllegalArgumentException e) {
            throw new ArgumentParseException("Invalid length format \"" + value + "\"! Use format 0w0d0h0m0s");
        }
    }
}
