package com.epam.autotasks;

import java.io.File;
import java.util.Arrays;
import java.util.Optional;

public class FileTree {
    private static final String FILE_INDENT = "├─ ";
    private static final String LAST_FILE_INDENT = "└─ ";
    private static final String DIRECTORY_INDENT = "│ ";
    private static final String LAST_DIRECTORY_INDENT = "  ";
    public Optional<String> tree(final String path) {
        if (path == null) {
            return Optional.empty();
        }
        File file = new File(path);
        if (!file.exists()) {
            return Optional.empty();
        }
        else if (file.isFile()) {
            return Optional.of(String.format("%s %d bytes", file.getName(), file.length()));
        }
        else {
            StringBuilder sb = new StringBuilder(String.format("%s %d bytes\n", file.getName(), totalSize(file)));
            File[] files = file.listFiles();
            Arrays.sort(files, (f1, f2) -> {
                if (f1.isDirectory() && f2.isFile()) return -1;
                if (f1.isFile() && f2.isDirectory()) return 1;
                return f1.getName().compareToIgnoreCase(f2.getName());
            });
            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory()) {
                    boolean isLastChild = (i == files.length - 1);
                    buildTree(files[i], "", isLastChild, sb);
                } else {
                    sb.append(getFileIndent(i == files.length - 1)).append(tree(files[i].getPath()).orElse("")).append("\n");
                }
            }
            return Optional.of(sb.toString());
        }
    }
    private void buildTree(File file, String prefix, boolean isLast, StringBuilder sb) {
        if (file.isFile()) {
            sb.append(prefix)
                    .append(getFileIndent(isLast))
                    .append(String.format("%s %d bytes\n", file.getName(), totalSize(file)));
        }
        if (file.isDirectory()) {
            sb.append(prefix)
                .append(getDirectoryIndent(isLast))
                .append(String.format("%s %d bytes\n", file.getName(), totalSize(file)));
            File[] files = file.listFiles();
            Arrays.sort(files, (f1, f2) -> {
                if (f1.isDirectory() && f2.isFile()) return -1;
                if (f1.isFile() && f2.isDirectory()) return 1;
                return f1.getName().compareToIgnoreCase(f2.getName());
            });
            for (int i = 0; i < files.length; i++) {
                boolean isLastChild = (i == files.length - 1);
                String childPrefix = prefix + getIndent(isLast) + " ";
                buildTree(files[i], childPrefix, isLastChild, sb);
            }
        }
    }
    private int totalSize(File file) {
        int size = 0;
        if (file.isFile()) size += file.length();
        else for (File f : file.listFiles()) size += totalSize(f);
        return size;
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
