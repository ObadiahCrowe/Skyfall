package io.skyfallsdk.world;

import io.skyfallsdk.Server;
import io.skyfallsdk.server.Difficulty;
import io.skyfallsdk.server.Gamemode;
import io.skyfallsdk.world.block.Block;
import io.skyfallsdk.world.chunk.Chunk;

import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;

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

    CompletableFuture<Chunk> getChunk(int x, int z);



    default CompletableFuture<Void> unload() {
        return Server.get().getWorldLoader().unload(this);
    }
}
