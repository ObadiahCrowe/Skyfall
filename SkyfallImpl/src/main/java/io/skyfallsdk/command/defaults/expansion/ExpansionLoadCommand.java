package io.skyfallsdk.command.defaults.expansion;

import io.skyfallsdk.Server;
import io.skyfallsdk.command.options.*;
import io.skyfallsdk.command.parameter.argument.Arg;
import io.skyfallsdk.command.parameter.service.Service;
import io.skyfallsdk.server.CommandSender;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Command(name = "load", desc = "Loads an Expansion from disk.")
@Alias(value = { "l", "init", "enable" })
@Permission(value = "skyfall.expansion.load")
public class ExpansionLoadCommand {

    @CommandExecutor
    public void onCommandExecute(@Sender CommandSender sender, @Arg Path path, @Service Server server) {
        // TODO: 14/11/2020 load
    }
}