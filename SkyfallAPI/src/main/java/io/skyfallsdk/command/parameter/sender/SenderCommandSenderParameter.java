package net.treasurewars.core.command.parameter.sender;

import net.treasurewars.core.command.CoreCommand;
import org.bukkit.command.CommandSender;

public class SenderCommandSenderParameter implements SenderParameter<CommandSender> {

    @Override
    public boolean supportsCommandSender() {
        return true;
    }

    @Override
    public boolean forceAsync() {
        return false;
    }

    @Override
    public boolean supportsAsync() {
        return true;
    }

    @Override
    public CommandSender parse(CommandSender sender, CoreCommand command, String[] args) throws Exception {
        return sender;
    }
}
