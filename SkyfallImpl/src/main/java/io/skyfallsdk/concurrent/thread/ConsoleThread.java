package io.skyfallsdk.concurrent.thread;

import io.skyfallsdk.SkyfallServer;
import io.skyfallsdk.command.ServerConsoleCommandCompleter;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.UserInterruptException;
import org.jline.terminal.TerminalBuilder;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class ConsoleThread extends Thread {

    private final SkyfallServer server;
    private final LineReader reader;

    public ConsoleThread(SkyfallServer server) {
        super("SF-Console");

        this.server = server;
        try {
            this.reader = LineReaderBuilder.builder()
              .appName("Skyfall")
              .terminal(TerminalBuilder.builder()
                .encoding(StandardCharsets.UTF_8)
                .dumb(true)
                .jansi(true)
                .build()
              )
              .variable(LineReader.HISTORY_FILE, java.nio.file.Paths.get(".console_history"))
              .completer(new ServerConsoleCommandCompleter(server.getCommandMap()))
              .build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                String input = this.reader.readLine("→ ");
                if (input.isEmpty()) {
                    continue;
                }

                this.server.executeCommand(input);
            } catch (UserInterruptException e) {
                break;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
