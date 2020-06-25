package io.skyfallsdk.command.parameter.argument.parse;

import io.skyfallsdk.command.parameter.argument.ArgumentParseException;
import io.skyfallsdk.command.parameter.argument.CommandArgument;
import io.skyfallsdk.command.util.CommandCompletion;
import io.skyfallsdk.server.CommandSender;

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
