package com.epam.rd.autocode.collection.tree;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Y. Mishcheriakov
 */
class BinaryTreeTest {

    @Test
    void testSize() {
        Random r = new Random(205);
        BinaryTree tree = new BinaryTree();
        IntStream.range(0, 8)
                .forEach(v -> {
                    int nextInt = r.nextInt(-25, 25);
                    assertTrue(tree.add(nextInt),
                            "Should add if the element does not exists in the container.");
                    assertEquals(v + 1, tree.size(),
                            "Should increment size if the container was changed");
                });
        Random rn = new Random(205);
        IntStream.range(0, 8).forEach(v -> {
            assertFalse(tree.add(rn.nextInt(-25, 25)),
                    "Should not add if the element exists in the container.");
            assertEquals(8, tree.size(),
                    "The size should not be changed if the container was not changed");
        });
        Random rd = new Random(205);
        IntStream.range(-7, 1).map(v -> -v).forEach(v -> {
            int nextInt = rd.nextInt(-25, 25);
            assertEquals(nextInt, tree.remove(nextInt).orElse(-1),
                    "Should remove an element and return it if it exists in the container");
            assertEquals(v, tree.size(),
                    "The size should be decremented if the container was changed");
        });
    }

    @Test
    void testRemoveFromEmpty() {
        assertEquals(Optional.empty(), new BinaryTree().remove(0),
                "Must return 'Optional.empty' if there is no such element.");
    }

    @Test
    void testRemoveFromShouldThrow() {
        assertThrows(NullPointerException.class, () -> new BinaryTree().remove(null),
                "The parameter can not be 'null'");
    }

    @Test
    void testRemoveRight() {
        BinaryTree tree = new BinaryTree(8, 6, 12, 10);
        assertEquals(Optional.empty(), tree.remove(13));
        assertEquals(4, tree.size(),
                "Size of the tree must not be changed if the tree was not changed.");
    }

    @Test
    void testRemove() {
        // 14, 33, 26, 37, 19, 27, 0, 21, 23, 39, 39, 13, 2, 7, 39, 23, 2, 23, 9, 38
        Random r = new Random(922);
        Integer[] data = IntStream.generate(() -> r.nextInt(0, 40))
                .limit(20)
                .boxed()
                .toArray(Integer[]::new);
        BinaryTree tree = new BinaryTree(data);

        Integer[] toRemove = {14, 19, 21, 23, 39, 33, 13, 0, 2, 7, 9, 37, 27, 26, 38};
        int expectedSize = toRemove.length;
        assertEquals(Optional.empty(), tree.remove(35));
        assertEquals(expectedSize--, tree.size());
        for (Integer el : toRemove) {
            assertEquals(Optional.of(el), tree.remove(el));
            assertEquals(expectedSize--, tree.size());
        }
    }

    @Test
    void testAddAll() {
        Random r = new Random(25);
        Integer[] data = IntStream.generate(() -> r.nextInt(-5, 5))
                .limit(5)
                .boxed()
                .toArray(Integer[]::new);
        BinaryTree tree = new BinaryTree(data);
        assertEquals(3, tree.size(),
                "Constructor should accept all values except repeated elements. " +
                        "The provided data: " + Arrays.toString(data));

        tree.addAll(data);
        assertEquals(3, tree.size(),
                "Should not change size if elements were not added. " +
                        "The provided data: " + Arrays.toString(data));

        Integer[] newData = IntStream.generate(() -> r.nextInt(-5, 5))
                .limit(5)
                .boxed()
                .toArray(Integer[]::new);
        tree.addAll(newData);
        assertEquals(6, tree.size(),
                "The provided data in the constructor: " + Arrays.toString(data) +
                        ", the data in add method: " + Arrays.toString(newData));
    }

    @Test
    void testAdd() {
        Random r = new Random(25);
        Integer[] data = IntStream.generate(() -> r.nextInt(-5, 5))
                .limit(5)
                .boxed()
                .toArray(Integer[]::new);
        BinaryTree tree = new BinaryTree(data);
        assertEquals(3, tree.size(),
                "Constructor should accept all values except repeated elements. " +
                        "The provided data: " + Arrays.toString(data));

        for (Integer el : data) {
            assertFalse(tree.add(el));
            assertEquals(3, tree.size(),
                "Should not change size if elements were not added. " +
                        "The provided data: " + Arrays.toString(data));
        }

        Integer[] newData = IntStream.generate(() -> r.nextInt(5, 15))
                .limit(5)
                .boxed()
                .toArray(Integer[]::new);
        for (Integer el : newData) {
            assertTrue(tree.add(el),
                    "Elements must not be added twice: " + el + ", tree: " + tree);
        }
        assertEquals(8, tree.size(),
                "The provided data in the constructor: " + Arrays.toString(data) +
                        ", the data in add method: " + Arrays.toString(newData));
    }

    @Test
    void testConstructorShouldThrow() {
        assertThrows(NullPointerException.class, () -> new BinaryTree(null),
                "Comparable cannot be used on 'null'.");
        Integer[] integers = new Integer[]{1, null, 1};
        assertThrows(NullPointerException.class, () -> new BinaryTree(integers),
                "Comparable cannot be used on 'null' value.");
    }

    @Test
    void testToString() {
        Random r = new Random(123);
        Integer[] data = IntStream.generate(() -> r.nextInt(-25, 25))
                .limit(15)
                .boxed()
                .toArray(Integer[]::new);
        String expected = Arrays.stream(data)
                .distinct()
                .sorted()
                .map(String::valueOf)
                .collect(Collectors.joining(", ", "[", "]"));
        BinaryTree tree = new BinaryTree(data);
        String actual = tree.toString();
        assertEquals(expected, actual,
                "Expected: " + expected + " but was: " + actual);
        Integer[] data2 = IntStream.generate(() -> r.nextInt(-25, 25))
                .limit(15)
                .boxed()
                .toArray(Integer[]::new);
        tree.addAll(data2);
        Integer[] allData = Arrays.copyOf(data, 30);
        System.arraycopy(data2, 0, allData, 15, 15);
        expected = Arrays.stream(allData)
                .distinct()
                .sorted()
                .map(String::valueOf)
                .collect(Collectors.joining(", ", "[", "]"));
        actual = tree.toString();
        assertEquals(expected, actual,
                "Expected: " + expected + " but was: " + actual);
    }
}

