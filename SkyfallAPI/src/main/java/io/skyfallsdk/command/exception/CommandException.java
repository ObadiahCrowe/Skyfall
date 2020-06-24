package io.skyfallsdk.command.exception;

public class CommandException extends RuntimeException {

    private final boolean showTooltip;

    public CommandException() {
        this.showTooltip = false;
    }

    public CommandException(boolean tooltip) {
        this("An unexpected error occurred!", tooltip);
    }

    public CommandException(Throwable cause) {
        this("An unexpected error occurred", cause, false);
    }

    public CommandException(String message) {
        this(message, false);
    }

    public CommandException(String message, boolean tooltip) {
        super(message);
        this.showTooltip = tooltip;
    }

    public CommandException(String message, Throwable cause, boolean tooltip) {
        super(message, cause);
        this.showTooltip = tooltip;
    }

    public CommandException(Throwable cause, boolean tooltip) {
        super("An unexpected error occurred: " + (cause.getMessage() != null ? ": " + cause.getMessage() : ""), cause);
        this.showTooltip = tooltip;
    }

    public CommandException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, boolean showTooltip) {
        super(message + (cause.getMessage() != null ? ": " + cause.getMessage() : ""), cause, enableSuppression, writableStackTrace);
        this.showTooltip = showTooltip;
    }

    public boolean showTooltip() {
        return this.showTooltip;
    }
}
