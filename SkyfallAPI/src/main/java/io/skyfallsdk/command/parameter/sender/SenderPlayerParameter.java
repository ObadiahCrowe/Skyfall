package io.skyfallsdk.command.parameter.sender;

import io.skyfallsdk.command.Command;
import io.skyfallsdk.player.Player;
import io.skyfallsdk.server.CommandSender;

public class SenderPlayerParameter implements SenderParameter<Player> {

    private final boolean allowNull;

    public SenderPlayerParameter(boolean allowNull) {
        this.allowNull = allowNull;
    }

    @Override
    public boolean supportsCommandSender() {
        return allowNull;
    }

    @Override
    public Player parse(CommandSender sender, Command command, String[] args) throws Exception {
        return (!this.allowNull || (sender instanceof Player)) ? (Player) sender : null;
    }
}
