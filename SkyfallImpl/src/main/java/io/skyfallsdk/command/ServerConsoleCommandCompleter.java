package io.skyfallsdk.command;

import io.skyfallsdk.Server;
import org.jline.reader.Candidate;
import org.jline.reader.Completer;
import org.jline.reader.LineReader;
import org.jline.reader.ParsedLine;

import java.util.List;
import java.util.stream.Collectors;

public class ServerConsoleCommandCompleter implements Completer {

    private final ServerCommandMap commandMap;

    public ServerConsoleCommandCompleter(ServerCommandMap commandMap) {
        this.commandMap = commandMap;
    }

    @Override
    public void complete(LineReader lineReader, ParsedLine parsedLine, List<Candidate> list) {
        String line = parsedLine.line();
        if (line == null) {
            return;
        }

        list.addAll(this.commandMap.tabComplete(Server.get(), line)
          .stream()
          .map(Candidate::new)
          .collect(Collectors.toList()));
    }
}
