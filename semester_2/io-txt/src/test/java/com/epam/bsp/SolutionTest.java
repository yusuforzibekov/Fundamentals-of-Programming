package com.epam.bsp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;


public class SolutionTest {

    private static ClassLoader classLoader;

    private final static String DIR_INPUT = "inputs";
    private final static String DIR_EXPECTED = "expected";
    private final static String DIR_ACTUAL = "actual";
    private final static String IN = "input";
    private final static String OUT = "output";
    private final static String PATTERN = "test_%d_%s.txt";

    @BeforeEach
    public void init() {
        SolutionTest app = new SolutionTest();
        classLoader = getClass().getClassLoader();
    }

    @Test
    public void testLengthFrequency() {
        final int N_TESTS = 3;
        for(int i = 0; i < N_TESTS; i++) {
            File in = getFile(i, DIR_INPUT, IN);
            File out = getFile(i, DIR_ACTUAL, OUT);
            try {
                Solution.lengthFrequency(in, out);
            } catch (FileNotFoundException e) {
                throw new IllegalStateException(e.getMessage());
            }
            String actual = parseOutput(out);
            String expected = parseOutput(getFile(i, DIR_EXPECTED, OUT));
            assertEquals(expected, actual);
        }
    }

    private File getFile(int ii, String dir, String type) {
        try {
            String filename = String.format(PATTERN, ii, type);
            String name = String.format("%s/%s", dir, filename);
            URL resource = classLoader.getResource(name);
            return new File(resource.toURI());
        } catch(URISyntaxException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    private String parseOutput(File file) {
        try {
            return String.join("", Files.readAllLines(file.toPath()));
        } catch(IOException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

}