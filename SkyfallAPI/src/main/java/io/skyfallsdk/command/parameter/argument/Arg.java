package io.skyfallsdk.command.parameter.argument;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * A value the user inputs in the command line that is then passed to the command execution method
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface Arg {

    /**
     * Constant for indicating that this argument has no default, and is therefore required.
     */
    String NONE = "THIS_ARGUMENT_IS_REQUIRED";

    /**
     * The index of the argument in the command line, starting at 0. If you were making a command using
     * Bukkit's API, this would be the same as the index used when accessing {@code args}
     *
     * @return The index of the argument in the command line. Defaults to chronological ordering in
     * command executor method
     */
    int index() default -1;

    /**
     * Some arguments require multiple arguments from the command line, e. g. messages. This specifies the
     * first index to include (<b>Inclusive</b>) in the parsing.
     * <p>
     * For example, if the command was on the form {@code /msg <player> <message>} this value would be set
     * to 1
     *
     * @return The first index of the arguments from command line that should be sent to the parser (Inclusive)
     * @see #index()
     */
    int indexStart() default -1;

    /**
     * The end index for multi-argument parsers. Not required. If not specified, and if {@link #indexStart()} is
     * specified, all arguments beyond {@link #indexStart()} will be passed.
     *
     * @return The end index (Exclusive)
     */
    int indexEnd() default -1;

    /**
     * The description/name of this argument, describing its purpose in as few words as possible
     *
     * @return The description/name of this argument
     */
    String desc() default "";

    /**
     * The default value of this argument, if it has one. Please note that this can be multiple
     * parameters (arguments) from the command line, if this argument in the command executor requires
     * that. If you for example need 3 values (that are normally seperated by spaces in the command line),
     * you should simple enter them in the same way here.
     * <p>
     * E. g. if this command is {@code /msg <player> [messages]}, and you want the default message to be
     * "Hello, I am stupid", simply put that as the default value. It will be passed to the parser as an
     * array consisting of {@code ["Hello,", "I", "am", "stupid"}, which the parser will then assemble.
     *
     * @return The default value of this argument
     */
    String defaultValue() default NONE;
}
