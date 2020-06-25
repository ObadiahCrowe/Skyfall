package io.skyfallsdk.server;

import io.skyfallsdk.chat.ChatComponent;

public interface CommandSender {

    void sendMessage(ChatComponent component);

    default void sendMessage(String message) {
        this.sendMessage(ChatComponent.from(message));
    }
}
