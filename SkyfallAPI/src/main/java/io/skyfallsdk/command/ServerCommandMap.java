package io.skyfallsdk.command;

import com.google.common.collect.Lists;
import io.skyfallsdk.Server;
import io.skyfallsdk.expansion.Expansion;
import io.skyfallsdk.util.Validator;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.regex.Pattern;

public class ServerCommandMap {

    private static final Pattern PATTERN_ON_SPACE = Pattern.compile("\\s+");

    private final Logger logger;

    private ServerCommandMap() {
        this.logger = Server.get().getLogger();
    }

    public CoreCommand registerCommand(Expansion expansion, Class<?> commandClass) {
        return this.registerCommand(expansion, CoreCommand.fromClass(commandClass));
    }

    public CoreCommand registerCommand(Class<?> commandClass) {
        return this.registerCommand(CoreCommand.fromClass(commandClass));
    }

    public CoreCommand registerCommand(Expansion expansion, CoreCommand command) {
        Validator.notNull(expansion, "Expansion can't be null!");
        Validator.notNull(command, "Command can't be null!");

        this.register(Server.get().getExpansionInfo(expansion).name(), new CoreCommandAdapter(command));

        return command;
    }

    public CoreCommand registerCommand(CoreCommand command) {
        Validator.notNull(command, "Command can't be null!");

        this.register("skyfall", new CoreCommandAdapter(command));

        return command;
    }

    public void removeCommands(Class<?>... commandClasses) {
        for (Class<?> commandClass : commandClasses) {
            this.removeCommand(commandClass);
        }
    }

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

    public void blockCommands(List<String> commands) {
        commands.forEach(this::blockCommand);
    }

    public void blockCommand(String removedCommand) {
        if (!removedCommand.contains(":")) {
            this.removedCommands.add(removedCommand);
            Command command = this.knownCommands.remove(removedCommand);
            List<String> toRemove = Lists.newArrayList();
            for (Entry<String, Command> entry : this.knownCommands.entrySet()) {
                if (!entry.getValue().equals(command)) {
                    continue;
                }

                toRemove.add(entry.getKey());
            }

            toRemove.forEach(this.knownCommands::remove);
        } else {
            String baseCommand = removedCommand.split(":")[1];
            Command command = this.knownCommands.remove(removedCommand);
            this.knownCommands.remove(baseCommand);

            this.removedCommands.add(removedCommand);
            this.removedCommands.add(baseCommand);

            List<String> toRemove = Lists.newArrayList();
            for (Entry<String, Command> entry : this.knownCommands.entrySet()) {
                if (!entry.getValue().equals(command)) {
                    continue;
                }

                toRemove.add(entry.getKey());
            }

            toRemove.forEach(this.knownCommands::remove);
        }

        this.debug("Removed command \"" + removedCommand + "\".");
    }

    public <T> CoreCommand getCommand(Class<T> tClass) {
        for (Command command : this.getCommands()) {
            if (!(command instanceof CoreCommandAdapter)) {
                continue;
            }

            CoreCommandAdapter adapter = (CoreCommandAdapter) command;
            CoreCommand coreCommand = adapter.getCommand();
            if (coreCommand.getCommandClass() != tClass) {
                continue;
            }

            return coreCommand;
        }

        return null;
    }

    public <T> CoreCommand getCoreCommand(String name) {
        Command command = this.getCommand(name);
        return command instanceof CoreCommandAdapter ? ((CoreCommandAdapter) command).getCommand() : null;
    }

    @Override
    public boolean register(String fallbackPrefix, Command command) {
        if (this.removedCommands != null && this.removedCommands.contains(fallbackPrefix + ":" + command.getName())) {
            return true;
        }

        if (command instanceof CoreCommandAdapter) {
            this.knownCommands.remove(command.getName());
            command.getAliases().forEach(this.knownCommands::remove);
        }

        return super.register(fallbackPrefix, command);
    }

    @Override
    public boolean dispatch(CommandSender sender, String commandLine) throws CommandException {
        String[] args = PATTERN_ON_SPACE.split(commandLine);
        if (args.length == 0) {
            sender.sendMessage(MessageTemplate.COMMAND_ERROR_NON_EXISTANT);
            return true;
        }

        String sentCommandLabel = args[0].toLowerCase();
        Command target = this.getCommand(sentCommandLabel);
        if (target == null) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(ChatColour.RED + "Command doesn't exist!");
                return true;
            }

            sender.sendMessage(MessageTemplate.COMMAND_ERROR_NON_EXISTANT);
            // Return true anyway, so that another message is not sent
            return true;
        }

        if (!target.testPermissionSilent(sender)) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(ChatColour.RED + "No permission!");
                return true;
            }

            sender.sendMessage(MessageTemplate.COMMAND_ERROR_NO_PERM);
            // Return true anyway, so that another message is not sent
            return true;
        }

        try {
            target.timings.startTiming();

            String[] arguments = Arrays.copyOfRange(args, 1, args.length);
            target.execute(sender, sentCommandLabel, arguments);
        } catch (Throwable throwable) {
            new CommandException("Unhandled exception executing \'" + commandLine + "\' in " + target, throwable).printStackTrace();
        } finally {
            target.timings.stopTiming();
        }

        return true;
    }

    public List<String> tabComplete(CommandSender sender, String cmdLine) {
        Validate.notNull(sender, "Sender cannot be null");
        Validate.notNull(cmdLine, "Command line cannot null");

        int spaceIndex = cmdLine.indexOf(32);
        String argLine;

        boolean op = !(sender instanceof Player) || sender.isOp();

        if (spaceIndex == -1) {
            List<String> commandNames = Lists.newArrayList();
            argLine = sender instanceof Player ? "/" : "";

            for (Entry<String, Command> entry : this.knownCommands.entrySet()) {
                Command command = entry.getValue();
                if (!command.testPermissionSilent(sender) || !op && !(command instanceof CoreCommandAdapter)) {
                    continue;
                }

                String name = entry.getKey();
                if (!StringUtil.startsWithIgnoreCase(name, cmdLine)) {
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
        } else if (!target.testPermissionSilent(sender)) {
            return null;
        }

        argLine = cmdLine.substring(spaceIndex + 1);
        String[] args = PATTERN_ON_SPACE.split(argLine, -1);

        try {
            return target.tabComplete(sender, commandName, args);
        } catch (Throwable otherExc) {
            new CommandException("Unhandled exception executing tab-completer for \'" + cmdLine + "\' in " + target, otherExc).printStackTrace();
            return Lists.newArrayList();
        }
    }

    private void debug(String msg) {
        this.logger.info(msg);
    }
}
