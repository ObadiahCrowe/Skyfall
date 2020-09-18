package io.skyfallsdk.command.defaults;

import io.skyfallsdk.command.options.Alias;
import io.skyfallsdk.command.options.Command;
import io.skyfallsdk.command.options.CommandExecutor;
import io.skyfallsdk.command.options.Sender;
import io.skyfallsdk.server.CommandSender;

@Command(name = "version", desc = "Displays the current software version.")
@Alias(value = { "ver" })
public class VersionCommand {

    @CommandExecutor
    public void onCommandExecute(@Sender CommandSender sender) {

    }
}
