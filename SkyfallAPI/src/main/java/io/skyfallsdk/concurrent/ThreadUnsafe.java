package io.skyfallsdk.concurrent;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Used to mark a method as unsafe. Synchronizes the entire execution.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ThreadUnsafe {

    /**
     * @return The class to lock on, e. g. the data object being modified
     */
    Class value() default Object.class;
}
