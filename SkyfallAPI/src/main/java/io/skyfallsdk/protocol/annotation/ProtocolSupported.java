package io.skyfallsdk.protocol.annotation;

import io.skyfallsdk.protocol.ProtocolVersion;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates a feature which has been introduced or deprecated since a specific {@link ProtocolVersion}. Usage of deprecated
 * or future features can still be utilised, but may not result in the intended behaviour.
 *
 * Proceed with caution if using features with a differing supported {@link ProtocolVersion} range.
 *
 * Features that do not specify the "{@code to}" value are supported on the latest version.
 */
@Target(value = { ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ProtocolSupported {

    ProtocolVersion from();

    ProtocolVersion to() default ProtocolVersion.UNKNOWN;
}
