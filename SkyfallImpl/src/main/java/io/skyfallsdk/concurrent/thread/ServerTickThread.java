package io.skyfallsdk.concurrent.thread;

import io.skyfallsdk.SkyfallServer;

public class ServerTickThread extends Thread {

    private static final int TICK_IN_MS = 50;

    private final SkyfallServer server;

    public ServerTickThread(SkyfallServer server) {
        super("Skyfall - Tick");

        this.server = server;
    }

    @Override
    public void run() {
        while (true) {
            try {
                long start = System.currentTimeMillis();

                // Do ticking

                long end = System.currentTimeMillis();
                long elapsed = end - start;
                long wait = TICK_IN_MS - elapsed;

                if (wait < 0) {
                    this.server.getLogger().warning("Server is falling behind by " + wait + "ms!");
                } else {
                    Thread.sleep(wait);
                }
            } catch (InterruptedException ignored) {
                break;
            }
        }
    }
}
