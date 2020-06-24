package net.treasurewars.core.command.parameter.argument.parse;

import net.treasurewars.core.command.parameter.argument.ArgumentParseException;
import net.treasurewars.core.module.ModuleManager;
import net.treasurewars.core.modules.player.rank.Rank;
import net.treasurewars.core.modules.player.rank.RankModule;
import net.treasurewars.core.modules.player.rank.type.AbstractRank;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PlayerParser implements ArgumentParser<Player> {

    @Override
    public Class[] getTypes() {
        return new Class[]{Player.class};
    }

    @Override
    public Player parse(CommandSender sender, Class type, String value) throws ArgumentParseException {
        // Preset "functions"
        switch (value.toLowerCase()) {
            case "self()":
                if (!(sender instanceof Player)) {
                    throw new ArgumentParseException("Please provide a target!");
                }

                return (Player) sender;
        }

        Player player = Bukkit.getPlayer(value);
        if (player == null) {
            throw new ArgumentParseException("Couldn't find player \"" + value + "\"!");
        }

        if (sender instanceof Player && !((Player) sender).canSee(player) && !this.getRank((Player) sender).isAtLeast(this.getRank(player))) {
            throw new ArgumentParseException("Couldn't find player \"" + value + "\"!");
        }

        return player;
    }

    public Rank getRank(Player player) {
        RankModule module = ModuleManager.getModule(RankModule.class);
        AbstractRank rank = module.getProvider().getRank((player).getUniqueId());
        return rank.getRank();
    }
}
