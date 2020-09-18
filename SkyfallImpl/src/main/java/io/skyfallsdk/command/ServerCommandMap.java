package io.skyfallsdk.command;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.skyfallsdk.Server;
import io.skyfallsdk.expansion.Expansion;
import io.skyfallsdk.util.Validator;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.Map.Entry;
import java.util.regex.Pattern;

public class ServerCommandMap implements CommandMap {

    private static final Pattern PATTERN_ON_SPACE = Pattern.compile("\\s+");

    private final Map<String, Command> knownCommands;
    private final Logger logger;

    public ServerCommandMap() {
        this.knownCommands = Maps.newConcurrentMap();
        this.logger = Server.get().getLogger();
    }

    @Override
    public Command registerCommand(Expansion expansion, Class<?> commandClass) {
        return this.registerCommand(expansion, Command.fromClass(commandClass));
    }

    public Command registerCommand(Class<?> commandClass) {
        return this.registerCommand(Command.fromClass(commandClass));
    }

    @Override
    public Command registerCommand(Expansion expansion, Command command) {
        Validator.notNull(expansion, "Expansion can't be null!");
        Validator.notNull(command, "Command can't be null!");

        this.register(Server.get().getExpansionInfo(expansion).name(), command);

        return command;
    }

    public Command registerCommand(Command command) {
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

    public <T> Command getCommand(Class<T> commandClass) {
        for (Command command : this.getCommands()) {
            if (!(command instanceof CoreCommandAdapter)) {
                continue;
            }

            CoreCommandAdapter adapter = (CoreCommandAdapter) command;
            Command coreCommand = adapter.getCommand();
            if (coreCommand.getCommandClass() != commandClass) {
                continue;
            }

            return coreCommand;
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
        if (this.removedCommands != null && this.removedCommands.contains(fallbackPrefix + ":" + command.getName())) {
            return true;
        }

        if (command instanceof CoreCommandAdapter) {
            this.knownCommands.remove(command.getName());
            command.getAliases().forEach(this.knownCommands::remove);
        }

        return super.register(fallbackPrefix, command);
    }

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
