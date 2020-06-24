package io.skyfallsdk.command.parameter.argument;

import com.google.common.collect.ImmutableList;
import net.treasurewars.core.command.CoreCommand;
import net.treasurewars.core.command.parameter.CommandParameter;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

public class CommandArgument<T> implements CommandParameter<T> {

    private final int fieldIndex;
    private final Arg data;
    private final Class<T> type;
    private final String defaultName;
    private TabCompleterMethod completerMethod;

    public CommandArgument(int fieldIndex, Arg data, Class<T> type, String name) {
        this.fieldIndex = fieldIndex;
        this.data = data;
        this.type = type;
        this.defaultName = name;
    }

    public int getFieldIndex() {
        return fieldIndex;
    }

    public Class<T> getType() {
        return type;
    }

    public boolean isOptional() {
        return !this.data.defaultValue().equals(Arg.NONE);
    }

    public void setCompleterMethod(TabCompleterMethod completerMethod) {
        this.completerMethod = completerMethod;
    }

    public int getFirstArgsIndex() {
        if (this.data.indexStart() != -1) {
            return this.data.indexStart();
        }

        return this.data.index() == -1 ? this.fieldIndex : this.data.index();
    }

    public int getLastArgsIndex() {
        if (this.data.indexEnd() != -1) {
            return this.data.indexEnd();
        }

        if (this.data.indexStart() != -1) {
            return -1;
        }

        return this.data.index() == -1 ? this.fieldIndex : this.data.index();
    }

    public void applySpecifications(ArgumentSpecification specification) {
        ArgumentState state = this.isOptional() ? ArgumentState.OPTIONAL : ArgumentState.REQUIRED;
        if (this.data.indexStart() != -1) {
            specification.setState(this.data.indexStart(), state);
            if (this.data.indexEnd() == -1) {
                specification.setAllowInfinite(true);
                return;
            }

            for (int i = (this.data.indexStart() + 1); i <= this.data.indexEnd(); i++) {
                specification.setState(i, state);
            }
            return;
        }

        int index = this.data.index() == -1 ? this.fieldIndex : this.data.index();
        specification.setState(index, state);
    }

    @Override
    public boolean forceAsync() {
        return this.getParser().forceAsync();
    }

    @Override
    public boolean supportsAsync() {
        return this.getParser().supportsAsync();
    }

    public Collection<String> complete(CommandSender sender, String[] args) throws ArgumentParseException {
        if (this.completerMethod != null) {
            if (!this.completerMethod.hasAccess(sender)) {
                return ImmutableList.of();
            }

            try {
                return this.completerMethod.call(sender, args);
            } catch (InvocationTargetException | IllegalAccessException e) {
                throw new ArgumentParseException("An unexpected error occurred while tab completing", e);
            }
        }

        if (this.data.indexStart() != -1) {
            int endIndex = this.data.indexEnd();
            if (endIndex == -1) {
                endIndex = args.length;
            }

            int start = this.data.indexStart();
            String[] relevantArgs;
            if (start < args.length && endIndex <= args.length) {
                relevantArgs = Arrays.copyOfRange(args, this.data.indexStart(), endIndex);
            } else {
                if (!this.isOptional()) {
                    throw new ArgumentParseException("Not enough arguments!");
                }

                String defaultVal = this.data.defaultValue();
                relevantArgs = defaultVal.split(" ");
            }

            return this.getParser().complete(sender, this, relevantArgs);
        }

        int index = this.data.index() == -1 ? this.fieldIndex : this.data.index();
        if (index >= args.length) {
            if (!this.isOptional()) {
                throw new ArgumentParseException("Not enough arguments!");
            }

            return null;
        }

        return this.getParser().complete(sender, this, args[index]);
    }

    @Override
    public T parse(CommandSender sender, CoreCommand command, String[] args) throws ArgumentParseException {
        if (this.data.indexStart() != -1) {
            int endIndex = this.data.indexEnd();
            if (endIndex == -1) {
                endIndex = args.length;
            }

            int start = this.data.indexStart();
            String[] relevantArgs;
            if (start < args.length && endIndex <= args.length) {
                relevantArgs = Arrays.copyOfRange(args, this.data.indexStart(), endIndex);
            } else {
                if (!this.isOptional()) {
                    throw new ArgumentParseException("Not enough arguments!");
                }

                String defaultVal = this.data.defaultValue();
                relevantArgs = defaultVal.split(" ");
            }

            return this.getParser().parse(sender, this.getType(), relevantArgs);
        }

        int index = this.data.index() == -1 ? this.fieldIndex : this.data.index();
        if (index >= args.length) {
            if (!this.isOptional()) {
                throw new ArgumentParseException("Not enough arguments!");
            }

            String defaultVal = this.data.defaultValue();
            return this.getParser().parse(sender, this.getType(), defaultVal);
        }

        return this.getParser().parse(sender, this.getType(), args[index]);
    }

    public ArgumentParser<T> getParser() {
        ArgumentParser<T> parser = ArgumentFactory.getInstance().getParser(this.getType());
        if (parser == null) {
            throw new IllegalArgumentException("No parser for " + this + "! Needed parser for: " + this.getType().getName());
        }

        return parser;
    }

    public String getDescription() {
        if (!StringUtils.isBlank(this.data.desc())) {
            return this.data.desc();
        }

        if (!this.defaultName.startsWith("arg")) {
            return this.defaultName;
        }

        return this.getType().getSimpleName().toLowerCase();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof CommandArgument)) {
            return false;
        }

        CommandArgument<?> argument = (CommandArgument<?>) o;
        return getFieldIndex() == argument.getFieldIndex() &&
                Objects.equals(data, argument.data) &&
                Objects.equals(getType(), argument.getType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFieldIndex(), data, getType());
    }

    @Override
    public String toString() {
        return "CommandArgument{" +
                "fieldIndex=" + fieldIndex +
                ", data=" + data +
                ", type=" + type +
                ", description='" + getDescription() + '\'' +
                '}';
    }
}