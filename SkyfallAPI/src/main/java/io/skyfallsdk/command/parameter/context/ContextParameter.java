package io.skyfallsdk.command.parameter.context;

import io.skyfallsdk.command.Command;
import io.skyfallsdk.command.parameter.CommandParameter;
import io.skyfallsdk.server.CommandSender;

public class ContextParameter implements CommandParameter<ContextContainer<?>> {

    private final Class<?> contextType;
    private final boolean instantiate;

    public ContextParameter(Class<?> contextType, boolean instantiate) {
        this.contextType = contextType;
        this.instantiate = instantiate;
    }

    @Override
    public ContextContainer<?> parse(CommandSender sender, Command command, String[] args) throws Exception {
        CommandContext context = command.getContext(sender);
        ContextContainer<?> specificContext = context.getContext(this.contextType);
        if (this.instantiate) {
            specificContext.instantiate();
        }

        return specificContext;
    }
}
