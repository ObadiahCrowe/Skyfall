package io.skyfallsdk.config;

import com.google.common.collect.Lists;
import io.skyfallsdk.Server;
import io.skyfallsdk.config.type.YamlConfig;
import io.skyfallsdk.protocol.ProtocolVersion;
import io.skyfallsdk.world.option.Difficulty;
import io.skyfallsdk.world.option.Gamemode;
import io.skyfallsdk.world.WorldFormat;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ServerConfig extends YamlConfig<ServerConfig> {

    private static final ServerConfig DEFAULT_CONFIG = new ServerConfig(20, true, Gamemode.SURVIVAL, Difficulty.PEACEFUL);

    private ServerNetConfig server;
    private ServerSentryConfig sentry;
    private ServerSpectreConfig spectre;
    private boolean debugEnabled;
    private boolean enableVersionWarnings;

    private int maxPlayers;
    private boolean onlineMode;

    private Gamemode gamemode;
    private Difficulty difficulty;

    private int renderDistance;
    private WorldFormat worldFormat;

    private String baseVersion;
    private List<String> supportedVersions;

    /**
     * Represents a configuration file.
     */
    public ServerConfig() {
        super(ServerConfig.class);
    }

    private ServerConfig(int maxPlayers, boolean onlineMode, Gamemode gamemode, Difficulty difficulty) {
        super(ServerConfig.class);

        this.server = new ServerNetConfig("Powered by Skyfall.", "0.0.0.0", 25565);
        this.sentry = new ServerSentryConfig(false, "", 1.0D);
        this.spectre = new ServerSpectreConfig(false, "", Lists.newArrayList("https://spectre.skyfallsdk.io"));
        this.debugEnabled = false;
        this.enableVersionWarnings = true;

        this.maxPlayers = maxPlayers;
        this.onlineMode = onlineMode;

        this.renderDistance = 10;
        this.worldFormat = WorldFormat.ANVIL;

        this.gamemode = gamemode;
        this.difficulty = difficulty;

        this.baseVersion = ProtocolVersion.values()[0].getName();
        this.supportedVersions = Arrays.stream(ProtocolVersion.values())
          .map(ProtocolVersion::getName)
          .collect(Collectors.toList());
    }

    public ServerNetConfig getNetworkConfig() {
        return this.server;
    }

    public ServerSentryConfig getSentryConfig() {
        return this.sentry;
    }

    public ServerSpectreConfig getSpectreConfig() {
        return this.spectre;
    }

    public boolean isDebugEnabled() {
        return this.debugEnabled;
    }

    public void setDebugEnabled(boolean debugEnabled) {
        this.debugEnabled = debugEnabled;
    }

    public boolean enableVersionWarnings() {
        return this.enableVersionWarnings;
    }

    public void setEnableVersionWarnings(boolean enableVersionWarnings) {
        this.enableVersionWarnings = enableVersionWarnings;
    }

    public int getMaxPlayers() {
        return this.maxPlayers;
    }

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public boolean isOnlineMode() {
        return this.onlineMode;
    }

    public void setOnlineMode(boolean onlineMode) {
        this.onlineMode = onlineMode;
    }

    public Gamemode getGamemode() {
        return this.gamemode;
    }

    public void setGamemode(Gamemode gamemode) {
        this.gamemode = gamemode;
    }

    public Difficulty getDifficulty() {
        return this.difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public int getRenderDistance() {
        return this.renderDistance;
    }

    public void setRenderDistance(int renderDistance) {
        this.renderDistance = renderDistance;
    }

    public WorldFormat getWorldFormat() {
        return this.worldFormat;
    }

    public void setWorldFormat(WorldFormat worldFormat) {
        this.worldFormat = worldFormat;
    }

    public ProtocolVersion getBaseVersion() {
        return ProtocolVersion.getByName(this.baseVersion);
    }

    public void setBaseVersion(ProtocolVersion version) {
        this.baseVersion = version.getName();
    }

    public List<ProtocolVersion> getSupportedVersions() {
        return this.supportedVersions.stream().map(ProtocolVersion::getByName).collect(Collectors.toList());
    }

    @Override
    public Path getPath() {
        return Server.get().getPath().resolve("config.yml");
    }

    @Override
    public ServerConfig getDefaultConfig() {
        return DEFAULT_CONFIG;
    }

    @Override
    public String toString() {
        return "ServerConfig{" +
          "server=" + server +
          ", debugEnabled=" + debugEnabled +
          ", maxPlayers=" + maxPlayers +
          ", onlineMode=" + onlineMode +
          ", gamemode=" + gamemode +
          ", difficulty=" + difficulty +
          ", renderDistance=" + renderDistance +
          ", baseVersion='" + baseVersion + '\'' +
          ", supportedVersions=" + supportedVersions +
          '}';
    }

    public static final class ServerNetConfig {

        private String motd;
        private String address;
        private int port;

        public ServerNetConfig() {}

        public ServerNetConfig(String motd, String address, int port) {
            this.motd = motd;
            this.address = address;
            this.port = port;
        }

        public String getMotd() {
            return this.motd;
        }

        public void setMotd(String motd) {
            this.motd = motd;
        }

        public String getAddress() {
            return this.address;
        }

        public int getPort() {
            return this.port;
        }

        @Override
        public String toString() {
            return "ServerNetConfig{" +
              "motd='" + motd + '\'' +
              ", address='" + address + '\'' +
              ", port=" + port +
              '}';
        }
    }

    public static final class ServerSentryConfig {

        private boolean enabled;
        private String dsn;
        private double traceSampleRate;

        public ServerSentryConfig() {}

        public ServerSentryConfig(boolean enabled, String dsn, double traceSampleRate) {
            this.enabled = enabled;
            this.dsn = dsn;
            this.traceSampleRate = traceSampleRate;
        }

        public boolean isEnabled() {
            return this.enabled;
        }

        public String getDsn() {
            return this.dsn;
        }

        public double getTraceSampleRate() {
            return this.traceSampleRate;
        }

        @Override
        public String toString() {
            return "ServerSentryConfig{" +
              "enabled=" + enabled +
              ", dsn='" + dsn + '\'' +
              ", traceSampleRate=" + traceSampleRate +
              '}';
        }
    }

    public static final class ServerSpectreConfig {

        private boolean enabled;
        private String apiKey;
        private List<String> repositories;

        public ServerSpectreConfig() {}

        public ServerSpectreConfig(boolean enabled, String apiKey, List<String> repositories) {
            this.enabled = enabled;
            this.apiKey = apiKey;
            this.repositories = repositories;
        }

        public boolean isEnabled() {
            return this.enabled;
        }

        public String getApiKey() {
            return this.apiKey;
        }

        public List<String> getRepositories() {
            return this.repositories;
        }

        @Override
        public String toString() {
            return "ServerSpectreConfig{" +
              "enabled=" + enabled +
              ", apiKey='" + apiKey + '\'' +
              ", repositories=" + repositories +
              '}';
        }
    }
}
