package io.skyfallsdk.server;

import io.skyfallsdk.chat.ChatComponent;
import io.skyfallsdk.permission.PermissionHolder;

public interface CommandSender extends PermissionHolder {

    void sendMessage(ChatComponent component);

    default void sendMessage(String message) {
        this.sendMessage(ChatComponent.from(message));
    }
}
