package com.epam.rd.autocode.collection.list;

import java.util.Iterator;
import java.util.Optional;

public interface List extends Iterable<Object> {

    /**
     * Removes all elements from this list.
     */
    void clear();

    /**
     * Returns the number of elements in this list.
     *
     * @return the number of elements in this list.
     */
    int size();

    /**
     * Inserts the specified element to this list
     *
     * @param element the element to add
     * @return {@code true} if this list was changed {@code false} otherwise
     */
    boolean add(Object element);

    /**
     * Removes the first occurrence of the specified element from this list.
     * If the list does not contain the element, it is unchanged.
     * More formally, removes the first element {@code el} such that
     * {@code Objects.equals(o, el) == true} (if such an element exists).
     * Returns {@code true} if this list was changed or {@code false} otherwise.
     *
     * @param element the element to be removed from this list, if present
     * @return {@code true} if an element was removed as a result of this call, {@code false} otherwise.
     */
    Optional<Object> remove(Object element);

    /**
     * Returns an element from this list by its index.
     *
     * @return an element
     * @throws IndexOutOfBoundsException if the index is out of range. More formally,
     *                                   if {@code index < o || index >= size()}
     */
    Object get(int index);

    /**
     * Makes a string representation of this list.
     *
     * @return a string representation of this list.
     */
    String toString();

    /**
     * Returns an iterator over elements.
     *
     * @return an iterator
     */
    Iterator<Object> iterator();
}
