package io.skyfallsdk.command.parameter.argument.parse;

import io.skyfallsdk.Server;
import io.skyfallsdk.command.parameter.argument.ArgumentParseException;
import io.skyfallsdk.command.parameter.argument.CommandArgument;
import io.skyfallsdk.expansion.Expansion;
import io.skyfallsdk.command.CommandSender;

import java.util.Collection;
import java.util.stream.Collectors;

public class ExpansionParser implements ArgumentParser<Expansion> {

    @Override
    public Collection<String> complete(CommandSender sender, CommandArgument<Expansion> argument, String value) {
        return Server.get().getExpansionRegistry().getExpansions().stream()
          .map(expansion -> Server.get().getExpansionRegistry().getExpansionInfo(expansion).name())
          .filter(name -> name.startsWith(value))
          .collect(Collectors.toList());
    }

    @Override
    public Class[] getTypes() {
        return new Class[] {
          Expansion.class
        };
    }

    @Override
    public Expansion parse(CommandSender sender, Class type, String value) throws ArgumentParseException {
        Expansion expansion = Server.get().getExpansionRegistry().getExpansions().stream()
          .filter(ex -> Server.get().getExpansionRegistry().getExpansionInfo(ex).name().equalsIgnoreCase(value))
          .findFirst()
          .orElse(null);

        if (expansion == null) {
            throw new ArgumentParseException("Could not find an Expansion by the name of, \"" + value + "\"!");
        }

        return expansion;
    }
}
