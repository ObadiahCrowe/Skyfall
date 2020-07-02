package io.skyfallsdk.command;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ExecutionException;

public class CoreCommand extends AnnotatedPermissible {
    public static final Set<UUID> NO_COMMANDS = Sets.newConcurrentHashSet();

    private static final Comparator<CommandExecutorMethod> EXECUTOR_METHOD_COMPARATOR =
      Comparator.<CommandExecutorMethod>comparingInt(execMethod -> execMethod.getArguments().length).reversed();

    public static final String COMMAND_PREFIX = "/";
    public static final String WRAPPER_REQUIRED_ARGUMENT = "<%s>";
    public static final String WRAPPER_OPTIONAL_ARGUMENT = "[%s]";

    private static List<CommandExecutorMethod> getExecutorMethods(CoreCommand command, Class<?> commandClass, Object commandInstance) {
        List<CommandExecutorMethod> executorMethods = Lists.newArrayList();
        List<TabCompleterMethod> tabCompleterMethods = Lists.newArrayList();
        for (Method method : commandClass.getMethods()) {
            if (method.isAnnotationPresent(CommandExecutor.class)) {
                executorMethods.add(CommandExecutorMethod.fromMethod(command, commandInstance, method));
                continue;
            }

            if (!method.isAnnotationPresent(TabCompleter.class)) {
                continue;
            }

            tabCompleterMethods.add(TabCompleterMethod.fromMethod(command, commandInstance, method));
        }

        for (TabCompleterMethod method : tabCompleterMethods) {
            int argument = method.getTargetArgument();
            String targetMethod = method.getTargetMethod();
            for (CommandExecutorMethod executorMethod : executorMethods) {
                if (!targetMethod.equals("NONE") && !targetMethod.equalsIgnoreCase(executorMethod.getMethod().getName())) {
                    continue;
                }

                for (CommandParameter parameter : executorMethod.getParameters()) {
                    if (!(parameter instanceof CommandArgument)) {
                        continue;
                    }

                    if (((CommandArgument) parameter).getFirstArgsIndex() != argument) {
                        continue;
                    }

                    ((CommandArgument) parameter).setCompleterMethod(method);
                }
            }
        }

        return executorMethods;
    }

    public static CoreCommand fromClass(Class<?> commandClass) {
        Validate.notNull(commandClass);

        Object commandInstance;

        try {
            commandInstance = commandClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }

        return fromInstance(commandInstance);
    }

    public static CoreCommand fromInstance(Object commandInstance) {
        Validate.notNull(commandInstance);

        Class<?> commandClass = commandInstance.getClass();
        Command commandAnnot = commandClass.getAnnotation(Command.class);
        if (commandAnnot == null) {
            throw new IllegalArgumentException("\"" + commandClass.getName() + "\" is not a command!");
        }

        String name = commandAnnot.name();
        String desc = commandAnnot.desc();

        String[] aliases = new String[0];
        Alias aliasAnnot = commandClass.getAnnotation(Alias.class);
        if (aliasAnnot != null) {
            aliases = aliasAnnot.value();
        }

        List<CoreCommand> subCommandList = Lists.newArrayList();
        for (Class<?> innerClass : commandClass.getDeclaredClasses()) {
            if (innerClass.isInterface()) {
                continue;
            }

            if (!innerClass.isAnnotationPresent(Command.class)) {
                continue;
            }

            subCommandList.add(fromClass(innerClass));
        }

        SubCommand subCommandAnnot = commandClass.getAnnotation(SubCommand.class);
        if (subCommandAnnot != null) {
            for (Class subCommandClass : subCommandAnnot.value()) {
                if (!subCommandClass.isAnnotationPresent(Command.class)) {
                    throw new IllegalArgumentException("Invalid sub command " + subCommandClass.getName() + "!");
                }

                subCommandList.add(fromClass(subCommandClass));
            }
        }

        CoreCommand[] subCommands = subCommandList.toArray(new CoreCommand[0]);
        return new CoreCommand(commandClass, commandInstance, name, desc, aliases, subCommands);
    }

    private final Class<?> commandClass;
    private final String name;
    private final String description;
    private final String[] aliases;
    private final CommandExecutorMethod[] executorMethods;
    private final Object commandInstance;
    private CoreCommand superCommand;
    private CoreCommand[] subCommands;
    private boolean disabled;

    CoreCommand(Class<?> commandClass, Object commandInstance, String name, String description, String[] aliases, CoreCommand[] subCommands) {
        super(commandClass);

        this.commandClass = commandClass;
        this.name = name;
        this.description = description;
        this.aliases = aliases;
        this.subCommands = subCommands;
        this.commandInstance = commandInstance;

        for (CoreCommand command : this.subCommands) {
            command.superCommand = this;
        }

        if (this.subCommands.length != 0) {
            this.addSubcommand(fromInstance(new HelpSubCommand(this)));
        }

        List<CommandExecutorMethod> executorMethods = getExecutorMethods(this, commandClass, commandInstance);
        CloneExecutors annotation = commandClass.getAnnotation(CloneExecutors.class);
        if (annotation != null) {
            for (Class cloneExecutorTarget : annotation.value()) {
                try {
                    Object instance = cloneExecutorTarget.newInstance();
                    executorMethods.addAll(getExecutorMethods(this, cloneExecutorTarget, instance));
                } catch (InstantiationException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        executorMethods.sort(EXECUTOR_METHOD_COMPARATOR);
        this.executorMethods = executorMethods.toArray(new CommandExecutorMethod[0]);
    }

    public boolean isDisabled() {
        return this.disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public Object getCommandInstance() {
        return this.commandInstance;
    }

    public CoreCommand getSuperCommand() {
        return this.superCommand;
    }

    public CommandSignature getMergedSignature() {
        CommandSignature builder = null;
        for (CommandExecutorMethod method : this.getExecutorMethods()) {
            if (builder == null) {
                builder = method.createSignature();
                continue;
            }

            builder.merge(method.createSignature());
        }

        return builder == null ? new CommandSignature(this) : builder;
    }

    public String getCommandBaseSignature() {
        if (this.getSuperCommand() == null) {
            return COMMAND_PREFIX + this.getName();
        }

        return this.getSuperCommand().getCommandBaseSignature() + " " + this.getName();
    }

    public CommandSignature[] getSignatures() {
        List<CommandSignature> signatures = Lists.newArrayList();

        for (CommandExecutorMethod method : this.executorMethods) {
            signatures.add(method.createSignature());
        }

        for (CoreCommand subCommand : this.getSubCommands()) {
            signatures.add(subCommand.getMergedSignature());
        }

        return signatures.toArray(new CommandSignature[0]);
    }

    public CommandSignature[] getSignatures(CommandSender sender) {
        List<CommandSignature> signatures = Lists.newArrayList();

        for (CommandExecutorMethod method : this.executorMethods) {
            if (!method.hasAccess(sender)) {
                continue;
            }

            signatures.add(method.createSignature());
        }

        for (CoreCommand subCommand : this.getSubCommands()) {
            if (!subCommand.hasAccess(sender)) {
                continue;
            }

            signatures.add(subCommand.getMergedSignature());
        }

        return signatures.toArray(new CommandSignature[0]);
    }

    public Class<?> getCommandClass() {
        return this.commandClass;
    }

    public CoreCommand getSubCommand(String name) {
        return Arrays.stream(this.subCommands)
          .filter(subCmd -> subCmd.matchesLabel(name))
          .findFirst().orElse(null);
    }

    public CoreCommand getSubCommand(Class<?> commandClass) {
        return Arrays.stream(this.subCommands)
          .filter(subCmd -> subCmd.getCommandClass() == commandClass)
          .findFirst().orElse(null);
    }

    public void addSubcommand(Class<?> commandClass) {
        this.addSubcommand(fromClass(commandClass));
    }

    public void addSubcommand(CoreCommand command) {
        boolean needsHelp = this.getSubCommand(HelpSubCommand.class) == null && command.getCommandClass() != HelpSubCommand.class;

        this.subCommands = Arrays.copyOf(this.subCommands, this.subCommands.length + (needsHelp ? 2 : 1));
        this.subCommands[this.subCommands.length - (needsHelp ? 2 : 1)] = command;
        command.superCommand = this;

        if (!needsHelp) {
            return;
        }

        CoreCommand helpCommand = fromInstance(new HelpSubCommand(this));
        this.subCommands[this.subCommands.length - 1] = helpCommand;
        helpCommand.superCommand = this;
    }

    public String[] getAliases() {
        return this.aliases;
    }

    public String getName() {
        return this.name;
    }

    public boolean matchesLabel(String label) {
        return this.getName().equalsIgnoreCase(label)
          || Arrays.stream(this.getAliases()).anyMatch(alias -> alias.equalsIgnoreCase(label));
    }

    public String getDescription() {
        return this.description;
    }

    public CoreCommand[] getSubCommands() {
        return this.subCommands;
    }

    public CommandExecutorMethod[] getExecutorMethods() {
        return this.executorMethods;
    }

    @Override
    public boolean hasAccess(CommandSender sender) {
        if (this.getExecutorMethods().length > 0) {
            if (Arrays.stream(this.getExecutorMethods()).noneMatch(method -> method.hasAccess(sender))) {
                return false;
            }
        } else {
            if (Arrays.stream(this.getSubCommands())
              .filter(command -> !(command.getCommandInstance() instanceof HelpSubCommand))
              .noneMatch(command -> command.hasAccess(sender))) {
                return false;
            }
        }

        return super.hasAccess(sender);
    }

    @Override
    public boolean isPlayerOnly() {
        if (this.getExecutorMethods().length == 0
          || !Arrays.stream(this.getExecutorMethods()).allMatch(CommandMethodWrapper::isPlayerOnly)) {
            return false;
        }

        return Arrays.stream(this.getSubCommands()).allMatch(CoreCommand::isPlayerOnly);
    }

    public void callExecute(CommandSender sender, String[] args) {
        if (this.isDisabled()) {
            this.printError(sender, "This command has been temporarily disabled!");
            return;
        }

        if (!this.hasAccess(sender)) {
            this.printError(sender, "No permission!");
            return;
        }

        if (this.getSubCommands().length > 0) {
            if (args.length > 0) {
                CoreCommand subCommand = this.getSubCommand(args[0]);

                if (subCommand != null) {
                    subCommand.callExecute(sender, Arrays.copyOfRange(args, 1, args.length));
                    return;
                }
            }

            if (this.getExecutorMethods().length == 0) {
                this.getSubCommand(HelpSubCommand.class).callExecute(sender, args);
                return;
            }
        }

        UtilConcurrency.ASYNC_EXECUTOR.execute(() -> {
            CommandExecutorMethod method = null;
            CommandException firstException = null;
            for (CommandExecutorMethod executorMethod : this.getExecutorMethods()) {
                if (!executorMethod.hasAccess(sender)) {
                    continue;
                }

                try {
                    CommandException exception = executorMethod.call(sender, args).get();
                    if (exception == null) {
                        return;
                    }

                    // This means command somewhat executed and might actually have done something. That would
                    // not be good. We are also basically only resolving the commands based on signature anyway
                    // so if it throws CommandException that is not ArgumentParse it would not be great.
                    if (!(exception instanceof ArgumentParseException)) {
                        this.printError(sender, exception, executorMethod);
                        return;
                    }

                    if (firstException != null) {
                        continue;
                    }

                    firstException = exception;
                    method = executorMethod;
                } catch (InterruptedException | ExecutionException e) {
                    this.printError(sender, "An unexpected error occurred: " + e.getMessage());
                    e.printStackTrace();
                    return;
                }
            }

            if (firstException != null) {
                this.printError(sender, firstException, method);
                return;
            }

            this.printError(sender, "No permission!");
        });
    }

    public void printError(CommandSender sender, CommandException exc, CommandExecutorMethod method) {
        if (exc.showTooltip()) {
            sender.sendMessage(ChatColour.GRAY + method.createSignature().toString());
        }

        this.printError(sender, exc.getMessage());
    }

    public void printError(CommandSender sender, String message) {
        if (sender instanceof Player) {
            sender.sendMessage(MessageTemplate.ERROR.apply(message));
            return;
        }

        sender.sendMessage(ChatColour.RED + "Command could not be executed: " + message);
    }

    public List<String> callTabComplete(CommandSender sender, String[] args) throws CommandException, InvocationTargetException, IllegalAccessException {
        if (!this.hasAccess(sender)) {
            return ImmutableList.of();
        }

        if (args.length > 0 && this.getSubCommands().length > 0) {
            CoreCommand subCommand = this.getSubCommand(args[0]);
            if (subCommand != null) {
                return subCommand.callTabComplete(sender, Arrays.copyOfRange(args, 1, args.length));
            }
        }

        ArgumentParseException exception = null;
        List<String> tabComplete = Lists.newArrayList();
        if (args.length == 1) {
            Arrays.stream(this.getSubCommands()).map(CoreCommand::getName).forEach(tabComplete::add);
        }

        for (CommandExecutorMethod method : this.getExecutorMethods()) {
            if (!method.hasAccess(sender)) {
                continue;
            }

            try {
                // Ensure distinct values returned
                Collection<String> tabCompleteCurr = method.tabComplete(sender, args);
                if (tabCompleteCurr != null) {
                    tabCompleteCurr.stream()
                      .filter(str -> !tabComplete.contains(str))
                      .forEach(tabComplete::add);
                    continue;
                }

                Player target = sender instanceof Player ? (Player) sender : null;
                Bukkit.getOnlinePlayers().stream()
                  .filter(player -> target == null || target.canSee(player))
                  .map(HumanEntity::getName)
                  .filter(str -> !tabComplete.contains(str))
                  .forEach(tabComplete::add);
            } catch (ArgumentParseException e) {
                exception = e;
            }
        }

        if (exception == null || !tabComplete.isEmpty()) {
            return tabComplete;
        }

        this.printError(sender, exception.getMessage());
        return ImmutableList.of();
    }
}
