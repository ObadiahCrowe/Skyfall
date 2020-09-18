package io.skyfallsdk.command.parameter.sender;

import io.skyfallsdk.command.CoreCommand;
import io.skyfallsdk.server.CommandSender;

public class SenderCommandSenderParameter implements SenderParameter<CommandSender> {

    @Override
    public boolean supportsCommandSender() {
        return true;
    }

    @Override
    public CommandSender parse(CommandSender sender, CoreCommand command, String[] args) throws Exception {
        return sender;
    }
}
