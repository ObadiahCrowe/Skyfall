package io.skyfallsdk.concurrent.thread;

import io.skyfallsdk.SkyfallServer;
import org.apache.logging.log4j.Level;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.terminal.TerminalBuilder;

import java.io.IOException;

public class ConsoleThread extends Thread {

    private static volatile boolean isRunning;

    private final SkyfallServer server;

    public ConsoleThread(SkyfallServer server) {
        super("Skyfall - Console");

        this.server = server;

        isRunning = true;
    }

    public static void setRunning(boolean running) {
        isRunning = running;
    }

    @Override
    public void run() {
        LineReader reader;
        try {
            reader = LineReaderBuilder.builder()
              .appName("Skyfall")
              .terminal(TerminalBuilder.builder()
                .dumb(true)
                .jansi(true)
                .build()
              )
              .build();
        } catch (IOException e) {
            this.server.getLogger().throwing(Level.FATAL, e);
            return;
        }

        while (isRunning) {
            String input = reader.readLine("→ ");
            if (input.isEmpty()) {
                continue;
            }

            this.server.executeCommand(input);
        }
    }
}
