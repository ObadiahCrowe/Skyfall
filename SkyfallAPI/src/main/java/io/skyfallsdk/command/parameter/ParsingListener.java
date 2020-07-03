package io.skyfallsdk.command.parameter;

public interface ParsingListener<T> {

    void onSuccess(CommandParameter parameter, T value);

    void onFailure(CommandParameter parameter, String message);
}
