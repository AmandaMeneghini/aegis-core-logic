package com.aegis.core;

import com.aegis.core.datastructures.MyStack;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class MyStackTest {

    private MyStack<Integer> stack;

    @BeforeEach
    void setUp() {
        stack = new MyStack<>();
    }

    @Test
    void testNewStackIsEmpty() {
        assertTrue(stack.isEmpty());
        assertEquals(0, stack.size());
    }

    @Test
    void testPushAddsElement() {
        stack.push(10);

        assertFalse(stack.isEmpty());
        assertEquals(1, stack.size());
        assertEquals(10, stack.peek());
    }

    @Test
    void testPushMultipleElements() {
        stack.push(10);
        stack.push(20);
        stack.push(30);

        assertEquals(3, stack.size());
        assertEquals(30, stack.peek());
    }

    @Test
    void testPeekDoesNotRemoveElement() {
        stack.push(42);

        Integer peeked1 = stack.peek();
        Integer peeked2 = stack.peek();

        assertEquals(42, peeked1);
        assertEquals(42, peeked2);
        assertEquals(1, stack.size());
        assertFalse(stack.isEmpty());
    }

    @Test
    void testPopRemovesAndReturnsTopElement() {
        stack.push(10);
        stack.push(20);

        Integer popped = stack.pop();

        assertEquals(20, popped);
        assertEquals(1, stack.size());
        assertEquals(10, stack.peek());
    }

    @Test
    void testPopMultipleElementsLIFO() {
        stack.push(10);
        stack.push(20);
        stack.push(30);

        assertEquals(30, stack.pop());
        assertEquals(20, stack.pop());
        assertEquals(10, stack.pop());
        assertTrue(stack.isEmpty());
    }

    @Test
    void testPopUntilEmpty() {
        stack.push(1);
        stack.push(2);
        stack.push(3);

        while (!stack.isEmpty()) {
            stack.pop();
        }

        assertTrue(stack.isEmpty());
        assertEquals(0, stack.size());
    }

    @Test
    void testPeekThrowsExceptionOnEmptyStack() {
        assertThrows(NoSuchElementException.class, () -> stack.peek());
    }

    @Test
    void testPopThrowsExceptionOnEmptyStack() {
        assertThrows(NoSuchElementException.class, () -> stack.pop());
    }

    @Test
    void testPushAndPopIntercalated() {
        stack.push(10);
        assertEquals(10, stack.peek());

        stack.push(20);
        assertEquals(20, stack.peek());

        assertEquals(20, stack.pop());
        assertEquals(10, stack.peek());

        stack.push(30);
        assertEquals(30, stack.peek());

        assertEquals(30, stack.pop());
        assertEquals(10, stack.pop());
        assertTrue(stack.isEmpty());
    }

    @Test
    void testStackWithSingleElement() {
        stack.push(42);

        assertEquals(42, stack.peek());
        assertEquals(42, stack.pop());
        assertTrue(stack.isEmpty());
    }

    @Test
    void testStackWithStrings() {
        MyStack<String> stringStack = new MyStack<>();

        stringStack.push("A");
        stringStack.push("B");
        stringStack.push("C");

        assertEquals("C", stringStack.pop());
        assertEquals("B", stringStack.pop());
        assertEquals("A", stringStack.pop());
        assertTrue(stringStack.isEmpty());
    }

    @Test
    void testSizeAfterOperations() {
        assertEquals(0, stack.size());

        stack.push(1);
        assertEquals(1, stack.size());

        stack.push(2);
        assertEquals(2, stack.size());

        stack.pop();
        assertEquals(1, stack.size());

        stack.pop();
        assertEquals(0, stack.size());
    }

    @Test
    void testIsEmptyAfterOperations() {
        assertTrue(stack.isEmpty());

        stack.push(10);
        assertFalse(stack.isEmpty());

        stack.pop();
        assertTrue(stack.isEmpty());
    }
}