package com.aegis.core;

import com.aegis.core.datastructures.MyLinkedList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class MyLinkedListTest {

    private MyLinkedList<Integer> list;

    @BeforeEach
    void setUp() {
        list = new MyLinkedList<>();
    }

    @Test
    void testAddAndGet() {
        list.add(1);
        list.add(2);
        list.add(3);

        assertEquals(3, list.size()); // Verifica se o tamanho é 3
        assertEquals(1, list.get(0)); // Verifica se o primeiro elemento é 1
        assertEquals(2, list.get(1)); // Verifica se o segundo é 2
        assertEquals(3, list.get(2)); // Verifica se o terceiro é 3
    }

    @Test
    void testAddFirst() {
        list.add(1);
        list.addFirst(0);

        assertEquals(2, list.size());
        assertEquals(0, list.get(0));
        assertEquals(1, list.get(1));
    }

    @Test
    void testAddNullThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> list.add(null));
    }

    @Test
    void testAddFirstNullThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> list.addFirst(null));
    }


    @Test
    void testRemoveFirst() {
        list.add(0);
        list.add(1);

        Integer removed = list.removeFirst();

        assertEquals(0, removed);
        assertEquals(1, list.size());
        assertEquals(1, list.get(0));
    }

    @Test
    void testRemoveFirstWithSingleElement() {
        list.add(42);

        Integer removed = list.removeFirst();

        assertEquals(42, removed);
        assertTrue(list.isEmpty());
        assertEquals(0, list.size());
    }

    @Test
    void testRemoveFromMiddle() {
        list.add(10);
        list.add(20);
        list.add(30);

        Integer removed = list.remove(1);

        assertEquals(20, removed);
        assertEquals(2, list.size());
        assertEquals(10, list.get(0));
        assertEquals(30, list.get(1));
    }

    @Test
    void testRemoveLast() {
        list.add(10);
        list.add(20);

        Integer removed = list.remove(1);

        assertEquals(20, removed);
        assertEquals(1, list.size());
        assertEquals(10, list.get(0));
    }

    @Test
    void testIsEmptyAndSize() {
        assertTrue(list.isEmpty());
        assertEquals(0, list.size());

        list.add(1);

        assertFalse(list.isEmpty());
        assertEquals(1, list.size());
    }

    @Test
    void testGetThrowsExceptionOnOutOfBounds() {

        assertThrows(IndexOutOfBoundsException.class, () -> list.get(0));

        list.add(1);

        assertThrows(IndexOutOfBoundsException.class, () -> list.get(1));
    }

    @Test
    void testGetOptimizationFirstHalf() {
        for (int i = 0; i < 10; i++) {
            list.add(i);
        }

        assertEquals(2, list.get(2));
    }

    @Test
    void testGetOptimizationSecondHalf() {
        for (int i = 0; i < 10; i++) {
            list.add(i);
        }

        assertEquals(8, list.get(8));
    }


    @Test
    void testRemoveSingleElement() {
        list.add(42);

        Integer removed = list.remove(0);

        assertEquals(42, removed);
        assertTrue(list.isEmpty());
    }

    @Test
    void testRemoveFirstThrowsExceptionOnEmptyList() {

        assertThrows(NoSuchElementException.class, () -> list.removeFirst());
    }

    @Test
    void testRemoveThrowsExceptionOnInvalidIndex() {
        assertThrows(IndexOutOfBoundsException.class, () -> list.remove(0));

        list.add(1);

        assertThrows(IndexOutOfBoundsException.class, () -> list.remove(-1));

        assertThrows(IndexOutOfBoundsException.class, () -> list.remove(1));
    }
}
