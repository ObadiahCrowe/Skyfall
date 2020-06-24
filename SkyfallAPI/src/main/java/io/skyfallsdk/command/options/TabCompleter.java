package io.skyfallsdk.command.options;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.List;

/**
 * A method which returns a {@link List} of {@link String strings}. There is no async support for this, everything
 * <b>will</b> run synchronously.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TabCompleter {

    String targetMethod() default "NONE";

    int argument();
}
