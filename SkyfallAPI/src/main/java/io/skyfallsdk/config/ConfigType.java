package io.skyfallsdk.config;

import com.google.common.collect.Maps;

import javax.annotation.concurrent.Immutable;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;
import java.util.stream.Stream;

@Immutable
public enum ConfigType {

    /**
     * Known extensions for JSON formatted files.
     */
    JSON("json"),

    /**
     * Known extensions for YAML formatted files.
     */
    YAML("yaml", "yml"),

    /**
     * If the file type is unknown.
     */
    UNKNOWN;

    private static final Map<String, ConfigType> TYPES = Maps.newHashMap();

    static {
        for (ConfigType type : ConfigType.values()) {
            for (String extension : type.getValidExtensions()) {
                TYPES.put(extension, type);
            }
        }
    }

    private final String[] validExtensions;

    ConfigType(String... validExtensions) {
        this.validExtensions = validExtensions;
    }

    public String[] getValidExtensions() {
        return this.validExtensions;
    }

    public static ConfigType getType(Path path) throws IOException {
        return getType(path.toFile());
    }

    public static ConfigType getType(File file) throws IOException {
        if (!file.exists()) {
            throw new IOException("File can not be found!");
        }

        String[] parts = file.getName().split(".");
        if (parts.length <= 1) {
            throw new IllegalStateException("File does not have an extension!");
        }

        String extension = parts[parts.length - 1];
        return TYPES.get(extension.toLowerCase());
    }
}
