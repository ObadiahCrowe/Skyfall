package io.skyfallsdk.command.parameter.argument.parse;

import io.skyfallsdk.command.parameter.argument.ArgumentParseException;
import io.skyfallsdk.command.parameter.argument.CommandArgument;
import io.skyfallsdk.command.util.CommandCompletion;
import io.skyfallsdk.server.CommandSender;

import java.util.Collection;

public class DoubleParser implements ArgumentParser<Double> {

    @Override
    public Collection<String> complete(CommandSender sender, CommandArgument<Double> argument, String value) {
        return CommandCompletion.createRange(0.0, 10.0, 20);
    }

    @Override
    public Class[] getTypes() {
        return new Class[]{double.class, Double.class};
    }

    @Override
    public Double parse(CommandSender sender, Class type, String value) throws ArgumentParseException {
        try {
            return Double.valueOf(value);
        } catch (NumberFormatException e) {
            throw new ArgumentParseException("Invalid decimal number \"" + value + "\"!");
        }
    }
}
