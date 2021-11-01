package io.skyfallsdk.command.defaults.expansion;

import io.skyfallsdk.Server;
import io.skyfallsdk.command.options.*;
import io.skyfallsdk.command.parameter.argument.Arg;
import io.skyfallsdk.command.parameter.service.Service;
import io.skyfallsdk.expansion.Expansion;
import io.skyfallsdk.command.CommandSender;

@Command(name = "unload", desc = "Unloads an Expansion from the server.")
@Alias(value = { "disable" })
@Permission(value = "skyfall.expansion.unload")
public class ExpansionUnloadCommand {

    @CommandExecutor
    public void onCommandExecute(@Sender CommandSender sender, @Arg Expansion expansion, @Service Server server) {

    }
}
