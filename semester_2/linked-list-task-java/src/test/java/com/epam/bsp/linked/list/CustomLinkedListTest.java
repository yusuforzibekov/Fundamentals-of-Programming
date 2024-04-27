package com.epam.bsp.linked.list;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class CustomLinkedListTest {

    private final static List<Integer> LIST_EMPTY = List.of();

    private final static CustomLinkedList<Integer> CUSTOM_EMPTY = new CustomLinkedList<>(LIST_EMPTY);

    @Test
    public void testCustomLinkedListSample() {
        List<Integer> ints = List.of(1, 2, 3, 4);
        CustomLinkedList<Integer> custom = new CustomLinkedList<>(ints);
        assertTrue(custom.check(ints));

        List<String> strings = List.of("a", "b", "c");
        CustomLinkedList<String> custom2 = new CustomLinkedList<>(strings);
        assertTrue(custom2.check(strings));
    }

    @Test
    public void testRemoveNodesSample() {
        assertTrue(CUSTOM_EMPTY.removeNodes(107).check(LIST_EMPTY));

        CustomLinkedList<Integer> custom2 = new CustomLinkedList<>(List.of(107));
        assertTrue(custom2.removeNodes(107).check(LIST_EMPTY));

        CustomLinkedList<Integer> custom3 = new CustomLinkedList<>(
                List.of(107, 1, 107, 107, 2, 107, 3, 107, 4, 107)
        );
        assertTrue(custom3.removeNodes(107).check(List.of(1, 2, 3, 4)));

        CustomLinkedList<String> custom4 = new CustomLinkedList<>(
                List.of("ab", " ab ", "a b", "Ab", "ab", "AB", "ab")
        );
        assertTrue(custom4.removeNodes("ab")
                .check(List.of(" ab ", "a b", "Ab", "AB"))
        );
    }
    @Test
    public void testReverseSample() {
        assertTrue(CUSTOM_EMPTY.reverse().check(LIST_EMPTY));

        CustomLinkedList<Integer> custom2 = new CustomLinkedList<>(List.of(107));
        assertTrue(custom2.reverse().check(List.of(107)));

        CustomLinkedList<Integer> custom3 = new CustomLinkedList<>(
                List.of(1, 2, 3, 4, 5)
        );
        assertTrue(custom3.reverse().check(List.of(5, 4, 3, 2, 1)));
    }

    @Test
    public void testGetMiddleNodeSample() {
        assertTrue(CUSTOM_EMPTY.getRightMiddle().check(LIST_EMPTY));

        CustomLinkedList<Integer> custom2 = new CustomLinkedList<>(
                List.of(1, 2, 3, 4, 5)
        );
        assertTrue(custom2.getRightMiddle().check(List.of(3, 4, 5)));

        CustomLinkedList<Integer> custom3 = new CustomLinkedList<>(
                List.of(1, 2, 3, 4, 5, 6)
        );
        assertTrue(custom3.getRightMiddle().check(List.of(4, 5, 6)));
    }

}
