package io.skyfallsdk.chat;

import it.unimi.dsi.fastutil.chars.Char2IntArrayMap;
import it.unimi.dsi.fastutil.chars.Char2IntMap;

public enum ChatColour {

    BLACK('0'),
    DARK_BLUE('1'),
    DARK_GREEN('2'),
    DARK_CYAN('3'),
    DARK_RED('4'),
    DARK_PURPLE('5'),
    GOLD('6'),
    GRAY('7'),
    DARK_GRAY('8'),
    BLUE('9'),
    GREEN('a'),
    AQUA('b'),
    RED('c'),
    LIGHT_PURPLE('d'),
    YELLOW('e'),
    WHITE('f');

    private static final Char2IntMap CHAR_TO_COLOUR = new Char2IntArrayMap(ChatColour.values().length);

    static {
        for (ChatColour colour : ChatColour.values()) {
            CHAR_TO_COLOUR.put(colour.code, colour.ordinal());
        }
    }

    private final char code;

    ChatColour(char code) {
        this.code = code;
    }

    public char getCode() {
        return this.code;
    }

    public static ChatColour getByCode(char code) {
        int index = CHAR_TO_COLOUR.getOrDefault(code, -1);

        return index == -1 ? null : ChatColour.values()[index];
    }
}
