package com.epam.rd.autocode.dllist;

import java.util.Optional;

class Node {

	Object element;
	Node next;
	Node prev;

	Node(Object obj, Node prev, Node next) {
		this.element = obj;
		this.next = next;
		this.prev = prev;

	}

}

public class DoublyLinkedListImpl implements DoublyLinkedList {

	private Node head;
	private Node tail;

	private int getSize() {
		int size = 0;
		Node current = head;
		while (current != null) {
			size++;
			current = current.next;
		}
		return size;
	}

	@Override
	public boolean addFirst(Object element) {
		Node newNode = new Node(element, null, null);
		if (head == null) {
			head = tail = newNode;
		} else {
			newNode.next = head;
			head = newNode;

		}
		return true;
	}

	@Override
	public boolean addLast(Object element) {
		Node new_Node = new Node(element, null, null);
		if (element == null)
			return false;

		if (head == null) {
			head = new_Node;

			return true;
		}
		Node current = head;

		while (current.next != null)
			current = current.next;

		current.next = new_Node;

		new_Node.prev = current;

		return true;
	}

	@Override
	public void delete(int index) {
		if (index == 0) {
			removeFirst();
		} else if (index == getSize()) {
			removeLast();
		} else {
			remove(index);

		}
	}

	private void remove(int index) {
		Node previousNode = findNodeByIndex(index - 1);
		previousNode.next = previousNode.next.next;
	}

	private Node findNodeByIndex(int index) {
		Node current = head;
		for (int i = 0; i < index; i++) {
			if (current.next != null) {
				current = current.next;
			}
		}
		return current;
	}

	private void removeFirst() {
		head = head.next;

	}

	private void removeLast() {
		if (tail != null) {
			tail = tail.prev;
			tail.next = null;
		}

	}

	private boolean isEmpty() {
		return head == null;
	}

	@Override
	public Optional<Object> remove(Object element) {
		if (element == null) {
			throw new NullPointerException();
		}
		if (!isEmpty()) {
			Node current = head;
			while (current != null) {
				if (current.element == element) {
					current.prev = current.next;

					return Optional.of(current.element);
				}
				current = current.next;
			}
		}
		return Optional.empty();

	}

	@Override
	public boolean set(int index, Object element) throws IndexOutOfBoundsException {
		if (index < 0 || index >= getSize()) {
			throw new IndexOutOfBoundsException();
		}

		if (element == null)
			return false;

		Node newNode = new Node(element, null, null);

		if (index == 0) {
			newNode.next = head;
			head = newNode;
		} else {
			Node prevNode = findNodeByIndex(index - 1);
			newNode.next = prevNode.next.next;
			prevNode.next = newNode;

		}
		return true;
	}

	@Override
	public int size() {
		return getSize();
	}

	@Override
	public Object[] toArray() {
		Object[] result = new Object[getSize()];
		int i = 0;
		Node current = head;
		while (i < getSize()) {
			if (current != null) {
				result[i++] = current.element;
				current = current.next;
			}
		}
		return result;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		Node current = head;
		while (current != null) {
			builder.append(current.element);
			current = current.next;
			if (current != null) {
				builder.append(" ");
			}
		}
		return builder.toString();
	}

}