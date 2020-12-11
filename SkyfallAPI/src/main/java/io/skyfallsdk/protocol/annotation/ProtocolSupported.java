package io.skyfallsdk.protocol.annotation;

import io.skyfallsdk.protocol.ProtocolVersion;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value = { ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ProtocolSupported {

    ProtocolVersion from();

    ProtocolVersion to() default ProtocolVersion.UNKNOWN;
}
