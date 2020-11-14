package io.skyfallsdk.expansion;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExpansionInfo {

    String name();

    String version();

    String[] authors();

    String[] dependencies() default {};

    long expansionId() default -1L;
}
