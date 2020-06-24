package net.treasurewars.core.command.parameter.argument.parse;

import org.bukkit.command.CommandSender;

public class StringParser implements ArgumentParser<String> {

    @Override
    public Class[] getTypes() {
        return new Class[]{String.class};
    }

    @Override
    public String parse(CommandSender sender, Class type, String value) {
        return value;
    }

    @Override
    public String parse(CommandSender sender, Class type, String... values) {
        return String.join(" ", values);
    }
}
