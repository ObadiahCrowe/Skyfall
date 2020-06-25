package io.skyfallsdk.config.type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.skyfallsdk.Server;
import io.skyfallsdk.config.ConfigType;
import io.skyfallsdk.config.LoadableConfig;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.logging.Logger;

public abstract class JsonConfig<T extends JsonConfig> implements LoadableConfig<T> {

    private static final Gson GSON_INSTANCE = new GsonBuilder()
      .serializeNulls()
      .disableHtmlEscaping()
      .create();

    /**
     * Class of the configuration (for serialisation).
     */
    private transient final Class<? extends JsonConfig> configurationClass;

    /**
     * Logger to log to.
     */
    private transient final Logger logger;

    /**
     * Represents a configuration file.
     *
     * @param clazz Class of the configuration.
     */
    public JsonConfig(Class<? extends JsonConfig> clazz) {
        this.configurationClass = clazz;
        this.logger = Server.get().getLogger();
    }

    /**
     * Loads the configuration from disk and returns its instantiated object.
     *
     * @return The config instance.
     */
    @Override
    @SuppressWarnings("unchecked")
    public T load() {
        try {
            this.logger.info("Attempting to load config, " + this.getClass().getSimpleName() + "..");
            return (T) GSON_INSTANCE.fromJson(String.join("", Files.readAllLines(this.getPath())), this.configurationClass);
        } catch (IOException | ClassCastException e) {
            if (e instanceof NoSuchFileException) {
                this.logger.warning("Could not find, " + this.getPath().toFile().getName() + ", creating one now..");
            } else {
                e.printStackTrace();
            }

            T config = this.getDefaultConfig();

            config.save();

            return config;
        }
    }

    /**
     * Saves the config to disk.
     */
    @Override
    public void save() {
        try {
            this.logger.info("Saving config, " + this.getClass().getSimpleName() + "..");
            Path saveDirectory = this.getPath().getParent();
            if (!Files.exists(saveDirectory)) {
                Files.createDirectory(saveDirectory);
            }

            if (!Files.exists(this.getPath())) {
                Files.createFile(this.getPath());
            }

            Files.write(this.getPath(), GSON_INSTANCE.toJson(this).getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return The type of config this represents on disk.
     */
    @Override
    public ConfigType getType() {
        return ConfigType.JSON;
    }

    /**
     * @return The path that this config should be saved and loaded from.
     */
    @Override
    public abstract Path getPath();

    /**
     * @return Default config to write if none can be found.
     */
    @Override
    public abstract T getDefaultConfig();
}
