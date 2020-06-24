package io.skyfallsdk.command.parameter.argument;

import io.skyfallsdk.command.exception.CommandException;

/**
 * An exception that can occur when parsing an argument
 */
public class ArgumentParseException extends CommandException {

    public ArgumentParseException(String message) {
        super(message, true);
    }

    public ArgumentParseException(String message, Throwable cause) {
        super(message, cause, true);
    }

    public ArgumentParseException(Throwable cause) {
        super(cause, true);
    }

    public ArgumentParseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace, true);
    }
}
