package io.skyfallsdk.command.defaults.expansion;

import io.skyfallsdk.Server;
import io.skyfallsdk.command.options.*;
import io.skyfallsdk.command.parameter.service.Service;
import io.skyfallsdk.command.CommandSender;

@Command(name = "list", desc = "Lists all currently loaded Expansions.")
@Alias(value = { "ls", "l" })
@Permission(value = "skyfall.expansion.list")
public class ExpansionListCommand {

    @CommandExecutor
    public void onCommandExecute(@Sender CommandSender sender, @Service Server server) {
        server.getExpansionRegistry().getExpansions().forEach(expansion -> {
            sender.sendMessage(expansion.getClass().getCanonicalName());
            sender.sendMessage(server.getExpansionInfo(expansion).name());
        });
    }
}
