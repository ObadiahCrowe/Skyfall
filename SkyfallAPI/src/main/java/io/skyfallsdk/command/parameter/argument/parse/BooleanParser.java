package io.skyfallsdk.command.parameter.argument.parse;

import com.google.common.collect.ImmutableList;
import io.skyfallsdk.command.parameter.argument.ArgumentParseException;
import io.skyfallsdk.command.parameter.argument.CommandArgument;
import io.skyfallsdk.server.CommandSender;

import java.util.Collection;

public class BooleanParser implements ArgumentParser<Boolean> {

    @Override
    public Collection<String> complete(CommandSender sender, CommandArgument<Boolean> argument, String value) {
        return ImmutableList.of("true", "false");
    }

    @Override
    public Class[] getTypes() {
        return new Class[]{boolean.class, Boolean.class};
    }

    @Override
    public Boolean parse(CommandSender sender, Class type, String value) throws ArgumentParseException {
        if (value.equalsIgnoreCase("true") || value.equalsIgnoreCase("on")) {
            return true;
        }

        if (value.equalsIgnoreCase("false") || value.equalsIgnoreCase("off")) {
            return false;
        }

        throw new ArgumentParseException("Invalid boolean argument \"" + value + "\"! Expected true/false.");
    }
}
