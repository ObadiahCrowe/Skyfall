package net.treasurewars.core.command.options;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Used to mark a command executor method as unsafe. Synchronizes the entire execution, including argument
 * parsing.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ThreadUnsafe {

    /**
     * @return The class to lock on, e. g. the data object being modified
     */
    Class value() default Object.class;
}
