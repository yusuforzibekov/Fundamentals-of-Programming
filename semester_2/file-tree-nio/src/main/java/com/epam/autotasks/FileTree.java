package com.epam.autotasks;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class FileTree {
    private  Comparator<Path> comparator = Comparator.<Path, String>comparing(path -> String.valueOf(Files.isDirectory(path) ? 0 : 1))
            .thenComparing(path -> (path).getFileName().toString(), String.CASE_INSENSITIVE_ORDER);
    private static final String FILE_INDENT = "├─ ";
    private static final String LAST_FILE_INDENT = "└─ ";
    private static final String DIRECTORY_INDENT = "│ ";
    private static final String LAST_DIRECTORY_INDENT = "  ";
    public Optional<String> tree(final Path paths) {
        if (paths == null) {
            return Optional.empty();
        }
        Path root = Paths.get(paths.toUri());

        if (!Files.exists(root)) {
            return Optional.empty();
        }

        StringBuilder sb = new StringBuilder();
        try {
            if (Files.isRegularFile(root)) {
                sb.append(String.format("%s %d bytes", root.getFileName(), Files.size(root)));
            } else {
                long size = computeDirectorySize(root);
                sb.append(String.format("%s %d bytes%n", root.getFileName(), size));
                List<Path> contents = getDirectoryContents(root);
                contents.sort(comparator);
                int count = 0;
                for (Path elm : contents) {
                    count++;
                    if (Files.isDirectory(elm)) {
                        boolean isLastChild = (count == contents.size());
                        buildTree(elm, "", isLastChild, sb);
                    } else {
                        sb.append(getFileIndent(count == contents.size())).append(tree(elm).orElse("")).append("\n");
                    }
                }
            }
        } catch (Exception e) {
        }

        return Optional.of(sb.toString());
    }

    private long computeDirectorySize(Path dir) throws IOException {
        if (Files.isRegularFile(dir)) {
            return Files.size(dir);
        }
        long size = 0;
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
            for (Path entry : stream) {
                size += Files.isDirectory(entry) ? computeDirectorySize(entry) : Files.size(entry);
            }
        }
        return size;
    }

    private List<Path> getDirectoryContents(Path dir) throws IOException {
        List<Path> contents = new ArrayList<>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
            for (Path entry : stream) {
                contents.add(entry);
            }
        }
        return contents;
    }
    private void buildTree(Path path, String prefix, boolean isLast, StringBuilder sb) throws IOException {
        if (!Files.isDirectory(path)) {
            sb.append(prefix)
                    .append(getFileIndent(isLast))
                    .append(String.format("%s %d bytes\n", path.getFileName(), computeDirectorySize(path)));
        }
        if (Files.isDirectory(path)) {
            sb.append(prefix)
                    .append(getDirectoryIndent(isLast))
                    .append(String.format("%s %d bytes\n", path.getFileName(), computeDirectorySize(path)));
            List<Path> contents = getDirectoryContents(path);
            contents.sort(comparator);
            int count = 0;
            for (Path elm : contents) {
                count++;
                boolean isLastChild = (count == contents.size());
                String childPrefix = prefix + getIndent(isLast) + " ";
                buildTree(elm, childPrefix, isLastChild, sb);
            }
        }
    }
    private String getIndent(boolean isLast) {
        return isLast ? LAST_DIRECTORY_INDENT: DIRECTORY_INDENT;
    }
    private String getDirectoryIndent(Boolean isLast) {
        return isLast ? LAST_FILE_INDENT : FILE_INDENT;
    }
    private String getFileIndent(boolean isLast) {
        return isLast ? LAST_FILE_INDENT : FILE_INDENT;
    }
}
