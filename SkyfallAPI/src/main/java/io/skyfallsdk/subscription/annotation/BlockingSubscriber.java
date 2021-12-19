package io.skyfallsdk.subscription.annotation;

import io.skyfallsdk.subscription.SubscriptionPriority;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface BlockingSubscriber {

    /**
     * Determines the position that the subscription should be received at.
     *
     * @return The priority in which this subscription will be received.
     */
    SubscriptionPriority priority() default SubscriptionPriority.MIDDLE;
}
