package com.epam.rd.autocode.collection.array;

import java.util.Arrays;
import java.util.Optional;

public class SimpleArrayListImpl implements SimpleArrayList {

    private static final int DEFAULT_CAPACITY = 10;
    private static final int FACTOR_MULTIPLIER = 2;
    private static final double INCREASE_LOAD_FACTOR = 0.75;
    private static final double DECREASE_LOAD_FACTOR = 0.4;

    private Object[] elements;
    private int size;

    /**
     * Creates a list with the default capacity = 10.
     */
    public SimpleArrayListImpl() {
        this.elements = new Object[DEFAULT_CAPACITY];
        this.size = 0;
    }

    @Override
    public boolean add(Object element) {
        if (element == null) throw new NullPointerException();
        if (isEmpty()) {
            elements[0] = element;
        }
        elements[size] = element;
        size++;

        if (size >= elements.length * INCREASE_LOAD_FACTOR) {
            increaseCapacity();
        }
        return true;
    }

    @Override
    public Optional<Object> remove(Object el) {
        if (el == null) throw new NullPointerException();

        for (int i = 0; i < size; i++) {
            if (elements[i].equals(el)) {
                Object removed = elements[i];
                System.arraycopy(elements, i + 1, elements, i, size - i - 1);
                elements[size - 1] = null;
                size--;
                return Optional.of(removed);
            }
        }
        if (size <= elements.length * DECREASE_LOAD_FACTOR) {
            decreaseCapacity();
        }
        return Optional.empty();
    }

    private void increaseCapacity() {
        int newCapacity = (int) (elements.length * INCREASE_LOAD_FACTOR * FACTOR_MULTIPLIER);
        Object[] newArray = new Object[newCapacity];
        System.arraycopy(elements, 0, newArray, 0, size);
        elements = newArray;
    }

    @Override
    public boolean decreaseCapacity() {
        int newCapacity = size * FACTOR_MULTIPLIER;
        if (newCapacity < elements.length) {
            Object[] newArray = new Object[newCapacity];
            System.arraycopy(elements, 0, newArray, 0, size);
            elements = newArray;
            return true;
        }
        return false;
    }

    @Override
    public int capacity() {
        return elements.length;
    }

    @Override
    public Object get(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
        return elements[index];
    }

    private boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < size; i++) {
            sb.append(elements[i]);
            if (i < size - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }
}
