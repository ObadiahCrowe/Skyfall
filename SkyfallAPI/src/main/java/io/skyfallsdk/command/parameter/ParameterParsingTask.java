package net.treasurewars.core.command.parameter;

import com.google.common.util.concurrent.MoreExecutors;
import net.treasurewars.core.command.CoreCommand;
import net.treasurewars.core.command.exception.CommandException;
import net.treasurewars.core.util.UtilConcurrency;
import org.bukkit.command.CommandSender;

import java.util.concurrent.Executor;

public class ParameterParsingTask<T> implements Runnable {

    private final CommandSender sender;
    private final CoreCommand command;
    private final String[] args;
    private final CommandParameter<T> parameter;
    private final Executor executor;
    private final ParsingListener listener;

    public ParameterParsingTask(CommandSender sender, CoreCommand command, String[] args, CommandParameter<T> parameter, ParsingListener<T> listener, boolean first) {
        this.sender = sender;
        this.command = command;
        this.args = args;
        this.parameter = parameter;
        this.listener = listener;

        if (parameter.forceAsync()) {
            this.executor = UtilConcurrency.ASYNC_EXECUTOR;
        } else if (!parameter.supportsAsync()) {
            this.executor = UtilConcurrency.SYNC_EXECUTOR;
        } else {
            this.executor = first ? UtilConcurrency.ASYNC_EXECUTOR : MoreExecutors.sameThreadExecutor();
        }
    }

    public void execute() {
        this.executor.execute(this);
    }

    @Override
    public void run() {
        try {
            T value = this.parameter.parse(this.sender, this.command, this.args);
            this.listener.onSuccess(this.parameter, value);
        } catch (Throwable t) {
            if (!(t instanceof CommandException)) {
                t.printStackTrace();
                this.listener.onFailure(this.parameter, "An internal error occurred!");
                return;
            }

            this.listener.onFailure(this.parameter, t.getMessage());
        }
    }
}
