package com.epam.autotasks;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

public class TransformerInputStream extends FilterInputStream {

    public TransformerInputStream(InputStream in) {
        super(in);
    }

    @Override
    public int read() throws IOException {
        int ch = super.read();
        if (ch == -1) {
            return -1; // end of stream
        }

        char originalChar = (char) ch;
        if (Character.isLetter(originalChar)) {
            char base = Character.isUpperCase(originalChar) ? 'A' : 'a';
            return base + (originalChar - base + 2) % 26;
        } else if (originalChar == '\n') {
            return originalChar;
        } else {
            // Ignore non-alphabet characters by reading the next character
            return read();
        }
    }

    @Override
    public void close() throws IOException {
        super.close();
        System.out.println("Stream closed.");
    }
}
