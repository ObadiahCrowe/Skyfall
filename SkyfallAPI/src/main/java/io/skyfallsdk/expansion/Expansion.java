package io.skyfallsdk.expansion;

import io.skyfallsdk.Server;
import io.skyfallsdk.command.Command;
import io.skyfallsdk.command.CommandMap;
import io.skyfallsdk.command.parameter.argument.ArgumentFactory;
import io.skyfallsdk.command.parameter.argument.parse.ArgumentParser;
import org.apache.logging.log4j.Logger;

import java.nio.file.Path;
import java.util.Arrays;

public interface Expansion {

    void onStartup();

    void onShutdown();

    default ExpansionInfo getInfo() {
        return Server.get().getExpansionInfo(this);
    }

    default Server getServer() {
        return Server.get();
    }

    default Path getPath() {
        return Server.get().getPath().resolve("expansions").resolve(this.getInfo().name());
    }

    default Logger getLogger() {
        return Server.get().getLogger(this);
    }

    default void registerArgumentParsers(ArgumentParser... parsers) {
        ArgumentFactory factory = ArgumentFactory.getInstance();
        for (ArgumentParser parser : parsers) {
            factory.registerParser(parser);
        }
    }

    default void registerCommand(Command command) {
        Server.get().getCommandMap().registerCommand(this, command);
    }

    default void registerCommand(Class<?> commandClass) {
        Server.get().getCommandMap().registerCommand(this, commandClass);
    }

    default void registerCommands(Class<?>... commandClasses) {
        for (Class<?> clazz : commandClasses) {
            this.registerCommand(clazz);
        }
    }

    default void registerCommands(Class<?>[]... commandClasses) {
        for (Class<?>[] classes : commandClasses) {
            if (classes.length > 1) {
                this.registerCommand(classes[0], Arrays.copyOfRange(classes, 1, classes.length));
            } else if (classes.length == 1) {
                this.registerCommand(classes[0]);
            }
        }
    }

    default void registerCommand(Class<?> superCommand, Command command) {
        CommandMap commandMap = Server.get().getCommandMap();
        Command superCommandInstance = commandMap.getCommand(superCommand);
        if (superCommandInstance == null) {
            superCommandInstance = commandMap.registerCommand(this, superCommand);
        }

        superCommandInstance.addSubcommand(command);
    }

    default void registerCommand(Class<?> superCommand, Class<?> commandClass) {
        CommandMap commandMap = Server.get().getCommandMap();
        Command superCommandInstance = commandMap.getCommand(superCommand);
        if (superCommandInstance == null) {
            superCommandInstance = commandMap.registerCommand(this, superCommand);
        }

        superCommandInstance.addSubcommand(commandClass);
    }

    default void registerCommand(Class<?> superCommand, Class<?>... subCommands) {
        CommandMap commandMap = Server.get().getCommandMap();
        Command superCommandInstance = commandMap.getCommand(superCommand);
        if (superCommandInstance == null) {
            superCommandInstance = commandMap.registerCommand(this, superCommand);
        }

        for (Class<?> subCommand : subCommands) {
            superCommandInstance.addSubcommand(subCommand);
        }
    }

    default Command getCommand(Class<?> commandClass, Class<?>... subCommands) {
        CommandMap commandMap = Server.get().getCommandMap();
        Command superCommand = commandMap.getCommand(commandClass);
        if (superCommand == null) {
            return null;
        }

        for (Class<?> subCommand : subCommands) {
            superCommand = superCommand.getSubCommand(subCommand);
            if (superCommand == null) {
                return null;
            }
        }

        return superCommand;
    }
}
