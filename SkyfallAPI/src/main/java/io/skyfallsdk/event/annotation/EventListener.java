package io.skyfallsdk.event.annotation;

import io.skyfallsdk.event.EventPriority;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface EventListener {

    /**
     * Determines the position that the event should be fired at.
     *
     * @return The priority in which this event will fire.
     */
    EventPriority priority() default EventPriority.MIDDLE;
}
