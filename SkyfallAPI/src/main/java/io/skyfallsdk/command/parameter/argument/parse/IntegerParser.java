package net.treasurewars.core.command.parameter.argument.parse;

import net.treasurewars.core.command.parameter.argument.ArgumentParseException;
import net.treasurewars.core.command.parameter.argument.CommandArgument;
import net.treasurewars.core.command.util.CommandCompletion;
import org.bukkit.command.CommandSender;

import java.util.Calendar;
import java.util.Collection;

public class IntegerParser implements ArgumentParser<Integer> {

    @Override
    public Collection<String> complete(CommandSender sender, CommandArgument<Integer> argument, String value) {
        return CommandCompletion.createRange(1, 10);
    }

    @Override
    public Class[] getTypes() {
        return new Class[]{int.class, Integer.class};
    }

    @Override
    public Integer parse(CommandSender sender, Class type, String value) throws ArgumentParseException {
        // Preset "functions"
        switch (value.toLowerCase()) {
            case "month()":
                return Calendar.getInstance().get(Calendar.MONTH) + 1;
            case "year()":
                return Calendar.getInstance().get(Calendar.YEAR) + 1;
        }

        try {
            return Integer.valueOf(value);
        } catch (NumberFormatException e) {
            throw new ArgumentParseException("Invalid number \"" + value + "\"!");
        }
    }
}
