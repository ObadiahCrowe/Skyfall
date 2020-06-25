package io.skyfallsdk.command;

import com.google.common.collect.Lists;
import net.treasurewars.core.command.management.ToggleCommand;
import net.treasurewars.core.message.MessageTemplate;
import net.treasurewars.core.modules.chat.ChatColour;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

public class CoreCommandMap extends SimpleCommandMap {

    public static CoreCommandMap injectCommandMap(Logger logger) {
        logger.info("Attempting to inject custom command map...");
        Class<? extends Server> serverClass = Bukkit.getServer().getClass();

        try {
            Field field = serverClass.getDeclaredField("commandMap");
            if (!field.getType().getPackage().equals(SimpleCommandMap.class.getPackage())) {
                return null;
            }

            field.setAccessible(true);
            SimpleCommandMap previousMap = (SimpleCommandMap) field.get(Bukkit.getServer());
            CoreCommandMap map = new CoreCommandMap(previousMap, logger);
            field.set(Bukkit.getServer(), map);

            logger.info("Successfully injected custom command map to CraftServer!");

            return map;
        } catch (NoSuchFieldException | IllegalAccessException e) {
            logger.log(Level.SEVERE, "Failed to inject custom command map to CraftServer!", e);
            return null;
        }
    }

    private static final Pattern PATTERN_ON_SPACE = Pattern.compile("\\s+");
    private static final List<String> DEFAULT_REMOVED_COMMANDS = Lists.newArrayList("minecraft:say", "minecraft:tell",
            "minecraft:me", "worldedit:/calc", "bukkit:about", "bukkit:pl", "bukkit:?", "bukkit:help", "bukkit:reload",
            "bukkit:rl", "bukkit:timings", "minecraft:achievement", "minecraft:ban", "minecraft:ban-ip", "minecraft:banlist", "minecraft:clear",
            "minecraft:blockdata", "minecraft:clear", "minecraft:debug", "minecraft:defaultgamemode", "minecraft:difficulty",
            "minecraft:effect", "minecraft:enchant", "minecraft:entitydata", "minecraft:execute", "minecraft:fill", "minecraft:gamemode",
            "minecraft:give", "minecraft:help", "minecraft:kick", "minecraft:kill", "minecraft:list",
            "minecraft:pardon", "minecraft:pardon-ip", "minecraft:particle", "minecraft:playsound", "minecraft:replaceitem", "minecraft:save-all",
            "minecraft:save-off", "minecraft:save-on", "minecraft:scoreboard", "minecraft:seed", "minecraft:setblock", "minecraft:setidletimeout",
            "minecraft:setworldspawn", "minecraft:spawnpoint", "minecraft:spreadplayers", "minecraft:stats", "minecraft:stop", "minecraft:summon",
            "minecraft:testfor", "minecraft:testforblock", "minecraft:testforblocks", "minecraft:title", "minecraft:toggledownfall", "minecraft:tp",
            "minecraft:trigger", "minecraft:weather", "minecraft:worldborder", "minecraft:xp", "minecraft:clone", "spigot:restart",
            "spigot:tps", "minecraft:time", "protocollib:filter", "protocollib:packet", "protocollib:packet_filter", "protocollib:packetlog", "worldedit:calc"
    );

    private final Logger logger;
    private final List<String> removedCommands;

    private CoreCommandMap(SimpleCommandMap previousMap, Logger logger) {
        super(Bukkit.getServer());

        this.logger = logger;
        this.removedCommands = Lists.newArrayList();

        // Initial set up
        this.logger.info("Cloning fields from previous command map into this instance...");
        for (Field field : previousMap.getClass().getDeclaredFields()) {
            this.logger.info("Cloning field \"" + field.getName() + "\"...");

            try {
                Field modifiers = Field.class.getDeclaredField("modifiers");
                modifiers.setAccessible(true);

                Field oldField = previousMap.getClass().getDeclaredField(field.getName());
                oldField.setAccessible(true);

                modifiers.setInt(oldField, oldField.getModifiers() & ~Modifier.FINAL);
                Object oldObject = oldField.get(previousMap);

                Field newField = this.getClass().getSuperclass().getDeclaredField(field.getName());
                newField.setAccessible(true);

                modifiers.setInt(newField, newField.getModifiers() & ~Modifier.FINAL);
                newField.set(this, oldObject);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                this.logger.log(Level.SEVERE, "An exception occurred when cloning fields from previous command map!", e);
                return;
            }
        }

        this.logger.info("Successfully cloned fields from previous instance.");
        this.blockCommands(DEFAULT_REMOVED_COMMANDS);

        this.registerCommand(ToggleCommand.class);
    }

    public CoreCommand registerCommand(Class<?> commandClass) {
        return this.registerCommand(CoreCommand.fromClass(commandClass));
    }

    public CoreCommand registerCommand(CoreCommand command) {
        Validate.notNull(command, "Command can't be null!");
        this.register("treasurewars", new CoreCommandAdapter(command));
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

    @Override
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
