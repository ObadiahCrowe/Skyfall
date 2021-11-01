package io.skyfallsdk.command.parameter.argument.parse;

import io.skyfallsdk.command.parameter.argument.ArgumentParseException;
import io.skyfallsdk.command.parameter.argument.CommandArgument;
import io.skyfallsdk.player.Player;
import io.skyfallsdk.command.CommandSender;
import io.skyfallsdk.world.option.Gamemode;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

public class GamemodeParser implements ArgumentParser<Gamemode> {

    @Override
    public Collection<String> complete(CommandSender sender, CommandArgument<Gamemode> argument, String value) {
        if (!(sender instanceof Player)) {
            return Collections.emptyList();
        }

        return Arrays.stream(Gamemode.values())
          .map(gamemode -> gamemode.name().toLowerCase())
          .collect(Collectors.toList());
    }

    @Override
    public Class[] getTypes() {
        return new Class[] {
          Gamemode.class
        };
    }

    @Override
    public Gamemode parse(CommandSender sender, Class type, String value) throws ArgumentParseException {
        Gamemode gamemode = Arrays.stream(Gamemode.values())
          .filter(mode -> mode.name().toLowerCase().startsWith(value) || String.valueOf(mode.getId()).equals(value))
          .findFirst()
          .orElse(null);

        if (gamemode == null) {
            throw new ArgumentParseException("Could not parse Gamemode, \"" + value + "\"!");
        }

        return gamemode;
    }
}
