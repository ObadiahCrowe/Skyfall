package io.skyfallsdk.config.type;

import io.skyfallsdk.Server;
import io.skyfallsdk.config.ConfigType;
import io.skyfallsdk.config.LoadableConfig;
import org.apache.logging.log4j.Logger;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public abstract class YamlConfig<T extends YamlConfig> implements LoadableConfig<T> {

    /**
     * Class of the configuration (for serialisation).
     */
    private transient final Class<T> clazz;

    /**
     * Logger to log to.
     */
    private transient final Logger logger;

    /**
     * Backing yaml to load to / from.
     */
    private transient final Yaml backingYaml;

    /**
     * Represents a configuration file.
     *
     * @param clazz Class of the configuration.
     */
    public YamlConfig(Class<T> clazz) throws NullPointerException {
        this.clazz = clazz;
        this.logger = Server.get().getLogger();
        this.backingYaml = new Yaml(new Constructor(this.clazz));
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
            return (T) this.backingYaml.load(new FileInputStream(this.getPath().toFile()));
        } catch (Exception e) {
            if (e instanceof FileNotFoundException) {
                this.logger.warn("Could not find, " + this.getPath().toFile().getName() + ", creating one now..");
            } else {
                e.printStackTrace();
            }

            T config = this.getDefaultConfig();

            config.save();

            return config;
        }
    }

    /**
     * @return The type of config this represents on disk.
     */
    @Override
    public ConfigType getType() {
        return ConfigType.YAML;
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

    @Override
    public void save() {
        try {
            if (!Files.exists(this.getPath())) {
                Files.createFile(this.getPath());
            }

            FileWriter writer = new FileWriter(this.getPath().toFile());

            this.backingYaml.dump(this, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
