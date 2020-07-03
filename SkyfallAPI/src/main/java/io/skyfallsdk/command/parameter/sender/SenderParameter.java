package io.skyfallsdk.command.parameter.sender;

import io.skyfallsdk.command.parameter.CommandParameter;

public interface SenderParameter<T> extends CommandParameter<T> {

    boolean supportsCommandSender();
}
