package net.treasurewars.core.command.parameter.argument.parse;

import net.treasurewars.core.command.parameter.argument.ArgumentParseException;
import org.bukkit.command.CommandSender;

public class StringArrayParser implements ArgumentParser<String[]> {

    public static final String EMPTY_ARRAY = "§EMPTY";

    @Override
    public Class[] getTypes() {
        return new Class[]{String[].class};
    }

    @Override
    public String[] parse(CommandSender sender, Class type, String value) throws ArgumentParseException {
        return value.equals(EMPTY_ARRAY) ? new String[0] : new String[]{value};
    }

    @Override
    public String[] parse(CommandSender sender, Class type, String... values) throws ArgumentParseException {
        return ((values.length > 0) && values[0].equals(EMPTY_ARRAY)) ? new String[0] : values;
    }
}
