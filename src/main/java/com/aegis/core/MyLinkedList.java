package com.aegis.core;

import java.util.NoSuchElementException;

public class MyLinkedList<T> {
    private class Node {
        T data;
        Node next;
        Node prev;

        Node(T data) {
            this.data = data;
            this.next = null;
            this.prev = null;
        }
    }

    private Node head;
    private Node tail;
    private int size;

    public MyLinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    // --- Public API ---

    /**
     * Adds an element to the end of the list.
     * O(1) time complexity.
     *
     * @param data The data to be added.
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot add null data to the list.");
        }

        Node newNode = new Node(data);
        if (isEmpty()) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
        size++;
    }

    /**
     * Adds an element to the beginning of the list.
     * O(1) time complexity.
     *
     * @param data The data to be added.
     */
    public void addFirst(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot add null data to the list.");
        }

        Node newNode = new Node(data);
        if (isEmpty()) {
            head = newNode;
            tail = newNode;
        } else {
            head.prev = newNode;
            newNode.next = head;
            head = newNode;
        }
        size++;
    }

    /**
     * Removes and returns the first element from the list.
     * O(1) time complexity.
     *
     * @return The data from the first element.
     * @throws NoSuchElementException if the list is empty.
     */
    public T removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("Cannot remove from an empty list.");
        }

        T data = head.data;

        if (head == tail) {
            // List has only one element
            head = null;
            tail = null;
        } else {
            head = head.next;
            head.prev = null;
        }

        size--;
        return data;
    }

    /**
     * Returns the element at the specified position in this list.
     * O(n) time complexity in the worst case (average O(n/2)).
     *
     * @param index The index of the element to return.
     * @return The element at the specified position.
     * @throws IndexOutOfBoundsException if the index is out of range (index < 0 || index >= size).
     */
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }

        // Optimization: check if index is in the first or second half
        Node current;
        if (index < size / 2) {
            current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        } else {
            current = tail;
            for (int i = size - 1; i > index; i--) {
                current = current.prev;
            }
        }

        return current.data;
    }

    /**
     * Returns the number of elements in this list.
     *
     * @return The size of the list.
     */
    public int size() {
        return this.size;
    }

    /**
     * Checks if the list is empty.
     *
     * @return true if this list contains no elements, false otherwise.
     */
    public boolean isEmpty() {
        return this.size == 0;
    }

    /**
     * Removes the element at the specified position.
     * (This is more complex: you must handle head, tail, and middle removal)
     *
     * @param index The index of the element to be removed.
     * @return The data that was removed.
     * @throws IndexOutOfBoundsException if the index is out of range.
     */
    public T remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }

        // Caso especial: remover primeiro elemento
        if (index == 0) {
            return removeFirst();
        }

        // Caso especial: remover último elemento
        if (index == size - 1) {
            T data = tail.data;
            tail = tail.prev;
            tail.next = null;
            size--;
            return data;
        }

        // Caso geral: remover do meio
        Node current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }

        T data = current.data;
        current.prev.next = current.next;
        current.next.prev = current.prev;
        size--;

        return data;
    }

}
