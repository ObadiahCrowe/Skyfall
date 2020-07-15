package io.skyfallsdk.world;

import io.skyfallsdk.server.Difficulty;
import io.skyfallsdk.server.Gamemode;
import io.skyfallsdk.world.block.Block;

import java.nio.file.Path;

public interface World {

    String getName();

    Path getDirectory();

    Block getBlockAt(Position position);

    default Block getBlockAt(int x, int y, int z) {
        return this.getBlockAt(new Position(this, x, y, z));
    }

    Dimension getDimension();

    Gamemode getGamemode();

    Difficulty getDifficulty();


}
