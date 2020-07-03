package io.skyfallsdk.entity;

import io.skyfallsdk.world.World;

import java.util.UUID;

public interface Entity {

    int getId();

    UUID getUuid();

    World getWorld();
}
