package io.skyfallsdk.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class UtilResource {

    public static InputStream readAsStream(String location) {
        return UtilResource.class.getClassLoader().getResourceAsStream(location);
    }

    public static Stream<String> readLinesAsStream(String location) {
        return new BufferedReader(new InputStreamReader(readAsStream(location))).lines();
    }

    public static Set<String> readLines(String location) {
        return readLinesAsStream(location).collect(Collectors.toSet());
    }

    public static String readAsString(String location) {
        return readLinesAsStream(location).collect(Collectors.joining());
    }
}
