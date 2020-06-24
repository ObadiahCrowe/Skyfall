package io.skyfallsdk.command.util;

import io.skyfallsdk.chat.ChatColour;
import io.skyfallsdk.server.CommandSender;
import net.treasurewars.core.command.CoreCommand;
import net.treasurewars.core.command.parameter.argument.signature.CommandSignature;

public class CommandHelp {

    public static BaseComponent[] getHelpMessage(CommandSender sender, CoreCommand command) {
        String name = command.getMergedSignature().toString();

        BaseComponent[] message = TextComponent.fromLegacyText(TreasureCore.getRealmInfo().getPrimaryColour() + name + ChatColour.DARK_GRAY + " - " +
                ChatColour.GRAY + command.getDescription());

        HoverEvent event = getHoverEvent(sender, command);
        for (BaseComponent component : message) {
            component.setHoverEvent(event);
        }

        return message;
    }

    public static HoverEvent getHoverEvent(CommandSender sender, CoreCommand command) {
        CommandSignature[] signatures = command.getSignatures(sender);
        StringBuilder hoverEvent = new StringBuilder(ChatColour.GRAY + "Command Signatures:");
        for (CommandSignature signature : signatures) {
            hoverEvent.append("\n").append(ChatColour.DARK_GRAY).append(" - ").append(TreasureCore.getRealmInfo().getPrimaryColour()).append(signature.toString());
        }

        return new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText(hoverEvent.toString()));
    }
}
