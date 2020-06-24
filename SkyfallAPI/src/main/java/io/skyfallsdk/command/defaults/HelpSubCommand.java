package io.skyfallsdk.command.defaults;

import io.skyfallsdk.chat.ChatColour;
import io.skyfallsdk.chat.ChatComponent;
import io.skyfallsdk.server.CommandSender;
import net.treasurewars.core.command.CoreCommand;
import net.treasurewars.core.command.options.Command;
import net.treasurewars.core.command.options.CommandExecutor;
import net.treasurewars.core.command.options.Sender;
import net.treasurewars.core.command.options.TabCompleter;
import net.treasurewars.core.command.parameter.argument.Arg;
import net.treasurewars.core.command.util.CommandCompletion;
import net.treasurewars.core.command.util.CommandHelp;
import net.treasurewars.core.command.util.ListCommand;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Command(name = "help", desc = "View helpful information about this command")
public class HelpSubCommand extends ListCommand<CoreCommand> {

    private static final int COMMANDS_PER_PAGE = 10;

    private final CoreCommand command;

    public HelpSubCommand(CoreCommand command) {
        this.command = command;
    }

    @CommandExecutor
    public void execute(@Sender CommandSender player, @Arg(defaultValue = "1") int page) {
        List<CoreCommand> commands = Arrays.stream(this.command.getSubCommands())
                .filter(cmd -> cmd.hasAccess(player))
                .sorted(Comparator.comparing(CoreCommand::getName))
                .collect(Collectors.toList());

        String header = ChatColour.BLUE + StringUtils.capitalize(this.command.getName());
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
    public ChatComponent getComponents(CommandSender player, CoreCommand command) {
        return CommandHelp.getHelpMessage(player, command);
    }

    @Override
    public String getCommandFormat() {
        return this.command.getCommandBaseSignature() + " help %s";
    }
}
