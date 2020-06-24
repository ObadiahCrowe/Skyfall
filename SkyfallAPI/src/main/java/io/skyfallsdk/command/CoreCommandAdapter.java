package net.treasurewars.core.command;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import net.treasurewars.core.command.event.PlayerCallCommandEvent;
import net.treasurewars.core.command.event.PlayerTabCompleteEvent;
import net.treasurewars.core.command.exception.CommandException;
import net.treasurewars.core.message.MessageTemplate;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.stream.Collectors;

public class CoreCommandAdapter extends Command {

    private final CoreCommand command;

    public CoreCommandAdapter(CoreCommand command) {
        super(command.getName(), command.getDescription(), command.getMergedSignature().toString(), Lists.newArrayList(command.getAliases()));
        this.command = command;
    }

    public CoreCommand getCommand() {
        return command;
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if (sender instanceof Player) {
            PlayerCallCommandEvent event = new PlayerCallCommandEvent((Player) sender, this.command, args);

            Bukkit.getPluginManager().callEvent(event);
            if (event.isCancelled()) {
                return false;
            }
        }

        try {
            this.command.callExecute(sender, args);
        } catch (Throwable t) {
            if (sender instanceof Player) {
                sender.sendMessage(MessageTemplate.UNEXPECTED_ERROR);
            }

            t.printStackTrace();
        }

        return true;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
        try {
            String current = args[args.length - 1];

            List<String> tabComplete = this.command.callTabComplete(sender, args);
            List<String> result;
            if (tabComplete == null) {
                Player target = sender instanceof Player ? (Player) sender : null;
                result = Bukkit.getOnlinePlayers().stream()
                        .filter(player -> target == null || target.canSee(player))
                        .map(HumanEntity::getName)
                        .filter(name -> StringUtils.startsWithIgnoreCase(name, current))
                        .collect(Collectors.toList());
            } else {
                result = tabComplete.parallelStream().filter(str -> StringUtils.startsWithIgnoreCase(str, current)).collect(Collectors.toList());
            }

            if (!(sender instanceof Player)) {
                return result;
            }

            PlayerTabCompleteEvent event = new PlayerTabCompleteEvent((Player) sender, this.getCommand(), result);
            Bukkit.getPluginManager().callEvent(event);

            return event.getResult();
        } catch (CommandException e) {
            sender.sendMessage(ChatColor.RED + "Something went wrong: " + e.getMessage());
            return ImmutableList.of();
        } catch (InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
            return ImmutableList.of();
        }

    }

    @Override
    public boolean testPermission(CommandSender sender) {
        return super.testPermissionSilent(sender);
    }

    @Override
    public boolean testPermissionSilent(CommandSender sender) {
        return this.command.hasAccess(sender);
    }
}
