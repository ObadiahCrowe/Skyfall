package io.skyfallsdk.chat;

public enum ChatMode {

    /**
     * Client receives all messages.
     */
    FULL,

    /**
     * Client receives only command messages.
     */
    SYSTEM,

    /**
     * Client does not receive any messages.
     */
    NONE;
}
