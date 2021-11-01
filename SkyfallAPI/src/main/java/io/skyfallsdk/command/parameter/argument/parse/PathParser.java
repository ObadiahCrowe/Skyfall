package io.skyfallsdk.command.parameter.argument.parse;

import com.google.common.collect.Lists;
import io.skyfallsdk.Server;
import io.skyfallsdk.command.parameter.argument.ArgumentParseException;
import io.skyfallsdk.command.parameter.argument.CommandArgument;
import io.skyfallsdk.command.CommandSender;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class PathParser implements ArgumentParser<Path>  {

    @Override
    public Collection<String> complete(CommandSender sender, CommandArgument<Path> argument, String value) {
        Path path = Server.get().getPath();
        List<String> fileNames = Lists.newArrayList();

        try {
            if (!Files.exists(path)) {
                return Collections.emptyList();
            }

            Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    if (Files.isHidden(file)) {
                        return FileVisitResult.CONTINUE;
                    }

                    StringBuilder builder = new StringBuilder();

                    if (file.getParent().equals(path)) {
                        builder.append(file.getFileName().toString());
                    } else {
                        String fileName = file.getFileName().toString();

                        List<String> parents = Lists.newArrayList();
                        while (!file.getParent().equals(path)) {
                            parents.add(file.getParent().getFileName().toString());
                            file = file.getParent();
                        }

                        Collections.reverse(parents);
                        parents.forEach(str -> builder.append(str).append(File.separator));

                        builder.append(fileName);
                    }

                    fileNames.add(builder.toString());
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        return fileNames.stream()
          .filter(fileName -> fileName.startsWith(value))
          .collect(Collectors.toList());
    }

    @Override
    public Class[] getTypes() {
        return new Class[] {
          Path.class
        };
    }

    @Override
    public Path parse(CommandSender sender, Class type, String value) throws ArgumentParseException {
        return Server.get().getPath().resolve(value);
    }
}
