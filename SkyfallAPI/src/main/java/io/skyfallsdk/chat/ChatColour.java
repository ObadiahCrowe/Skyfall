package io.skyfallsdk.chat;

import it.unimi.dsi.fastutil.chars.Char2IntArrayMap;
import it.unimi.dsi.fastutil.chars.Char2IntMap;

public enum ChatColour implements Colour {

    BLACK('0', "#000000"),
    DARK_BLUE('1', "#0000aa"),
    DARK_GREEN('2', "#00aa00"),
    DARK_CYAN('3', "#00aaaa"),
    DARK_RED('4', "#aa0000"),
    PURPLE('5', "#aa00aa"),
    GOLD('6', "#ffaa00"),
    GRAY('7', "#aaaaaa"),
    DARK_GRAY('8', "#555555"),
    BLUE('9', "#5555ff"),
    GREEN('a', "#55ff55"),
    CYAN('b', "#55ffff"),
    RED('c', "#ff5555"),
    PINK('d', "#ff55ff"),
    YELLOW('e', "#ffff55"),
    WHITE('f', "#ffffff");

    private static final Char2IntMap CHAR_TO_COLOUR = new Char2IntArrayMap(ChatColour.values().length);

    static {
        for (ChatColour colour : ChatColour.values()) {
            CHAR_TO_COLOUR.put(colour.code, colour.ordinal());
        }
    }

    private final char code;
    private final String hex;

    ChatColour(char code, String hex) {
        this.code = code;
        this.hex = hex;
    }

    @Override
    public String toParsable() {
        return this.name();
    }

    public char getCode() {
        return this.code;
    }

    public String getHexCode() {
        return this.hex;
    }

    @Override
    public String toString() {
        return String.valueOf('§' + this.code);
    }

    public static ChatColour getByCode(char code) {
        int index = CHAR_TO_COLOUR.getOrDefault(code, -1);

        return index == -1 ? null : ChatColour.values()[index];
    }
}
