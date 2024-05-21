package com.epam.autotasks;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class CipherCreator {

    private CipherCreator() {
    }

    public static StringBuilder cipherText(File input) {
        if (!input.exists()) {
            throw new IllegalArgumentException("File does not exist");
        }
        if (input.isDirectory()) {
            throw new IllegalArgumentException("Input is a directory");
        }
        if (!input.canRead()) {
            throw new IllegalArgumentException("File cannot be read");
        }

        StringBuilder result = new StringBuilder();
        try (TransformerInputStream tis = new TransformerInputStream(new FileInputStream(input))) {
            int ch;
            while ((ch = tis.read()) != -1) {
                result.append((char) ch);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
