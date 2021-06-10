package io.skyfallsdk.event.entity;

import io.skyfallsdk.entity.Entity;
import io.skyfallsdk.event.Event;

public abstract class EntityEvent implements Event {

    private final Entity entity;

    public EntityEvent(Entity entity) {
        this.entity = entity;
    }

    public Entity getEntity() {
        return this.entity;
    }
}
