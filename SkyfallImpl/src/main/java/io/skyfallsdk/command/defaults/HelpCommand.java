package io.skyfallsdk.command.defaults;

import io.skyfallsdk.Server;
import io.skyfallsdk.command.options.Alias;
import io.skyfallsdk.command.options.Command;
import io.skyfallsdk.command.options.CommandExecutor;
import io.skyfallsdk.command.options.Sender;
import io.skyfallsdk.command.parameter.service.Service;
import io.skyfallsdk.command.CommandSender;

@Command(name = "help", desc = "Displays help for all commands on the server.")
@Alias(value = { "?" })
public class HelpCommand {

    @CommandExecutor
    public void onCommandExecute(@Sender CommandSender sender, @Service Server server) {
        //
    }
}
