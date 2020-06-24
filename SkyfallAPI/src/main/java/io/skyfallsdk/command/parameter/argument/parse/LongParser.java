package net.treasurewars.core.command.parameter.argument.parse;

import net.treasurewars.core.command.parameter.argument.ArgumentParseException;
import net.treasurewars.core.command.parameter.argument.CommandArgument;
import net.treasurewars.core.command.util.CommandCompletion;
import org.bukkit.command.CommandSender;

import java.util.Collection;

public class LongParser implements ArgumentParser<Long> {

    @Override
    public Collection<String> complete(CommandSender sender, CommandArgument<Long> argument, String value) {
        return CommandCompletion.createRange(1, 10);
    }

    @Override
    public Class[] getTypes() {
        return new Class[]{long.class, Long.class};
    }

    @Override
    public Long parse(CommandSender sender, Class type, String value) throws ArgumentParseException {
        // Preset "functions"
        try {
            return Long.valueOf(value);
        } catch (NumberFormatException e) {
            throw new ArgumentParseException("Invalid number \"" + value + "\"!");
        }
    }
}
