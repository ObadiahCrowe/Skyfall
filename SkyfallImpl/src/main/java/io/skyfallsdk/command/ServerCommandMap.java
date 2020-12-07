package io.skyfallsdk.command;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.skyfallsdk.Server;
import io.skyfallsdk.chat.colour.ChatColour;
import io.skyfallsdk.command.defaults.GamemodeCommand;
import io.skyfallsdk.command.defaults.expansion.ExpansionCommand;
import io.skyfallsdk.command.defaults.HelpCommand;
import io.skyfallsdk.command.defaults.StopCommand;
import io.skyfallsdk.command.defaults.VersionCommand;
import io.skyfallsdk.command.defaults.expansion.ExpansionListCommand;
import io.skyfallsdk.command.defaults.expansion.ExpansionLoadCommand;
import io.skyfallsdk.command.defaults.expansion.ExpansionUnloadCommand;
import io.skyfallsdk.command.exception.CommandException;
import io.skyfallsdk.expansion.Expansion;
import io.skyfallsdk.player.Player;
import io.skyfallsdk.server.CommandSender;
import io.skyfallsdk.util.Validator;

import java.util.*;
import java.util.Map.Entry;
import java.util.regex.Pattern;

public class ServerCommandMap implements CommandMap {

    private static final Pattern PATTERN_ON_SPACE = Pattern.compile("\\s+");

    private final Map<String, Command> knownCommands;

    public ServerCommandMap() {
        this.knownCommands = Maps.newConcurrentMap();

        this.registerDefaultCommands();
    }

    private void registerDefaultCommands() {
        this.registerCommand(ExpansionCommand.class).addSubcommands(
          ExpansionListCommand.class,
          ExpansionLoadCommand.class,
          ExpansionUnloadCommand.class
        );
        this.registerCommand(GamemodeCommand.class);
        this.registerCommand(HelpCommand.class);
        this.registerCommand(StopCommand.class);
        this.registerCommand(VersionCommand.class);
    }

    @Override
    public Command registerCommand(Expansion expansion, Class<?> commandClass) {
        return this.registerCommand(expansion, Command.fromClass(commandClass));
    }

    private Command registerCommand(Class<?> commandClass) {
        return this.registerCommand(Command.fromClass(commandClass));
    }

    @Override
    public Command registerCommand(Expansion expansion, Command command) {
        Validator.notNull(expansion, "Expansion can't be null!");
        Validator.notNull(command, "Command can't be null!");

        this.register(Server.get().getExpansionInfo(expansion).name(), command);

        return command;
    }


    private Command registerCommand(Command command) {
        Validator.notNull(command, "Command can't be null!");

        this.register("skyfall", command);

        return command;
    }

    @Override
    public void removeCommand(Class<?> commandClass) {
        Iterator<Command> commandIterator = this.knownCommands.values().iterator();

        while (commandIterator.hasNext()) {
            Command command = commandIterator.next();
            if (command.getClass() != commandClass) {
                continue;
            }

            commandIterator.remove();
        }
    }

    @Override
    public <T> Command getCommand(Class<T> commandClass) {
        for (Command command : this.getCommands()) {
            if (command.getCommandClass() != commandClass) {
                continue;
            }

            return command;
        }

        return null;
    }

    @Override
    public Command getCommand(String name) {
        return this.knownCommands.getOrDefault(name.toLowerCase(), null);
    }

    @Override
    public Collection<Command> getCommands() {
        return this.knownCommands.values();
    }

    public boolean register(String fallbackPrefix, Command command) {
        this.knownCommands.remove(command.getName());
        for (String alias : command.getAliases()) {
            this.knownCommands.remove(alias);
        }

        fallbackPrefix = fallbackPrefix.toLowerCase();
        this.knownCommands.put(fallbackPrefix + ":" + command.getName().toLowerCase(), command);
        this.knownCommands.putIfAbsent(command.getName().toLowerCase(), command);

        for (String alias : command.getAliases()) {
            this.knownCommands.put(fallbackPrefix + ":" + alias.toLowerCase(), command);
            this.knownCommands.putIfAbsent(alias.toLowerCase(), command);
        }

        return true;
    }

    public boolean dispatch(CommandSender sender, String commandLine) throws CommandException {
        String[] args = PATTERN_ON_SPACE.split(commandLine);
        if (args.length == 0) {
            sender.sendMessage(ChatColour.RED + "This command doesn't exist!");
            return true;
        }

        String sentCommandLabel = args[0].toLowerCase();
        Command target = this.getCommand(sentCommandLabel);
        if (target == null) {
            sender.sendMessage(ChatColour.RED + "This command doesn't exist!");
            return true;
        }

        if (!target.hasAccess(sender)) {
            sender.sendMessage(ChatColour.RED + "You do not have permission to execute this command.");
            return true;
        }

        try {
            String[] arguments = Arrays.copyOfRange(args, 1, args.length);

            target.callExecute(sender, arguments);
        } catch (Throwable throwable) {
            new CommandException("Unhandled exception executing \'" + commandLine + "\' in " + target, throwable, false).printStackTrace();
        }

        return true;
    }

    public List<String> tabComplete(CommandSender sender, String cmdLine) {
        Validator.notNull(sender, "Sender cannot be null");
        Validator.notNull(cmdLine, "Command line cannot null");

        int spaceIndex = cmdLine.indexOf(32);
        String argLine;

        if (spaceIndex == -1) {
            List<String> commandNames = Lists.newArrayList();
            argLine = sender instanceof Player ? "/" : "";

            for (Entry<String, Command> entry : this.knownCommands.entrySet()) {
                Command command = entry.getValue();
                if (!command.hasAccess(sender)) {
                    continue;
                }

                String name = entry.getKey();
                if (!name.toLowerCase().startsWith(cmdLine.toLowerCase())) {
                    continue;
                }

                commandNames.add(argLine + name);
            }

            commandNames.sort(String.CASE_INSENSITIVE_ORDER);
            return commandNames;
        }

        String commandName = cmdLine.substring(0, spaceIndex);
        Command target = this.getCommand(commandName);
        if (target == null) {
            return null;
        } else if (!target.hasAccess(sender)) {
            return null;
        }

        argLine = cmdLine.substring(spaceIndex + 1);
        String[] args = PATTERN_ON_SPACE.split(argLine, -1);

        try {
            return target.callTabComplete(sender, args);
        } catch (Throwable otherExc) {
            new CommandException("Unhandled exception executing tab-completer for \'" + cmdLine + "\' in " + target, otherExc, false).printStackTrace();
            return Lists.newArrayList();
        }
    }
}
