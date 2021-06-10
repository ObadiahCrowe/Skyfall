package io.skyfallsdk.event.entity;

import io.skyfallsdk.entity.EntityLiving;

public class EntityDamageEntityEvent extends EntityEvent {

    private final EntityLiving damager;

    public EntityDamageEntityEvent(EntityLiving entity, EntityLiving damager) {
        super(entity);

        this.damager = damager;
    }

    public EntityLiving getDamager() {
        return this.damager;
    }
}
