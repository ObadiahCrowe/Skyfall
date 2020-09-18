package io.skyfallsdk.command.util;

import io.skyfallsdk.chat.ChatColour;
import io.skyfallsdk.chat.ChatComponent;
import io.skyfallsdk.chat.event.HoverEvent;
import io.skyfallsdk.chat.event.HoverType;
import io.skyfallsdk.command.Command;
import io.skyfallsdk.command.parameter.argument.signature.CommandSignature;
import io.skyfallsdk.server.CommandSender;

public class CommandHelp {

    public static ChatComponent getHelpMessage(CommandSender sender, Command command) {
        String name = command.getMergedSignature().toString();

        return ChatComponent.from(ChatColour.BLUE + name + ChatColour.DARK_GRAY + " - " + ChatColour.GRAY + command.getDescription())
          .setHoverEvent(getHoverEvent(sender, command));
    }

    public static HoverEvent getHoverEvent(CommandSender sender, Command command) {
        CommandSignature[] signatures = command.getSignatures(sender);
        StringBuilder hoverEvent = new StringBuilder(ChatColour.GRAY + "Command Signatures:");
        for (CommandSignature signature : signatures) {
            hoverEvent.append("\n").append(ChatColour.DARK_GRAY).append(" - ").append(ChatColour.BLUE).append(signature.toString());
        }

        return new HoverEvent(HoverType.SHOW_TEXT, ChatComponent.from(hoverEvent.toString()));
    }
}
