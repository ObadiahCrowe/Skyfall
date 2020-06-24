package net.treasurewars.core.command.parameter.argument.parse;

import net.treasurewars.core.command.parameter.argument.ArgumentParseException;
import net.treasurewars.core.command.parameter.argument.CommandArgument;
import net.treasurewars.core.command.util.CommandCompletion;
import org.bukkit.command.CommandSender;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Collection;

public class IPAddressParser implements ArgumentParser<InetAddress> {

    @Override
    public Collection<String> complete(CommandSender sender, CommandArgument<InetAddress> argument, String value) {
        return CommandCompletion.provideNone();
    }

    @Override
    public Collection<String> complete(CommandSender sender, CommandArgument<InetAddress> argument, String... values) {
        return CommandCompletion.provideNone();
    }

    @Override
    public Class[] getTypes() {
        return new Class[]{InetAddress.class};
    }

    @Override
    public InetAddress parse(CommandSender sender, Class type, String value) throws ArgumentParseException {
        try {
            return InetAddress.getByName(value);
        } catch (UnknownHostException e) {
            throw new ArgumentParseException("Invalid IP address \"" + value + "\"!");
        }
    }
}
