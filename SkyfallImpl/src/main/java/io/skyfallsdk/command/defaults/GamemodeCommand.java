package io.skyfallsdk.command.defaults;

import io.skyfallsdk.command.options.*;
import io.skyfallsdk.command.parameter.argument.Arg;
import io.skyfallsdk.player.Player;
import io.skyfallsdk.world.option.Gamemode;

@Command(name = "gamemode", desc = "Switch your Gamemode.")
@Alias(value = { "gm" })
@Permission(value = "skyfall.gamemode")
public class GamemodeCommand {
    
    @CommandExecutor
    public void onCommandExecute(@Sender Player player, @Arg Gamemode gamemode) {
        // TODO: 13/11/2020  
    }
}
