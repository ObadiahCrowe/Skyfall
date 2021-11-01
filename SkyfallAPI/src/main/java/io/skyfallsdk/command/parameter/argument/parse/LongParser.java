package io.skyfallsdk.command.parameter.argument.parse;

import io.skyfallsdk.command.parameter.argument.ArgumentParseException;
import io.skyfallsdk.command.parameter.argument.CommandArgument;
import io.skyfallsdk.command.util.CommandCompletion;
import io.skyfallsdk.command.CommandSender;

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
