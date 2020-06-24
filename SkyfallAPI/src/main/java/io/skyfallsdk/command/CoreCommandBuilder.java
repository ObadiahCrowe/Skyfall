package net.treasurewars.core.command;

import org.apache.commons.lang3.Validate;

public class CoreCommandBuilder {

    private Class<?> commandClass;
    private Object commandInstance;
    private String name;
    private String description;
    private String[] aliases;
    private CoreCommand[] subCommands;

    public CoreCommandBuilder(Object commandInstance) {
        if (commandInstance == null) {
            throw new IllegalArgumentException("Command class can't be null!");
        }

        this.commandInstance = commandInstance;
        this.commandClass = commandInstance.getClass();
        this.aliases = new String[0];
        this.subCommands = new CoreCommand[0];
    }

    public CoreCommand build() {
        Validate.notNull(this.name);
        Validate.notNull(this.description);

        return new CoreCommand(this.commandClass, this.commandInstance, this.name, this.description, this.aliases, this.subCommands);
    }

    public CoreCommandBuilder setName(String name) {
        Validate.notNull(name);

        this.name = name;
        return this;
    }

    public CoreCommandBuilder setDescription(String description) {
        Validate.notNull(description);

        this.description = description;
        return this;
    }

    public CoreCommandBuilder setAliases(String... aliases) {
        Validate.notNull(aliases);

        this.aliases = aliases;
        return this;
    }

    public CoreCommandBuilder setSubCommands(CoreCommand... subCommands) {
        Validate.notNull(subCommands);

        this.subCommands = subCommands;
        return this;
    }
}
