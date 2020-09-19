package io.skyfallsdk.command.defaults;

import io.skyfallsdk.chat.ChatColour;
import io.skyfallsdk.chat.ChatComponent;
import io.skyfallsdk.command.Command;
import io.skyfallsdk.command.options.*;
import io.skyfallsdk.command.parameter.argument.Arg;
import io.skyfallsdk.command.util.CommandCompletion;
import io.skyfallsdk.command.util.CommandHelp;
import io.skyfallsdk.command.util.ListCommand;
import io.skyfallsdk.permission.defaults.PlayerPermission;
import io.skyfallsdk.permission.permissible.PlayerPermissible;
import io.skyfallsdk.server.CommandSender;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@io.skyfallsdk.command.options.Command(name = "help", desc = "View helpful information about this command")
public class HelpSubCommand extends ListCommand<Command> {

    private static final int COMMANDS_PER_PAGE = 10;

    private final Command command;

    public HelpSubCommand(Command command) {
        this.command = command;
    }

    @CommandExecutor
    public void execute(@Sender CommandSender player, @Arg(defaultValue = "1") int page) {
        List<Command> commands = Arrays.stream(this.command.getSubCommands())
                .filter(cmd -> cmd.hasAccess(player))
                .sorted(Comparator.comparing(Command::getName))
                .collect(Collectors.toList());

        String header = ChatColour.BLUE + this.command.getName().substring(0, 1).toUpperCase() + this.command.getName().substring(1).toLowerCase();
        this.printPage(player, page, commands, header, COMMANDS_PER_PAGE);
    }

    @TabCompleter(argument = 0)
    public List<String> completePageArg(@Sender CommandSender sender) {
        int pages = this.getPageCount((int) Arrays.stream(this.command.getSubCommands())
                .filter(cmd -> cmd.hasAccess(sender))
                .count(), COMMANDS_PER_PAGE);

        return CommandCompletion.createRange(1, pages);
    }

    @Override
    public void onNoElements(CommandSender player) {
        // Will never happen
    }

    @Override
    public ChatComponent getComponents(CommandSender player, Command command) {
        return CommandHelp.getHelpMessage(player, command);
    }

    @Override
    public String getCommandFormat() {
        return this.command.getCommandBaseSignature() + " help %s";
    }
}
