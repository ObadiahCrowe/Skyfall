package io.skyfallsdk.command.parameter.sender;

import io.skyfallsdk.command.Command;
import io.skyfallsdk.server.CommandSender;

public class SenderCommandSenderParameter implements SenderParameter<CommandSender> {

    @Override
    public boolean supportsCommandSender() {
        return true;
    }

    @Override
    public CommandSender parse(CommandSender sender, Command command, String[] args) throws Exception {
        return sender;
    }
}
