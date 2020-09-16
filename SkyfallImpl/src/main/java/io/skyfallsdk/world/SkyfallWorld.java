package io.skyfallsdk.world;

import io.skyfallsdk.server.Difficulty;
import io.skyfallsdk.server.Gamemode;
import io.skyfallsdk.world.block.Block;

import java.nio.file.Path;

public class SkyfallWorld implements World {

    @Override
    public String getName() {
        return null;
    }

    @Override
    public Path getDirectory() {
        return null;
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
