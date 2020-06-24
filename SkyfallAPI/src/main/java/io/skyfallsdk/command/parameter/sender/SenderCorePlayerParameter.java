package net.treasurewars.core.command.parameter.sender;

import net.treasurewars.core.command.CoreCommand;
import net.treasurewars.core.module.ModuleManager;
import net.treasurewars.core.modules.player.CorePlayerModule;
import net.treasurewars.core.modules.player.data.CorePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SenderCorePlayerParameter implements SenderParameter<CorePlayer> {

    @Override
    public boolean supportsCommandSender() {
        return false;
    }

    @Override
    public boolean forceAsync() {
        return true;
    }

    @Override
    public boolean supportsAsync() {
        return true;
    }

    @Override
    public CorePlayer parse(CommandSender sender, CoreCommand command, String[] args) throws Exception {
        if (!(sender instanceof Player)) {
            return null;
        }

        return ModuleManager.getModule(CorePlayerModule.class).getDataLoader().load(((Player) sender).getUniqueId());
    }
}
