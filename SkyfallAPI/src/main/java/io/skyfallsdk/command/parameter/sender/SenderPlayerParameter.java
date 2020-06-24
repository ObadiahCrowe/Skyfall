package net.treasurewars.core.command.parameter.sender;

import net.treasurewars.core.command.CoreCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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
    public boolean forceAsync() {
        return false;
    }

    @Override
    public boolean supportsAsync() {
        return true;
    }

    @Override
    public Player parse(CommandSender sender, CoreCommand command, String[] args) throws Exception {
        return (!this.allowNull || (sender instanceof Player)) ? (Player) sender : null;
    }
}
