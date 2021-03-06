package io.skyfallsdk.command.parameter.argument;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.skyfallsdk.Server;
import io.skyfallsdk.command.parameter.argument.parse.*;

import java.util.Collection;
import java.util.Map;

public class ArgumentFactory {

    private static final ArgumentFactory INSTANCE = new ArgumentFactory();

    public static ArgumentFactory getInstance() {
        return INSTANCE;
    }

    private final Map<Class<?>, ArgumentParser> classToParser;
    private final Collection<ArgumentParser> parsers;

    private ArgumentFactory() {
        this.classToParser = Maps.newConcurrentMap();
        this.parsers = Lists.newArrayList();

        // Register defaults
        this.registerParser(new BooleanParser());
        this.registerParser(new CommandParser());
        this.registerParser(new DoubleParser());
        this.registerParser(new EnumParser());
        this.registerParser(new ExpansionParser());
        this.registerParser(new GamemodeParser(), true);
        this.registerParser(new IntegerParser());
        this.registerParser(new IPAddressParser());
        this.registerParser(new LongParser());
        this.registerParser(new PathParser());
        this.registerParser(new PlayerParser());
        this.registerParser(new StringArrayParser());
        this.registerParser(new StringParser());
        this.registerParser(new SubstanceParser(), true);
        this.registerParser(new UUIDParser());
        this.registerParser(new WorldParser());
    }

    public void registerParser(ArgumentParser parser) {
        this.registerParser(parser, false);
    }

    private void registerParser(ArgumentParser parser, boolean ignoreEnumWarning) {
        this.parsers.add(parser);

        for (Class type : parser.getTypes()) {
            if (type.isEnum() && !(parser instanceof EnumParser) && !ignoreEnumWarning) {
                Server.get().getLogger().warn("Registered parser " + parser.getClass().getSimpleName() + " which " +
                  "supports parsing enum of type " + type + "! This is not recommended due to default support for " +
                  " enums which also has tab completion.");
            }

            // Avoid allowing to override existing. We prioritize by what was registered first
            this.classToParser.putIfAbsent(type, parser);
        }
    }

    public <T> ArgumentParser<T> getParser(Class<T> classToParse) {
        return this.classToParser.computeIfAbsent(classToParse, clazz -> this.parsers.stream()
                .filter(parser -> parser.canParse(clazz))
                .findFirst().orElse(null));
    }
}
