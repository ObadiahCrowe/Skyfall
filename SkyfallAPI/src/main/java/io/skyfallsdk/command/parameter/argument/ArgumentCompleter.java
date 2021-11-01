package io.skyfallsdk.command.parameter.argument;

import io.skyfallsdk.command.CommandSender;

import java.util.Collection;

public interface ArgumentCompleter<T> {

    Collection<String> complete(CommandSender sender, CommandArgument<T> argument, String value);

    Collection<String> complete(CommandSender sender, CommandArgument<T> argument, String... values);
}
