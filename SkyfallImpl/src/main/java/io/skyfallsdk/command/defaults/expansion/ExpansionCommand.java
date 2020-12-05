package io.skyfallsdk.command.defaults.expansion;

import io.skyfallsdk.Server;
import io.skyfallsdk.command.options.*;
import io.skyfallsdk.command.parameter.service.Service;
import io.skyfallsdk.expansion.ExpansionRegistry;
import io.skyfallsdk.server.CommandSender;

@Command(name = "expansion", desc = "Main expansion command.")
@Alias(value = { "expansions", "plugins", "pl", "ex" })
@Permission(value = "skyfall.expansion")
public class ExpansionCommand {

    @CommandExecutor
    public void onCommandExecute(@Sender CommandSender sender, @Service Server server) {
        sender.executeCommand("expansion help");
    }
}
