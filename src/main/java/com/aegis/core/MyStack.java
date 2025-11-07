package com.aegis.core;

import java.util.NoSuchElementException;

/**
 * A custom implementation of a generic Stack (LIFO - Last-In, First-Out).
 * This class is built from scratch and uses MyLinkedList internally for storage.
 *
 * @param <T> The type of element held in this stack.
 */

public class MyStack<T> {

    private MyLinkedList<T> list;

    /**
     * Constructs an empty stack.
     */
    public MyStack() {
        this.list = new MyLinkedList<>();
    }

    /**
     * Pushes an item onto the top of this stack.
     * O(1) time complexity.
     *
     * @param data The data to be added.
     */
    public void push(T data) {
        list.addFirst(data);
    }

    /**
     * Removes the object at the top of this stack and returns it.
     * O(1) time complexity.
     *
     * @return The data from the top of the stack.
     * @throws NoSuchElementException if the stack is empty.
     */
    public T pop() {
        return list.removeFirst();
    }

    /**
     * Looks at the object at the top of this stack without removing it.
     * O(1) time complexity.
     *
     * @return The data at the top of the stack.
     * @throws NoSuchElementException if the stack is empty.
     */
    public T peek() {
        if (isEmpty()) {
            throw new NoSuchElementException("Cannot peek at an empty stack.");
        }

        return list.get(0);
    }

    /**
     * Checks if the stack is empty.
     *
     * @return true if this stack contains no elements, false otherwise.
     */
    public boolean isEmpty() {
        return list.isEmpty();
    }

    /**
     * Returns the number of elements in this stack.
     *
     * @return The size of the stack.
     */
    public int size() {
        return list.size();
    }
}