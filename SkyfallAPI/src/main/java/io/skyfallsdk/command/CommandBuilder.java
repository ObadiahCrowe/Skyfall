package io.skyfallsdk.command;

import io.skyfallsdk.util.Validator;

public class CommandBuilder {

    private Class<?> commandClass;
    private Object commandInstance;
    private String name;
    private String description;
    private String[] aliases;
    private CoreCommand[] subCommands;

    public CommandBuilder(Object commandInstance) {
        if (commandInstance == null) {
            throw new IllegalArgumentException("Command class can't be null!");
        }

        this.commandInstance = commandInstance;
        this.commandClass = commandInstance.getClass();
        this.aliases = new String[0];
        this.subCommands = new CoreCommand[0];
    }

    public CoreCommand build() {
        Validator.notNull(this.name);
        Validator.notNull(this.description);

        return new CoreCommand(this.commandClass, this.commandInstance, this.name, this.description, this.aliases, this.subCommands);
    }

    public CommandBuilder setName(String name) {
        Validator.notNull(name);

        this.name = name;
        return this;
    }

    public CommandBuilder setDescription(String description) {
        Validator.notNull(description);

        this.description = description;
        return this;
    }

    public CommandBuilder setAliases(String... aliases) {
        Validator.notNull(aliases);

        this.aliases = aliases;
        return this;
    }

    public CommandBuilder setSubCommands(CoreCommand... subCommands) {
        Validator.notNull(subCommands);

        this.subCommands = subCommands;
        return this;
    }
}
