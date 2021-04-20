package io.skyfallsdk.util;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Optional;

public class FileVisitorCallback extends SimpleFileVisitor<Path> {

    private final String match;
    private Path matchingPath;

    public FileVisitorCallback(String match) {
        this.match = match;
    }

    public Optional<Path> getMatchingPath() {
        return Optional.ofNullable(this.matchingPath);
    }

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
        Path potential = dir.resolve(this.match);
        if (!Files.exists(potential)) {
            return FileVisitResult.CONTINUE;
        }

        this.matchingPath = potential;
        return FileVisitResult.CONTINUE;
    }
}
