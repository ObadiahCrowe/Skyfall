package io.skyfallsdk.concurrent.tick;

import io.skyfallsdk.Server;
import io.skyfallsdk.SkyfallServer;
import io.skyfallsdk.server.ServerState;
import it.unimi.dsi.fastutil.longs.*;
import it.unimi.dsi.fastutil.objects.Object2LongMap;
import org.jetbrains.annotations.NotNull;

public class TickRegistrySequencialThread<T extends TickSpec<T>> extends Thread {

    private final Long2LongArrayMap lastTickTimes;

    private final SkyfallServer server;
    private final ServerTickRegistry<T> registry;

    public TickRegistrySequencialThread(@NotNull ServerTickRegistry<T> registry) {
        super("SF-SequentialRegistry-" + registry.getSpec().getName());

        this.lastTickTimes = new Long2LongArrayMap();

        this.server = (SkyfallServer) Server.get();
        this.registry = registry;
    }

    @Override
    public void run() {
        while (this.server.getState() == ServerState.RUNNING) {
            try {
                long startTime = System.currentTimeMillis();

                for (Object2LongMap.Entry<Tickable<T>> tickable : this.registry.getTickables()) {
                    long lastExecution = this.registry.getLastExecutionTime(tickable.getKey());

                    tickable.getKey().onTick();

                    long tickableEnd = System.currentTimeMillis();
                    long tickableElapsed = tickableEnd - lastExecution;

                    this.registry.setLastExecutionTime(tickable.getLongValue(), tickableEnd);
                    this.lastTickTimes.put(tickable.getLongValue(), tickableElapsed);
                }

                long endTime = System.currentTimeMillis();
                long elapsed = endTime - startTime;
                long wait = this.registry.getSpec().getTickLength() - elapsed;

                if (wait < 0) {
                    LongList highestOffenders = new LongArrayList();

                    for (Long2LongMap.Entry entry : this.lastTickTimes.long2LongEntrySet()) {
                        //
                    }

                    this.server.getLogger().warn(this.getName() + " is currently lagging behind by " + wait + " ms! The " +
                      "following Tickable's have been the biggest culprits: ");
                } else {
                    Thread.sleep(wait);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
