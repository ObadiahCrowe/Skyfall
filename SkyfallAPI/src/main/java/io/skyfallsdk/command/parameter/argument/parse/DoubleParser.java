package net.treasurewars.core.command.parameter.argument.parse;

import net.treasurewars.core.command.parameter.argument.ArgumentParseException;
import net.treasurewars.core.command.parameter.argument.CommandArgument;
import net.treasurewars.core.command.util.CommandCompletion;
import org.bukkit.command.CommandSender;

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
