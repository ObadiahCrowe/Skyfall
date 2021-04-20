package io.skyfallsdk.world;

import io.skyfallsdk.Server;
import io.skyfallsdk.server.Difficulty;
import io.skyfallsdk.server.Gamemode;
import io.skyfallsdk.world.block.Block;

import java.nio.file.Path;

public class SkyfallWorld implements World {

    private final String name;
    private final Dimension dimension;

    public SkyfallWorld(String name, Dimension dimension) {
        this.name = name;
        this.dimension = dimension;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Path getDirectory() {
        return Server.get().getPath().resolve("worlds").resolve(this.name);
    }

    @Override
    public Block getBlockAt(Position position) {
        return null;
    }

    @Override
    public Dimension getDimension() {
        return null;
    }

    @Override
    public Gamemode getGamemode() {
        return null;
    }

    @Override
    public Difficulty getDifficulty() {
        return null;
    }
}
