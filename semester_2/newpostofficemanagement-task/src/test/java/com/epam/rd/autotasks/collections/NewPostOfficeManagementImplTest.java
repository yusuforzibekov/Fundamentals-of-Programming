package com.epam.rd.autotasks.collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.IntStream;

import static com.epam.rd.autotasks.collections.Util.round;
import static org.junit.jupiter.api.Assertions.*;

class NewPostOfficeManagementImplTest {
    Box box1;
    Box box2;
    Box box3;
    Box box4;
    Box box5;
    List<Box> goodBoxes;
    List<Box> failBoxes;

    @BeforeEach
    void setUp() {
        IntSequence.reset();
        box1 = new Box("sender_1", "recipient_2", 3.5, 13.7, new BigDecimal("39.72"), "city_0", 9);
        box2 = new Box("sender_0", "recipient_2", 4.7, 9.56, new BigDecimal("67.38"), "city_1", 5);
        box3 = new Box("sender_4", "recipient_4", 15.9, 8.24, new BigDecimal("192.38"), "city_2", 12);
        box4 = new Box("sender_0", "recipient_2", 21.2, 11.92, new BigDecimal("234.18"), "city_3", 20);
        box5 = new Box("sender_1", "recipient_1", 19.6, 11.19, new BigDecimal("246.31"), "city_4", 13);
        goodBoxes = Arrays.asList(box1, box2, box3, box4, box5);
        failBoxes = Arrays.asList(box1, null, box2);
    }

    static List<Box> getBoxes(Random r, int size) {
        return IntStream.range(0, size).mapToObj(i -> new Box("sender_" + r.nextInt(5),
                        "recipient_" + r.nextInt(5),
                        round(r.nextDouble(1., 25.), 10),
                        round(r.nextDouble(.5, 15.), 100),
                        BigDecimal.valueOf(r.nextDouble(1., 250.)).setScale(2, RoundingMode.HALF_DOWN),
                        "city_" + i,
                        r.nextInt(1, 25)))
                .toList();
    }

    @Test
    void testConstructorShouldCreate() {
        assertDoesNotThrow(() -> new NewPostOfficeManagementImpl(List.of()),
                "It must not throw an exception for valid data");
        assertDoesNotThrow(() -> new NewPostOfficeManagementImpl(goodBoxes),
                "It must not throw an exception for valid data");
    }

    @Test
    void testConstructorShouldThrow() {
        assertThrows(NullPointerException.class, () -> new NewPostOfficeManagementImpl(null),
                "It must throw NullPointerException if parameter is null");
        assertThrows(NullPointerException.class, () -> new NewPostOfficeManagementImpl(failBoxes),
                "It must not permit null values.");
    }

    @Test
    void testGetBoxById() {
        IntSequence.reset();
        List<Box> boxes = getBoxes(new Random(15), 5);
        assertIterableEquals(boxes, goodBoxes);
        NewPostOfficeManagement office = new NewPostOfficeManagementImpl(goodBoxes);
        office.getBoxesByRecipient("a");

        boxes.forEach(expected -> {
            Optional<Box> actual = office.getBoxById(expected.getId());
            assertEquals(Optional.of(expected), actual,
                    "Expected: " + expected + ", but was: " + actual);
        });
        assertEquals(Optional.empty(), office.getBoxById(0));
        assertEquals(Optional.empty(), office.getBoxById(6));
    }

    @Test
    void testGetBoxesByRecipientShouldThrow() {
        assertThrows(NullPointerException.class,
                () -> new NewPostOfficeManagementImpl(goodBoxes).getBoxesByRecipient(null),
                "Must not permit 'null' values.");
    }

    @Test
    void testGetBoxesByRecipient() {
        IntSequence.reset();
        List<Box> boxes = getBoxes(new Random(15), 5);
        NewPostOfficeManagement office = new NewPostOfficeManagementImpl(boxes);
        office.getBoxById(1);

        List<Box> actual = office.getBoxesByRecipient("recipient_1");
        List<Box> expected = List.of(box5);
        assertEquals(expected, actual,
                "Expected: " + expected + ", but was: " + actual);

        actual = office.getBoxesByRecipient("recipient_2");
        expected = List.of(box1, box2, box4);
        assertIterableEquals(expected, actual,
                "Expected: " + expected + ", but was: " + actual);
    }

    @ParameterizedTest
    @CsvFileSource(files = "src/test/resources/weight.csv")
    void testGetDescSortedBoxesByWeight(int seq, String expected) {
        IntSequence.reset();
        List<Box> boxes = getBoxes(new Random(seq), 5);
        NewPostOfficeManagementImpl office = new NewPostOfficeManagementImpl(boxes);
        office.getBoxById(1);

        String actual = office.getDescSortedBoxesByWeight();
        expected = expected.replace(';', '\n');
        assertEquals(expected, actual,
                "Expected: " + expected + ", but was: " + actual);
    }

    @ParameterizedTest
    @CsvFileSource(files = "src/test/resources/cost.csv")
    void getAscSortedBoxesByCost(int seq, String expected) {
        IntSequence.reset();
        List<Box> boxes = getBoxes(new Random(seq), 5);
        NewPostOfficeManagementImpl office = new NewPostOfficeManagementImpl(boxes);
        office.getBoxById(1);

        String actual = office.getAscSortedBoxesByCost();
        expected = expected.replace(';', '\n');
        assertEquals(expected, actual,
                "Expected: " + expected + ", but was: " + actual);
    }
}
