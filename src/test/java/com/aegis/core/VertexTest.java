package com.aegis.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class VertexTest {

    private Vertex vA;
    private Vertex vB;

    @BeforeEach
    void setUp() {
        vA = new Vertex("A", "Vertex A");
        vB = new Vertex("B", "Vertex B");
    }

    @Test
    void testVertexConstructor() {
        assertEquals("A", vA.getId());
        assertEquals("Vertex A", vA.getName());
        assertNotNull(vA.getEdges());
        assertEquals(0, vA.getEdges().size());
    }

    @Test
    void testConstructorThrowsOnNullOrEmptyId() {
        assertThrows(IllegalArgumentException.class, () -> new Vertex(null, "Null ID"));
        assertThrows(IllegalArgumentException.class, () -> new Vertex("", "Empty ID"));
    }

    @Test
    void testAddEdge() {
        vA.addEdge(vB, 10);

        assertEquals(1, vA.getEdges().size());
        Edge addedEdge = vA.getEdges().get(0);

        assertEquals(vB, addedEdge.getDestination());
        assertEquals(10, addedEdge.getRiskWeight());
    }

    @Test
    void testAddMultipleEdges() {
        Vertex vC = new Vertex("C", "Vertex C");

        vA.addEdge(vB, 10);
        vA.addEdge(vC, 20);

        assertEquals(2, vA.getEdges().size());

        assertEquals(vB, vA.getEdges().get(0).getDestination());
        assertEquals(vC, vA.getEdges().get(1).getDestination());
    }

    @Test
    void testAddDuplicateEdge() {
        vA.addEdge(vB, 10);
        vA.addEdge(vB, 20);

        assertEquals(2, vA.getEdges().size());
    }

    @Test
    void testAddEdgeValidation() {
        assertThrows(IllegalArgumentException.class, () -> vA.addEdge(vB, -1));
        assertThrows(IllegalArgumentException.class, () -> vA.addEdge(null, 10));
    }

    @Test
    void testAddEdgeWithMaxRiskWeight() {
        vA.addEdge(vB, Integer.MAX_VALUE);

        assertEquals(1, vA.getEdges().size());
        assertEquals(Integer.MAX_VALUE, vA.getEdges().get(0).getRiskWeight());
    }

    @Test
    void testAddEdgeWithZeroRiskWeight() {
        vA.addEdge(vB, 0);

        assertEquals(1, vA.getEdges().size());
        assertEquals(0, vA.getEdges().get(0).getRiskWeight());
    }

    @Test
    void testEqualsAndHashCode() {
        Vertex vA_copy = new Vertex("A", "Vertex A Copy");
        Vertex vC = new Vertex("C", "Vertex C");

        assertEquals(vA, vA_copy);
        assertNotEquals(vA, vB);
        assertNotEquals(null, vA);
        assertNotEquals(new Object(), vA);

        assertEquals(vA.hashCode(), vA_copy.hashCode());
        assertNotEquals(vA.hashCode(), vB.hashCode());
    }

    @Test
    void testToString() {
        String expected = "Vertex{id='A', name='Vertex A', edges=0}";
        assertEquals(expected, vA.toString());

        vA.addEdge(vB, 10);
        String expectedWithEdge = "Vertex{id='A', name='Vertex A', edges=1}";
        assertEquals(expectedWithEdge, vA.toString());
    }

}