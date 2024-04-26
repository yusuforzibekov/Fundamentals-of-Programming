package com.epam.rd.autocode.collection.list;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

public class SingleLinkedListImpl implements List {

    private Node head;

    private static class Node {
        Object data;
        Node next;

        Node(Object data) {
            this.data = data;
        }

        Node(Object data, Node next) {
            this.data = data;
            this.next = next;
        }

        @Override
        public String toString() {
            return "[" + data + ']';
        }
    }

    public SingleLinkedListImpl() {
        head = new Node(0, null);
    }

    @Override
    public void clear() {
        head.next = null;
    }

    @Override
    public int size() {
        return len();
    }
    private int len() {
        int size = 0;
        Node current = head.next;
        while (current != null) {
            size++;
            current = current.next;
        }
        return size;
    }

    @Override
    public boolean add(Object el) {
        if (el == null) {
            throw new NullPointerException("Element cannot be null");
        }
        head.next = new Node(el, head.next);
        return true;
    }

    @Override
    public Optional<Object> remove(Object el) {
        if (el == null) {
            throw new NullPointerException("Element cannot be null");
        }
        Node current = head;
        while (current.next != null) {
            if (Objects.equals(current.next.data, el)) {
                Object removed = current.next.data;
                current.next = current.next.next;
                return Optional.of(removed);
            }
            current = current.next;
        }
        return Optional.empty();
    }

    @Override
    public Object get(int index) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        index = size() - index - 1;
        Node current = head.next;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current.data;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        Node current = head.next;
        while (current != null) {
            sb.append(current.data);
            if (current.next != null) {
                sb.append(", ");
            }
            current = current.next;
        }
        sb.append(']');
        return sb.toString();
    }

    @Override
    public Iterator<Object> iterator() {
        return new Iterator<Object>() {
            private Node current = head;
            private Node prev = null;
            boolean removeCalled = false;

            @Override
            public boolean hasNext() {
                return current.next != null;
            }

            @Override
            public Object next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                removeCalled = false;
                Object data = current.next.data;
                prev = current;
                current = current.next;
                return data;
            }

            @Override
            public void remove() {
                if (prev == null || removeCalled) {
                    throw new IllegalStateException();
                }
                removeCalled = true;
                prev.next = current.next;
                current = prev;
            }
        };
    }
}
