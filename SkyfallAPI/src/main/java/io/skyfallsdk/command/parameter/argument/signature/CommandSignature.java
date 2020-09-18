package io.skyfallsdk.command.parameter.argument.signature;

import io.skyfallsdk.command.Command;

import java.util.Arrays;
import java.util.stream.Collectors;

import static io.skyfallsdk.command.Command.WRAPPER_OPTIONAL_ARGUMENT;
import static io.skyfallsdk.command.Command.WRAPPER_REQUIRED_ARGUMENT;

public class CommandSignature {

    private final Command command;
    private UsageParameter[] parameters;

    public CommandSignature(Command command) {
        this.command = command;
        this.parameters = new UsageParameter[0];
    }

    public Command getCommand() {
        return command;
    }

    public UsageParameter[] getParameters() {
        return parameters;
    }

    public void addParameter(String value, boolean optional) {
        this.parameters = Arrays.copyOf(this.parameters, this.parameters.length + 1);
        this.parameters[this.parameters.length - 1] = new UsageParameter(value, optional);
    }

    public void merge(CommandSignature builder) {
        int length = this.parameters.length;
        if (length < builder.parameters.length) {
            this.parameters = Arrays.copyOf(this.parameters, builder.parameters.length);
            System.arraycopy(builder.parameters, length, this.parameters, length, this.parameters.length - length);

            for (int i = length; i < this.parameters.length; i++) {
                this.parameters[i].optional = true;
            }
        }

        length = Math.min(length, builder.parameters.length);
        for (int i = 0; i < length; i++) {
            UsageParameter parameter = builder.parameters[i];
            this.parameters[i].combine(parameter.optional, parameter.values);
        }

        for (int i = length; i < this.parameters.length; i++) {
            this.parameters[i].optional = true;
        }
    }

    @Override
    public String toString() {
        return this.getCommand().getCommandBaseSignature() + " " + Arrays.stream(this.getParameters())
                .map(UsageParameter::toString)
                .collect(Collectors.joining(" "));
    }

    public static class UsageParameter {

        private String[] values;
        private boolean optional;

        public UsageParameter(String value, boolean optional) {
            this.values = new String[]{value};
            this.optional = optional;
        }

        public void combine(boolean optional, String... values) {
            this.optional |= optional;
            this.values = Arrays.copyOf(this.values, this.values.length + values.length);
            System.arraycopy(values, 0, this.values, this.values.length - values.length, values.length);
        }

        @Override
        public String toString() {
            String names = String.join("/", this.values);
            return String.format(this.optional ? WRAPPER_OPTIONAL_ARGUMENT : WRAPPER_REQUIRED_ARGUMENT, names);
        }
    }
}
