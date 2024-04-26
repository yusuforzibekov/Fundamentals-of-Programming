package com.epam.rd.autocode.collection.list;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Iterator;
import java.util.Optional;

import org.junit.jupiter.api.Test;

class SingleLinkedListImplTest {

    @Test
    void testSize() {
        List list = new SingleLinkedListImpl();
        list.add(1);
        list.add(2);
        list.add(3);
        int actual = list.size();
        assertEquals(3, actual,
                "Were added 3 elements but actual size: " + actual);
        list.clear();
        actual = list.size();
        assertEquals(0, actual,
                "The list was cleared but actual size: " + actual);
    }

    @Test
    void testGet() {
        List list = new SingleLinkedListImpl();
        list.add(1);
        list.add(2);
        list.add(3);
        assertEquals(3, list.get(2));
        assertEquals(Optional.of(1), list.remove(1));
        assertEquals( 2, list.get(0));
    }

    @Test
    void testGetShouldThrow() {
        List list = new SingleLinkedListImpl();
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(1),
                "It must throw 'IndexOutOfBoundsException' if the index out of range [0..size-1].");
    }

    @Test
    void testAddGetNull() {
        List list = new SingleLinkedListImpl();
        list.add(1);
        assertThrows(NullPointerException.class, () -> list.add(null),
                "The list must not accept null values");
        list.add(2);
        assertIterableEquals(asList(2, 1), list,
                "The list must not accept null values");
        assertEquals(2, list.get(1));
    }

    @Test
    void testAddToFront() {
        List list = new SingleLinkedListImpl();
        list.add(1);
        list.add(2);
        list.add(3);
        assertIterableEquals(asList(3, 2, 1), list);
    }

    @Test
    void testRemove() {
        List list = new SingleLinkedListImpl();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(2);
        assertEquals(Optional.empty(), list.remove(4));
        assertIterableEquals(asList(2, 3, 2, 1), list);
        assertEquals(Optional.of(2), list.remove(2));
        assertIterableEquals(asList(3, 2, 1), list);
        assertEquals(Optional.of(1), list.remove(1));
        assertIterableEquals(asList(3, 2), list);
        assertEquals(Optional.empty(), list.remove(4));
        assertIterableEquals(asList(3, 2), list);
        assertEquals(Optional.of(3), list.remove(3));
        assertIterableEquals(asList(2), list);
        assertEquals(Optional.of(2), list.remove(2));
        assertIterableEquals(asList(), list);
    }

    @Test
    void testClear() {
        List list = new SingleLinkedListImpl();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(2);
        list.clear();
        assertIterableEquals(asList(), list,
                "'clear()' must remove all elements from the list");
        assertEquals(0, list.size(),
                "'clear()' must reset the 'size' field");
    }

    @Test
    void testToString() {
        List list = new SingleLinkedListImpl();
        list.add(1);
        list.add(2);
        list.add(3);
        String actual = list.toString();
        assertEquals("[3, 2, 1]", actual,
                "Actual: '" + actual + "'");
    }

    @Test
    void testIterator() {
        List a = new SingleLinkedListImpl();
        a.add(3);
        a.add(2);
        a.add(1);
        Iterator<Object> it = a.iterator();
        assertTrue(it.hasNext(),
                "'hasNext()' must return 'true' if there is at least one element for iterations");
        assertEquals(1, it.next(),
                "'next()' must return correct element");
        assertTrue(it.hasNext(),
                "'hasNext()' must return 'true' if there is at least one element for iterations");
        assertEquals(2, it.next(),
                "'next()' must return correct element");
        assertTrue(it.hasNext(),
                "'hasNext()' must return 'true' if there is at least one element for iterations");
        assertEquals(3, it.next(),
                "'next()' must return correct element");
        assertFalse(it.hasNext(),
                "'hasNext()' must return 'false' if there are no elements for iterations");
    }

    @Test
    void testIterator2() {
        List a = new SingleLinkedListImpl();
        a.add(3);
        a.add(2);
        a.add(1);
        StringBuilder sb = new StringBuilder();
        for (Object el : a) {
            sb.append(el);
        }
        assertEquals("123", sb.toString(),
                "See the JavaDoc for Iterator to fix the order of iterations.");
    }

    @Test
    void testIterator3() {
        List a = new SingleLinkedListImpl();
        a.add(3);
        a.add(1);
        a.add(2);
        Iterator<Object> it = a.iterator();
        assertEquals(2, it.next(),
                "'next()' must return correct element");
        it.remove();
        assertIterableEquals(asList(1, 3), a,
                "'it.remove()' must remove the last returned element");
        assertEquals(1, it.next(),
                "'next()' must return correct element");
        it.remove();
        assertIterableEquals(asList(3), a,
                "'it.remove()' must remove the last returned element");
        assertEquals(3, it.next(),
                "'next()' must return correct element");
        it.remove();
        assertIterableEquals(asList(), a,
                "'it.remove()' must remove the last returned element");
    }

    @Test
    void testIterator4() {
        List a = new SingleLinkedListImpl();
        a.add(2);
        a.add(1);
        Iterator<Object> it = a.iterator();
        assertThrows(IllegalStateException.class, () -> it.remove(),
                "'it.remove()' must throw 'IllegalStateException' " +
                        "if there is no last returned element");
    }

    @Test
    void testIterator5() {
        List a = new SingleLinkedListImpl();
        a.add(2);
        a.add(1);
        Iterator<Object> it = a.iterator();
        it.next();
        it.remove();
        assertThrows(IllegalStateException.class, () -> it.remove(),
                "'it.remove()' must throw 'IllegalStateException' " +
                        "if it was called twice in row");
    }

    @Test
    void testIterator6() {
        assertThrows(IllegalStateException.class, () -> new SingleLinkedListImpl().iterator().remove(),
                "'it.remove()' must throw 'IllegalStateException' " +
                        "if there is no last returned element");
    }

    @Test
    void testIterator7() {
        // test correct position of element deletion if they are not unique
        List a = new SingleLinkedListImpl();
        a.add(1);
        a.add(2);
        a.add(3);
        a.add(2);
        Iterator<Object> it = a.iterator();
        assertEquals(2, it.next());
        assertEquals(3, it.next());
        assertEquals(2, it.next());
        it.remove();
        assertIterableEquals(asList(2,3,1), a,
                "Must remove last returned element. Actual: " + a);
    }
}
