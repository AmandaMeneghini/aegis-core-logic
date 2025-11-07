package com.aegis.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class MyMinHeapTest {

    private MyMinHeap<Integer> heap;

    @BeforeEach
    void setUp() {
        heap = new MyMinHeap<>();
    }

    @Test
    void testIsEmptyOnCreation() {
        assertTrue(heap.isEmpty());
        assertEquals(0, heap.size());
    }

    @Test
    void testInsertAndPeekMin() {
        heap.insert(10);
        assertEquals(1, heap.size());
        assertEquals(10, heap.peekMin());

        heap.insert(5);
        assertEquals(2, heap.size());
        assertEquals(5, heap.peekMin());

        heap.insert(15);
        assertEquals(3, heap.size());
        assertEquals(5, heap.peekMin());
    }

    @Test
    void testExtractMinMaintainsHeapProperty() {

        heap.insert(20);
        heap.insert(5);
        heap.insert(1);
        heap.insert(10);
        heap.insert(3);

        assertEquals(5, heap.size());

        assertEquals(1, heap.extractMin());
        assertEquals(3, heap.extractMin());
        assertEquals(5, heap.extractMin());
        assertEquals(10, heap.extractMin());
        assertEquals(20, heap.extractMin());

        assertTrue(heap.isEmpty());
    }

    @Test
    void testMultipleExtractionsWithReinsertions() {
        heap.insert(5);
        heap.insert(3);
        assertEquals(3, heap.extractMin());

        heap.insert(1);
        heap.insert(7);
        assertEquals(1, heap.extractMin());
        assertEquals(5, heap.extractMin());
        assertEquals(7, heap.extractMin());
        assertTrue(heap.isEmpty());
    }

    @Test
    void testHeapWithStrings() {
        MyMinHeap<String> stringHeap = new MyMinHeap<>();
        stringHeap.insert("zebra");
        stringHeap.insert("apple");
        stringHeap.insert("banana");

        assertEquals("apple", stringHeap.extractMin());
        assertEquals("banana", stringHeap.extractMin());
        assertEquals("zebra", stringHeap.extractMin());
    }


    @Test
    void testHeapGrowsBeyondCapacity() {
        for (int i = 1; i <= 11; i++) {
            heap.insert(i);
        }

        assertEquals(11, heap.size());
        assertEquals(1, heap.peekMin());
        assertEquals(1, heap.extractMin());
        assertEquals(2, heap.peekMin());
    }

    @Test
    void testExtractMinOnEmptyHeapThrowsException() {
        assertThrows(NoSuchElementException.class, () -> heap.extractMin());
    }

    @Test
    void testPeekMinOnEmptyHeapThrowsException() {
        assertThrows(NoSuchElementException.class, () -> heap.peekMin());
    }

    @Test
    void testInsertNullThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> heap.insert(null));
    }
}
