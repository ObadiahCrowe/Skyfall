package io.skyfallsdk.command.parameter.argument.parse;

import io.skyfallsdk.command.parameter.argument.ArgumentParseException;
import io.skyfallsdk.command.parameter.argument.CommandArgument;
import io.skyfallsdk.server.CommandSender;
import io.skyfallsdk.substance.Substance;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

public class SubstanceParser implements ArgumentParser<Substance> {

    @Override
    public Collection<String> complete(CommandSender sender, CommandArgument<Substance> argument, String value) {
        return Arrays.stream(Substance.values())
          .filter(substance -> {
              if (value == null || value.isEmpty()) {
                  return true;
              }

              return substance.name().toLowerCase().startsWith(value.toLowerCase());
          })
          .map(Substance::getMinecraftId)
          .collect(Collectors.toList());
    }

    @Override
    public Class[] getTypes() {
        return new Class[] {
          Substance.class
        };
    }

    @Override
    public Substance parse(CommandSender sender, Class type, String value) throws ArgumentParseException {
        Substance substance = Arrays.stream(Substance.values())
          .filter(sub -> sub.name().toLowerCase().startsWith(value.toLowerCase()) || sub.getMinecraftId().toLowerCase().startsWith(value.toLowerCase()))
          .findFirst()
          .orElse(null);

        if (substance == null) {
            throw new ArgumentParseException("Could not parse Substance, \"" + value + "\"!");
        }

        return substance;
    }
}
