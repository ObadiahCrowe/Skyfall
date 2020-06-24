package net.treasurewars.core.command;

import net.treasurewars.core.TreasureCore;
import net.treasurewars.core.command.options.Permission;
import net.treasurewars.core.module.ModuleManager;
import net.treasurewars.core.modules.player.rank.Rank;
import net.treasurewars.core.modules.player.rank.RankModule;
import net.treasurewars.core.modules.player.rank.type.AbstractRank;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.lang.reflect.Method;

public abstract class AnnotatedPermissible {

    private final Rank requiredRank;
    private final boolean betaUnlocked;
    private final boolean betaTestersAllowed;

    public AnnotatedPermissible(Method method) {
        this.betaUnlocked = method.isAnnotationPresent(BetaUnlocked.class);
        this.betaTestersAllowed = !method.isAnnotationPresent(NoBetaTesters.class);

        Permission permission = method.getAnnotation(Permission.class);
        if (permission != null) {
            this.requiredRank = permission.value();
        } else {
            this.requiredRank = Rank.DEFAULT;
        }
    }

    public AnnotatedPermissible(Class<?> targetClass) {
        this.betaUnlocked = targetClass.isAnnotationPresent(BetaUnlocked.class);
        this.betaTestersAllowed = !targetClass.isAnnotationPresent(NoBetaTesters.class);

        Permission permission = targetClass.getAnnotation(Permission.class);
        if (permission != null) {
            this.requiredRank = permission.value();
        } else {
            this.requiredRank = Rank.DEFAULT;
        }
    }

    public AnnotatedPermissible(Rank requiredRank, boolean betaUnlocked) {
        this.requiredRank = requiredRank;
        this.betaUnlocked = betaUnlocked;
        this.betaTestersAllowed = true;
    }

    public Rank getRequiredRank() {
        return requiredRank;
    }

    public boolean isBetaUnlocked() {
        return betaUnlocked;
    }

    public boolean isBetaTestersAllowed() {
        return betaTestersAllowed;
    }

    public abstract boolean isPlayerOnly();

    public boolean hasAccess(CommandSender sender) {
        if (sender instanceof ConsoleCommandSender) {
            return !this.isPlayerOnly();
        }

        if (TreasureCore.getInstance().isBeta() && this.isBetaUnlocked()) {
            return true;
        }

        // We don't really want anything but console/player running our commands
        if (!(sender instanceof Player)) {
            return false;
        }

        if (CoreCommand.NO_COMMANDS.contains(((Player) sender).getUniqueId())) {
            return false;
        }
        if (this.getRequiredRank() == null || this.getRequiredRank() == Rank.DEFAULT) {
            return true;
        }

        RankModule module = ModuleManager.getModule(RankModule.class);
        AbstractRank rank = module.getProvider().getRank(((Player) sender).getUniqueId());

        if (TreasureCore.getInstance().isBeta()) {
            if (!this.isBetaTestersAllowed() && rank.getRank() == Rank.BETA_TESTER) {
                return false;
            }
        }

        return rank.getRank().isAtLeast(this.requiredRank);
    }
}
