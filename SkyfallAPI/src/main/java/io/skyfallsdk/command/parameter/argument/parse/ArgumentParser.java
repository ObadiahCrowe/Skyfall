package io.skyfallsdk.command.parameter.argument.parse;

import io.skyfallsdk.command.parameter.argument.ArgumentCompleter;
import io.skyfallsdk.command.parameter.argument.ArgumentParseException;
import io.skyfallsdk.command.parameter.argument.CommandArgument;
import io.skyfallsdk.command.util.CommandCompletion;
import io.skyfallsdk.server.CommandSender;

import java.lang.reflect.Type;
import java.util.Collection;

/**
 * Handler for parsing an argument from a string to an object
 *
 * @param <T> The {@link Type type} of the argument
 */
public interface ArgumentParser<T> extends ArgumentCompleter<T> {

    @Override
    default Collection<String> complete(CommandSender sender, CommandArgument<T> argument, String value) {
        return CommandCompletion.provideAllOnlinePlayers();
    }

    @Override
    default Collection<String> complete(CommandSender sender, CommandArgument<T> argument, String... values) {
        return CommandCompletion.provideAllOnlinePlayers();
    }

    default boolean canParse(Class someClass) {
        for (Class type : this.getTypes()) {
            if (!type.isAssignableFrom(someClass)) {
                continue;
            }

            return true;
        }

        return false;
    }

    default Class[] getTypes() {
        return new Class[0];
    }

    T parse(CommandSender sender, Class type, String value) throws ArgumentParseException;

    default T parse(CommandSender sender, Class type, String... values) throws ArgumentParseException {
        throw new UnsupportedOperationException("Argument parser " + this.getClass().getSimpleName() + " doesn't support multiple parameters!");
    }

    default boolean forceAsync() {
        return false;
    }

    default boolean supportsAsync() {
        return true;
    }
}
