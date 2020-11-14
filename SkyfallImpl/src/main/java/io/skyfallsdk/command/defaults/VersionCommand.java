package io.skyfallsdk.command.defaults;

import io.skyfallsdk.chat.colour.ChatColour;
import io.skyfallsdk.chat.ChatComponent;
import io.skyfallsdk.command.options.Alias;
import io.skyfallsdk.command.options.Command;
import io.skyfallsdk.command.options.CommandExecutor;
import io.skyfallsdk.command.options.Sender;
import io.skyfallsdk.server.CommandSender;
import io.skyfallsdk.util.UtilGitVersion;

@Command(name = "version", desc = "Displays the current software version.")
@Alias(value = { "ver" })
public class VersionCommand {

    @CommandExecutor
    public void onCommandExecute(@Sender CommandSender sender) {
        UtilGitVersion.GitInfo info = UtilGitVersion.getFromSkyfall();
        if (info == null) {
            sender.sendMessage(new ChatComponent("Could not obtain Skyfall git info.").setColour(ChatColour.RED));
            return;
        }

        sender.sendMessage(new ChatComponent("Skyfall version: " + UtilGitVersion.getFromSkyfall().toString()).setColour(ChatColour.GREEN));
    }
}
