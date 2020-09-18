package io.skyfallsdk.command;

import io.skyfallsdk.expansion.Expansion;

import java.util.Arrays;
import java.util.Collection;

public interface CommandMap {

    default Command[] registerCommands(Expansion expansion, Class<?>... commandClasses) {
        return Arrays.stream(commandClasses)
          .map(clazz -> this.registerCommand(expansion, clazz))
          .toArray(Command[]::new);
    }

    default Command registerCommand(Expansion expansion, Class<?> commandClass) {
        return this.registerCommand(expansion, Command.fromClass(commandClass));
    }

    Command registerCommand(Expansion expansion, Command command);

    default void removeCommands(Class<?>... commandClasses) {
        for (Class<?> commandClass : commandClasses) {
            this.removeCommand(commandClass);
        }
    }

    void removeCommand(Class<?> commandClass);

    <T> Command getCommand(Class<T> commandClass);

    Command getCommand(String name);

    Collection<Command> getCommands();
}
