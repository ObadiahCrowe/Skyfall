package io.skyfallsdk.command;

import io.skyfallsdk.chat.ChatComponent;
import io.skyfallsdk.permission.PermissionHolder;
import org.jetbrains.annotations.NotNull;

public interface CommandSender extends PermissionHolder {

    void sendMessage(@NotNull ChatComponent component);

    default void sendMessage(@NotNull String message) {
        this.sendMessage(ChatComponent.from(message));
    }

    void executeCommand(@NotNull String command);
}
