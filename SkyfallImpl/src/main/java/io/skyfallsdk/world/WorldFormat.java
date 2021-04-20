package io.skyfallsdk.world;

import io.skyfallsdk.world.loader.AbstractWorldLoader;
import io.skyfallsdk.world.loader.AnvilWorldLoader;
import io.skyfallsdk.world.loader.SlimeWorldLoader;

public enum WorldFormat {

    ANVIL(AnvilWorldLoader.class),
    SLIME(SlimeWorldLoader.class);

    private final Class<? extends AbstractWorldLoader> worldLoader;

    WorldFormat(Class<? extends AbstractWorldLoader> worldLoader) {
        this.worldLoader = worldLoader;
    }

    public Class<? extends AbstractWorldLoader> getWorldLoader() {
        return this.worldLoader;
    }
}
