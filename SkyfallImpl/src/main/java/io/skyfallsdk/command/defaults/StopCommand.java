package io.skyfallsdk.command.defaults;

import io.skyfallsdk.Server;
import io.skyfallsdk.chat.colour.ChatColour;
import io.skyfallsdk.command.options.*;
import io.skyfallsdk.command.parameter.service.Service;
import io.skyfallsdk.server.CommandSender;

@Command(name = "stop", desc = "Shuts down the server.")
@Alias(value = { "shutdown", "exit" })
@Permission(value = "skyfall.stop")
public class StopCommand {

    @CommandExecutor
    public void onCommandExecute(@Sender CommandSender sender, @Service Server server) {
        sender.sendMessage(ChatColour.GREEN + "Shutting down");
        server.shutdown();
    }
}
