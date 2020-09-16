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

    private int maxPlayers;
    private boolean onlineMode;

    private Gamemode gamemode;
    private Difficulty difficulty;

    private List<String> supportedVersions;

    /**
     * Represents a configuration file.
     */
    public ServerConfig() {
        super(ServerConfig.class);
    }

    private ServerConfig(int maxPlayers, boolean onlineMode, Gamemode gamemode, Difficulty difficulty) {
        super(ServerConfig.class);

        this.server = new ServerNetConfig("localhost", 25565);

        this.maxPlayers = maxPlayers;
        this.onlineMode = onlineMode;

        this.gamemode = gamemode;
        this.difficulty = difficulty;

        this.supportedVersions = Arrays.stream(ProtocolVersion.values())
          .map(ProtocolVersion::getName)
          .collect(Collectors.toList());
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public boolean isOnlineMode() {
        return onlineMode;
    }

    public void setOnlineMode(boolean onlineMode) {
        this.onlineMode = onlineMode;
    }

    public Gamemode getGamemode() {
        return gamemode;
    }

    public void setGamemode(Gamemode gamemode) {
        this.gamemode = gamemode;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
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

    private static final class ServerNetConfig {

        private String address;
        private int port;

        public ServerNetConfig() {}

        public ServerNetConfig(String address, int port) {
            this.address = address;
            this.port = port;
        }

        public String getAddress() {
            return this.address;
        }

        public int getPort() {
            return this.port;
        }
    }
}
