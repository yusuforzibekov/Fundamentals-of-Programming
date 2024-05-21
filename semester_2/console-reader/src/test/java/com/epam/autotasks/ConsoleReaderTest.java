package com.epam.autotasks;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class ConsoleReaderTest {

    @ParameterizedTest
    @MethodSource("validNames")
    @DisplayName("Should print the number of names from the console.")
    void shouldReturnNameCountWhenValidValues(String expectedOutput, List<String> inputNames) {

        StringBuilder sb = new StringBuilder();
        for (String name : inputNames) {
            sb.append(name).append(System.lineSeparator());
        }
        final ByteArrayInputStream in = new ByteArrayInputStream(sb.toString().getBytes());

        System.setIn(in);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(out);
        System.setOut(printStream);
        PrintStream defaultOut = System.out;
        InputStream defaultIn = System.in;

        try {
            ConsoleReader.readNames();
            printStream.flush();
            String actual = out.toString().trim();
            assertEquals(expectedOutput, actual);
        } finally {
            System.setIn(defaultIn);
            System.setOut(defaultOut);
        }
    }

    @ParameterizedTest
    @MethodSource("invalidNames")
    @DisplayName("Should throw an exception when given invalid names.")
    void shouldThrowExceptionWheInvalidValues(List<String> inputNames) {

        StringBuilder sb = new StringBuilder();
        for (String name : inputNames) {
            sb.append(name).append(System.lineSeparator());
        }
        final ByteArrayInputStream in = new ByteArrayInputStream(sb.toString().getBytes());

        System.setIn(in);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(out);
        System.setOut(printStream);
        PrintStream defaultOut = System.out;
        InputStream defaultIn = System.in;

        try {
            assertThrows(RuntimeException.class, ConsoleReader::readNames);
        } finally {
            System.setIn(defaultIn);
            System.setOut(defaultOut);
        }
    }

    @ParameterizedTest
    @MethodSource("validNumbers")
    @DisplayName("Should print the numbers from the console and their sum.")
    void shouldReturnNumbersAndCountWhenValidValues(String expectedOutput, String inputNumbers) {

        final ByteArrayInputStream in = new ByteArrayInputStream(inputNumbers.getBytes());
        System.setIn(in);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(out);
        System.setOut(printStream);
        PrintStream defaultOut = System.out;
        InputStream defaultIn = System.in;

        try {
            ConsoleReader.readNumbers();
            printStream.flush();
            String actual = out.toString().trim();
            assertEquals(expectedOutput, actual);
        } finally {
            System.setIn(defaultIn);
            System.setOut(defaultOut);
        }
    }

    @ParameterizedTest
    @MethodSource("invalidNumbers")
    @DisplayName("Should throw an exception when given invalid numbers.")
    void shouldThrowExceptionWheInvalidValues(String inputNumbers) {

        final ByteArrayInputStream in = new ByteArrayInputStream(inputNumbers.getBytes());
        System.setIn(in);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(out);
        System.setOut(printStream);
        PrintStream defaultOut = System.out;
        InputStream defaultIn = System.in;

        try {
            assertThrows(RuntimeException.class, ConsoleReader::readNumbers);
        } finally {
            System.setIn(defaultIn);
            System.setOut(defaultOut);
        }
    }

    private static Stream<Arguments> validNames() {
        return Stream.of(
                Arguments.of("Number of names: 3", List.of("Ashley", "Marco", "Tony", "0")),
                Arguments.of("Number of names: 5",
                        List.of("Ashley", "Marco", "Tony", "Andrew I", "Jose San Juan", "0")),
                Arguments.of("Number of names: 4", List.of("John Doe-Smith", "Tony", "Andrew I", "Jose San Juan", "0")),
                Arguments.of("Number of names: 1", List.of("Marco D'Alavar", "0")),
                Arguments.of("Number of names: 1", List.of("Daniel Rodriguez, Jr.", "0")),
                Arguments.of("Number of names: 0", List.of("0"))
        );
    }

    private static Stream<Arguments> invalidNames() {
        return Stream.of(
                Arguments.of(List.of("Ashl1ey", "Marco", "Tony", "0")),
                Arguments.of(List.of("Ashley", "Marco", "Tony$$", "0")),
                Arguments.of(List.of("Ashley", "Marco0", "Tony", "0")),
                Arguments.of(List.of("-1", "=5", "Tony", "0"))
        );
    }

    private static Stream<Arguments> validNumbers() {
        return Stream.of(
                Arguments.of("Numbers: 132 865 456722 6 261" + System.lineSeparator() + "Sum: 457986",
                        "132 865 456722 6 261" + System.lineSeparator()),
                Arguments.of("Numbers: 0 " + Integer.MAX_VALUE +
                                System.lineSeparator() + "Sum: " + Integer.MAX_VALUE,
                        "0 " + Integer.MAX_VALUE + System.lineSeparator()),
                Arguments.of("Numbers: 99999999 781 86528 57902 678992 67890528 6 261 " + System.lineSeparator() +
                        "Sum: 168714997", "99999999 781 86528 57902 678992 67890528 6 261 " + System.lineSeparator()),
                Arguments.of("Numbers: 0" + System.lineSeparator() +
                        "Sum: 0", "0" + System.lineSeparator())
        );
    }

    private static Stream<Arguments> invalidNumbers() {
        return Stream.of(
                Arguments.of("-132" + System.lineSeparator()),
                Arguments.of("132yu" + System.lineSeparator()),
                Arguments.of("!%%132" + System.lineSeparator()),
                Arguments.of("132+1" + System.lineSeparator())
        );
    }
}