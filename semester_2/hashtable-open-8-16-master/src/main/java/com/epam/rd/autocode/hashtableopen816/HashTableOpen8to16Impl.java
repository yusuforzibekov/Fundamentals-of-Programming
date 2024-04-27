package com.epam.rd.autocode.hashtableopen816;


import java.util.ArrayList;
import java.util.List;

class HashtableOpen8to16Impl implements HashtableOpen8to16 {

    private static final int INITIAL_CAPACITY = 8;
    private static final int MAX_CAPACITY = 16;
    private static final double LOAD_FACTOR_THRESHOLD = 0.25;
    private int capacity;
    private int size;
    private List<Integer> keys;
    private Object[] values;


    public HashtableOpen8to16Impl() {
        this.capacity = INITIAL_CAPACITY;
        this.size = 0;
        this.values = new Object[capacity];
        this.keys = new ArrayList<>(capacity);

        for (int i = 0; i < capacity; i++) {
            keys.add(0);
        }
    }

    @Override
    public void insert(int key, Object value) {
        if (size == capacity && ! keys.contains(key)) resize();
        int index = hash(key);

        for (int i = 0; i < capacity; i++) {
            if (keys.get(index) == key && values[index] != null) {
                values[index] = value;
                break;
            } else {
                if (keys.get(index) == 0 && values[index] == null) {
                    keys.set(index, key);
                    values[index] = value;
                    size++;
                    if (size > MAX_CAPACITY) throw new IllegalStateException();
                    return;
                } else {
                    index = (index + 1) % capacity;
                    if (index >= capacity) {
                        index = 0;
                    }
                }
            }
        }
    }

    private void resize() {
        int newCapacity = capacity * 2;
        List<Integer> newKeys = new ArrayList<>(newCapacity);
        Object[] newValues = new Object[newCapacity];
        for (int i = 0; i < newCapacity; i++) {
            newKeys.add(0);
        }

        for (int i = 0; i < capacity; i++) {
            int newIndex = Math.abs(keys.get(i)) % newCapacity;

            while (newKeys.get(newIndex) != 0 || newValues[newIndex] != null) {
                newIndex++;
            }
            newKeys.set(newIndex, keys.get(i));
            newValues[newIndex] = values[i];
        }
        capacity = newCapacity;
        keys = newKeys;
        values = newValues;
    }

    private int hash(int key) {
        return Math.abs(key % capacity);
    }

    @Override
    public Object search(int key) {
        if (keys.contains(key)) {
            int pos = keys.indexOf(key);
            return values[pos];
        }
        return null;
    }

    @Override
    public void remove(int key) {
        for (int i = 0; i < capacity; i++) {
            if (keys.contains(key)) {
                int pos = keys.indexOf(key);
                if (values[pos] != null) {
                    keys.set(pos, 0);
                    values[pos] = null;
                    size--;
                    break;
                }
            }
        }
        if (size > 0 && size <= capacity * LOAD_FACTOR_THRESHOLD) {
            cutTable();
        }
    }

    @Override
    public int size() {
        return size;
    }

    private void cutTable() {
        int newCapacity = capacity / 2;
        List<Integer> newKeys = new ArrayList<>(newCapacity);
        Object[] newValues = new Object[newCapacity];
        for (int i = 0; i < newCapacity; i++) {
            newKeys.add(0);
        }

        for (int i = 0; i < capacity; i++) {
            if (keys.get(i) != 0) {
                int newIndex = Math.abs(keys.get(i) % newCapacity);
                while (newKeys.get(newIndex) != 0) {
                    newIndex = (newIndex + 1) % newCapacity;
                }
                newKeys.set(newIndex, keys.get(i));
                newValues[newIndex] = values[i];
            }
        }
        capacity = newCapacity;
        keys = newKeys;
        values = newValues;
    }

    @Override
    public int[] keys() {
        int index = 0;
        int[] arrayKeys = new int[capacity];
        for (int key : keys) {
            arrayKeys[index++] = key;
        }
        return arrayKeys;
    }

}