package net.treasurewars.core.command.parameter.sender;

import net.treasurewars.core.command.parameter.CommandParameter;

public interface SenderParameter<T> extends CommandParameter<T> {

    boolean supportsCommandSender();
}
