package io.skyfallsdk.command.defaults.expansion;

import io.skyfallsdk.command.options.*;
import io.skyfallsdk.server.CommandSender;

@Command(name = "list", desc = "Lists all currently loaded Expansions.")
@Alias(value = { "ls", "l" })
@Permission(value = "skyfall.expansion.list")
public class ExpansionListCommand {

    @CommandExecutor
    public void onCommandExecute(@Sender CommandSender sender) {
        //
    }
}
