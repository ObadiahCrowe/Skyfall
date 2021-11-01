package io.skyfallsdk.command.defaults.world;

import io.skyfallsdk.Server;
import io.skyfallsdk.command.options.Command;
import io.skyfallsdk.command.options.CommandExecutor;
import io.skyfallsdk.command.options.Permission;
import io.skyfallsdk.command.options.Sender;
import io.skyfallsdk.command.parameter.service.Service;
import io.skyfallsdk.command.CommandSender;

@Command(name = "world", desc = "Main command for managing world state.")
@Permission(value = "skyfall.world")
public class WorldCommand {

    @CommandExecutor
    public void onCommandExecute(@Sender CommandSender sender, @Service Server server) {
        server.getWorldLoader().getLoadedWorlds().forEach(world -> {
            server.sendMessage("world: " + world.getName() + " - " + world.getUuid().toString() + " border: " + world.getBorder().toString());
        });
    }
}
