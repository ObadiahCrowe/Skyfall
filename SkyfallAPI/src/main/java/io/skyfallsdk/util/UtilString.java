package io.skyfallsdk.util;

import java.util.UUID;
import java.util.regex.Pattern;

public class UtilString {

    private static final Pattern UUID_ADD_DASHES = Pattern.compile("(\\w{8})(\\w{4})(\\w{4})(\\w{4})(\\w{12})");

    public static UUID uuidFromUndashed(String rawUuid) {
        return UUID.fromString(UUID_ADD_DASHES.matcher(rawUuid).replaceAll("$1-$2-$3-$4-$5"));
    }
}
