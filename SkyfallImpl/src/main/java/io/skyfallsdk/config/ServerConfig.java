package io.skyfallsdk.config;

import io.skyfallsdk.Server;
import io.skyfallsdk.config.type.YamlConfig;
import io.skyfallsdk.protocol.ProtocolVersion;
import io.skyfallsdk.server.Difficulty;
import io.skyfallsdk.server.Gamemode;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ServerConfig extends YamlConfig<ServerConfig> {

    private static final ServerConfig DEFAULT_CONFIG = new ServerConfig(20, true, Gamemode.SURVIVAL, Difficulty.PEACEFUL);

    private ServerNetConfig server;
    private boolean debugEnabled;

    private int maxPlayers;
    private boolean onlineMode;

    private Gamemode gamemode;
    private Difficulty difficulty;

    private int renderDistance;

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

        this.server = new ServerNetConfig("Powered by Skyfall.", "localhost", 25565);
        this.debugEnabled = false;

        this.maxPlayers = maxPlayers;
        this.onlineMode = onlineMode;

        this.renderDistance = 10;

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

    public boolean isDebugEnabled() {
        return this.debugEnabled;
    }

    public void setDebugEnabled(boolean debugEnabled) {
        this.debugEnabled = debugEnabled;
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
}
