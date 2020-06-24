package net.treasurewars.core.command.util;

import com.google.common.collect.Lists;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.treasurewars.core.modules.chat.ChatColour;
import net.treasurewars.core.util.UtilMath;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public abstract class ListCommand<T> {

    public void printPage(Player player, int page, List<T> list, String title) {
        this.printPage(player, page, list, title, 10);
    }

    public void printPage(CommandSender player, int page, List<T> list, String title, int perPage) {
        if (list == null || list.size() == 0) {
            this.onNoElements(player);
            return;
        }

        int totalPages = (int) Math.ceil((double) list.size() / (double) perPage);
        page = UtilMath.clamp(page, 1, totalPages);

        List<T> pageList = list.subList((page - 1) * perPage, Math.min(list.size(), page * perPage));

        List<BaseComponent[]> componentList = Lists.newArrayList();

        for (T value : pageList) {
            Collections.addAll(componentList, this.getComponents(player, value));
        }

        String header = title + ChatColour.RESET + " " + ChatColour.DARK_GRAY + "(" + ChatColour.GRAY + page + "/" + totalPages + ChatColour.DARK_GRAY + ")";
        player.sendMessage(header);

        for (BaseComponent[] components : componentList) {
            if (!(player instanceof Player)) {
                player.sendMessage(TextComponent.toLegacyText(components));
                continue;
            }

            ((Player) player).spigot().sendMessage(components);
        }
    }

    public int getPageCount(int count) {
        return this.getPageCount(count, 10);
    }

    public int getPageCount(int count, int perPage) {
        return (int) Math.ceil((double) count / (double) perPage);
    }

    public abstract void onNoElements(CommandSender sender);

    public abstract BaseComponent[] getComponents(CommandSender player, T value);

    public abstract String getCommandFormat();
}
