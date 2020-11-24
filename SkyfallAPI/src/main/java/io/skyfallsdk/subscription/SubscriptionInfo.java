package io.skyfallsdk.subscription;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.function.Predicate;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SubscriptionInfo {

    /**
     * Determines the position that the subscription should be received at.
     *
     * @return The priority in which this subscription will be received.
     */
    SubscriptionPriority priority() default SubscriptionPriority.MIDDLE;

    Class<? extends SubscriptionController> controller();
}
