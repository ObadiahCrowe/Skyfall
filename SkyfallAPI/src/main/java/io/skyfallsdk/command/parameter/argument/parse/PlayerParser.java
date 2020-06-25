package io.skyfallsdk.command.parameter.argument.parse;

import io.skyfallsdk.Server;
import io.skyfallsdk.command.parameter.argument.ArgumentParseException;
import io.skyfallsdk.player.Player;
import io.skyfallsdk.server.CommandSender;

public class PlayerParser implements ArgumentParser<Player> {

    @Override
    public Class[] getTypes() {
        return new Class[]{Player.class};
    }

    @Override
    public Player parse(CommandSender sender, Class type, String value) throws ArgumentParseException {
        // Preset "functions"
        switch (value.toLowerCase()) {
            case "self()":
                if (!(sender instanceof Player)) {
                    throw new ArgumentParseException("Please provide a target!");
                }

                return (Player) sender;
        }

        Player player = Server.get().getPlayer(value);
        if (player == null) {
            throw new ArgumentParseException("Couldn't find player \"" + value + "\"!");
        }

        return player;
    }
}
