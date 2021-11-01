package io.skyfallsdk.command.parameter.argument.parse;

import io.skyfallsdk.command.parameter.argument.ArgumentParseException;
import io.skyfallsdk.command.parameter.argument.CommandArgument;
import io.skyfallsdk.command.CommandSender;
import io.skyfallsdk.util.UtilMath;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

public class EnumParser implements ArgumentParser<Enum> {

    @Override
    public Collection<String> complete(CommandSender sender, CommandArgument<Enum> argument, String value) {
        Enum[] constants = argument.getType().getEnumConstants();
        return Arrays.stream(constants).map(Enum::name).collect(Collectors.toList());
    }

    @Override
    public boolean canParse(Class someClass) {
        return someClass.isEnum();
    }

    @Override
    public Enum parse(CommandSender sender, Class type, String value) throws ArgumentParseException {
        Object[] constants = type.getEnumConstants();
        if (UtilMath.isNumber(value)) {
            int index = Integer.parseInt(value);
            if (index < 0 || index > (constants.length - 1)) {
                throw new ArgumentParseException("Index \"" + value + "\" is out of bounds!");
            }

            return (Enum) constants[index];
        }

        StringBuilder builder = new StringBuilder();
        for (Object constant : constants) {
            String name = ((Enum) constant).name();
            builder.append(", ").append(name);
            if (name.equalsIgnoreCase(value)) {
                return (Enum) constant;
            }
        }

        throw new ArgumentParseException("Unknown value \"" + value + "\"! Legal values are: " + builder.substring(2));
    }
}
