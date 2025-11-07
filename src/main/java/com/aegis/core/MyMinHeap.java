package com.aegis.core;

import java.util.Arrays;
import java.util.NoSuchElementException;

/**
 * A custom implementation of a generic Min-Heap (Priority Queue).
 * This structure is essential for Dijkstra's algorithm.
 * It is built on top of a dynamic array.
 *
 * @param <T> The type of element, which MUST implement Comparable.
 */
public class MyMinHeap<T extends Comparable<T>> {

    private static final int DEFAULT_CAPACITY = 10;
    private Object[] heap; // Usamos Object[] para o array genérico
    private int size;

    public MyMinHeap() {
        this.heap = new Object[DEFAULT_CAPACITY];
        this.size = 0;
    }

    // --- Métodos Principais (Public API) ---

    /**
     * Adds a new element to the heap.
     * O(log n) time complexity.
     *
     * @param element The element to add.
     */
    public void insert(T element) {
        if (element == null) {
            throw new IllegalArgumentException("Cannot insert null element.");
        }
        ensureCapacity();
        heap[size] = element; // Adiciona no final
        size++;
        heapifyUp(size - 1); // Sobe o elemento para sua posição correta
    }

    /**
     * Removes and returns the smallest element from the heap (the root).
     * O(log n) time complexity.
     *
     * @return The smallest element.
     * @throws NoSuchElementException if the heap is empty.
     */
    @SuppressWarnings("unchecked")
    public T extractMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("Heap is empty.");
        }

        T minElement = (T) heap[0]; // O menor está sempre na raiz

        // Move o último elemento para a raiz
        heap[0] = heap[size - 1];
        heap[size - 1] = null; // Limpa a última posição
        size--;

        heapifyDown(0); // Desce o elemento da raiz para sua posição correta
        return minElement;
    }

    /**
     * Returns (but does not remove) the smallest element.
     * O(1) time complexity.
     *
     * @return The smallest element.
     * @throws NoSuchElementException if the heap is empty.
     */
    @SuppressWarnings("unchecked")
    public T peekMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("Heap is empty.");
        }
        return (T) heap[0];
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    @SuppressWarnings("unchecked")
    private void heapifyUp(int index) {
        while (index > 0) {
            int parentIndex = getParentIndex(index);

            T current = (T) heap[index];
            T parent = (T) heap[parentIndex];

            if (current.compareTo(parent) < 0) {
                swap(index, parentIndex);
                index = parentIndex;
            } else {
                break;
            }
        }
    }

    @SuppressWarnings("unchecked")
    private void heapifyDown(int index) {
        while (getLeftChildIndex(index) < size) {
            int leftChildIndex = getLeftChildIndex(index);
            int rightChildIndex = getRightChildIndex(index);
            int smallestChildIndex = leftChildIndex;

            // Verifica se o filho da direita existe e é menor
            if (rightChildIndex < size) {
                T leftChild = (T) heap[leftChildIndex];
                T rightChild = (T) heap[rightChildIndex];

                if (rightChild.compareTo(leftChild) < 0) {
                    smallestChildIndex = rightChildIndex;
                }
            }

            T current = (T) heap[index];
            T smallestChild = (T) heap[smallestChildIndex];

            if (current.compareTo(smallestChild) > 0) {
                swap(index, smallestChildIndex);
                index = smallestChildIndex;
            } else {
                break;
            }
        }
    }

    // --- Métodos Utilitários (Helpers) ---

    private void ensureCapacity() {
        if (size == heap.length) {
            heap = Arrays.copyOf(heap, heap.length * 2);
        }
    }

    private void swap(int i, int j) {
        Object temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }

    private int getParentIndex(int index) { return (index - 1) / 2; }
    private int getLeftChildIndex(int index) { return 2 * index + 1; }
    private int getRightChildIndex(int index) { return 2 * index + 2; }
}
