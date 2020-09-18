//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package io.skyfallsdk.command;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;
import org.apache.commons.lang.Validate;
import org.bukkit.Server;
import org.bukkit.command.defaults.HelpCommand;
import org.bukkit.command.defaults.PluginsCommand;
import org.bukkit.command.defaults.ReloadCommand;
import org.bukkit.command.defaults.TimingsCommand;
import org.bukkit.command.defaults.VanillaCommand;
import org.bukkit.command.defaults.VersionCommand;
import org.bukkit.entity.Player;
import org.bukkit.util.Java15Compat;
import org.bukkit.util.StringUtil;

public class SimpleCommandMap implements CommandMap {
    private static final Pattern PATTERN_ON_SPACE = Pattern.compile(" ", 16);
    protected final Map<String, Command> knownCommands = new HashMap();
    private final Server server;

    public SimpleCommandMap(Server server) {
        this.server = server;
        this.setDefaultCommands();
    }

    private void setDefaultCommands() {
        this.register("bukkit", new VersionCommand("version"));
        this.register("bukkit", new ReloadCommand("reload"));
        this.register("bukkit", new PluginsCommand("plugins"));
        this.register("bukkit", new TimingsCommand("timings"));
    }

    public void setFallbackCommands() {
        this.register("bukkit", new HelpCommand());
    }

    public void registerAll(String fallbackPrefix, List<Command> commands) {
        if (commands != null) {
            Iterator var4 = commands.iterator();

            while(var4.hasNext()) {
                Command c = (Command)var4.next();
                this.register(fallbackPrefix, c);
            }
        }

    }

    public boolean register(String fallbackPrefix, Command command) {
        return this.register(command.getName(), fallbackPrefix, command);
    }

    public boolean register(String label, String fallbackPrefix, Command command) {
        label = label.toLowerCase().trim();
        fallbackPrefix = fallbackPrefix.toLowerCase().trim();
        boolean registered = this.register(label, command, false, fallbackPrefix);
        Iterator iterator = command.getAliases().iterator();

        while(iterator.hasNext()) {
            if (!this.register((String)iterator.next(), command, true, fallbackPrefix)) {
                iterator.remove();
            }
        }

        if (!registered) {
            command.setLabel(fallbackPrefix + ":" + label);
        }

        command.register(this);
        return registered;
    }

    private synchronized boolean register(String label, Command command, boolean isAlias, String fallbackPrefix) {
        this.knownCommands.put(fallbackPrefix + ":" + label, command);
        if ((command instanceof VanillaCommand || isAlias) && this.knownCommands.containsKey(label)) {
            return false;
        } else {
            boolean registered = true;
            Command conflict = (Command)this.knownCommands.get(label);
            if (conflict != null && conflict.getLabel().equals(label)) {
                return false;
            } else {
                if (!isAlias) {
                    command.setLabel(label);
                }

                this.knownCommands.put(label, command);
                return registered;
            }
        }
    }

    public boolean dispatch(CommandSender sender, String commandLine) throws CommandException {
        String[] args = PATTERN_ON_SPACE.split(commandLine);
        if (args.length == 0) {
            return false;
        } else {
            String sentCommandLabel = args[0].toLowerCase();
            Command target = this.getCommand(sentCommandLabel);
            if (target == null) {
                return false;
            } else {
                try {
                    target.execute(sender, sentCommandLabel, (String[])Java15Compat.Arrays_copyOfRange(args, 1, args.length));
                    return true;
                } catch (CommandException var7) {
                    throw var7;
                } catch (Throwable var8) {
                    throw new CommandException("Unhandled exception executing '" + commandLine + "' in " + target, var8);
                }
            }
        }
    }

    public synchronized void clearCommands() {
        Iterator var2 = this.knownCommands.entrySet().iterator();

        while(var2.hasNext()) {
            Entry<String, Command> entry = (Entry)var2.next();
            ((Command)entry.getValue()).unregister(this);
        }

        this.knownCommands.clear();
        this.setDefaultCommands();
    }

    public Command getCommand(String name) {
        Command target = (Command)this.knownCommands.get(name.toLowerCase());
        return target;
    }

    public List<String> tabComplete(CommandSender sender, String cmdLine) {
        Validate.notNull(sender, "Sender cannot be null");
        Validate.notNull(cmdLine, "Command line cannot null");
        int spaceIndex = cmdLine.indexOf(32);
        String prefix;
        if (spaceIndex == -1) {
            ArrayList<String> completions = new ArrayList();
            Map<String, Command> knownCommands = this.knownCommands;
            prefix = sender instanceof Player ? "/" : "";
            Iterator var8 = knownCommands.entrySet().iterator();

            while(var8.hasNext()) {
                Entry<String, Command> commandEntry = (Entry)var8.next();
                Command command = (Command)commandEntry.getValue();
                if (command.testPermissionSilent(sender)) {
                    String name = (String)commandEntry.getKey();
                    if (StringUtil.startsWithIgnoreCase(name, cmdLine)) {
                        completions.add(prefix + name);
                    }
                }
            }

            Collections.sort(completions, String.CASE_INSENSITIVE_ORDER);
            return completions;
        } else {
            String commandName = cmdLine.substring(0, spaceIndex);
            Command target = this.getCommand(commandName);
            if (target == null) {
                return null;
            } else if (!target.testPermissionSilent(sender)) {
                return null;
            } else {
                prefix = cmdLine.substring(spaceIndex + 1, cmdLine.length());
                String[] args = PATTERN_ON_SPACE.split(prefix, -1);

                try {
                    return target.tabComplete(sender, commandName, args);
                } catch (CommandException var11) {
                    throw var11;
                } catch (Throwable var12) {
                    throw new CommandException("Unhandled exception executing tab-completer for '" + cmdLine + "' in " + target, var12);
                }
            }
        }
    }

    public Collection<Command> getCommands() {
        return Collections.unmodifiableCollection(this.knownCommands.values());
    }

    public void registerServerAliases() {
        Map<String, String[]> values = this.server.getCommandAliases();
        Iterator var3 = values.keySet().iterator();

        while(true) {
            while(var3.hasNext()) {
                String alias = (String)var3.next();
                if (!alias.contains(":") && !alias.contains(" ")) {
                    String[] commandStrings = (String[])values.get(alias);
                    List<String> targets = new ArrayList();
                    StringBuilder bad = new StringBuilder();
                    String[] var10 = commandStrings;
                    int var9 = commandStrings.length;

                    for(int var8 = 0; var8 < var9; ++var8) {
                        String commandString = var10[var8];
                        String[] commandArgs = commandString.split(" ");
                        Command command = this.getCommand(commandArgs[0]);
                        if (command == null) {
                            if (bad.length() > 0) {
                                bad.append(", ");
                            }

                            bad.append(commandString);
                        } else {
                            targets.add(commandString);
                        }
                    }

                    if (bad.length() > 0) {
                        this.server.getLogger().warning("Could not register alias " + alias + " because it contains commands that do not exist: " + bad);
                    } else if (targets.size() > 0) {
                        this.knownCommands.put(alias.toLowerCase(), new FormattedCommandAlias(alias.toLowerCase(), (String[])targets.toArray(new String[targets.size()])));
                    } else {
                        this.knownCommands.remove(alias.toLowerCase());
                    }
                } else {
                    this.server.getLogger().warning("Could not register alias " + alias + " because it contains illegal characters");
                }
            }

            return;
        }
    }
}
