package io.skyfallsdk.command.options;

import io.skyfallsdk.player.Player;
import io.skyfallsdk.command.CommandSender;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface Sender {

    /**
     * If this can be a null value. This is mostly relevant for a situation where you may wish to access the
     * {@link CommandSender sender} as a {@link Player player}, but also support the command as a console command.
     * It is however recommended to implement two separate methods, with similar annotations etc. but different
     * method contracts such that one will support the normal {@link CommandSender} and the other will support
     * {@link Player}.
     *
     * @return If this can be null if the type does not match
     */
    boolean allowNull() default false;
}
