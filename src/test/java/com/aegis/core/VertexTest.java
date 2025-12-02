package com.aegis.core;

import com.aegis.core.graph.Edge;
import com.aegis.core.graph.Vertex;
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
        assertEquals(10, addedEdge.getCost());
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
        assertEquals(Integer.MAX_VALUE, vA.getEdges().get(0).getCost());
    }

    @Test
    void testAddEdgeWithZeroRiskWeight() {
        vA.addEdge(vB, 0);

        assertEquals(1, vA.getEdges().size());
        assertEquals(0, vA.getEdges().get(0).getCost());
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
    void testCompareToLessThan() {
        vA.tempMinRisk = 10;
        vB.tempMinRisk = 20;

        assertTrue(vA.compareTo(vB) < 0);
    }

    @Test
    void testCompareToGreaterThan() {
        vA.tempMinRisk = 30;
        vB.tempMinRisk = 15;

        assertTrue(vA.compareTo(vB) > 0);
    }

    @Test
    void testCompareToEqual() {
        vA.tempMinRisk = 25;
        vB.tempMinRisk = 25;

        assertEquals(0, vA.compareTo(vB));
    }

    @Test
    void testCompareToWithMaxValue() {
        vA.tempMinRisk = Integer.MAX_VALUE;
        vB.tempMinRisk = 100;

        assertTrue(vA.compareTo(vB) > 0);
    }

    @Test
    void testCompareToWithZeroValues() {
        vA.tempMinRisk = 0;
        vB.tempMinRisk = 0;

        assertEquals(0, vA.compareTo(vB));
    }

    @Test
    void testCompareToNegativeVsPositive() {
        vA.tempMinRisk = -5;
        vB.tempMinRisk = 10;

        assertTrue(vA.compareTo(vB) < 0);
    }

    @Test
    void testToStringWithoutEdges() {
        assertEquals("Vertex{id='A', name='Vertex A', edges=0}", vA.toString());
    }

    @Test
    void testToStringWithSingleEdge() {
        vA.addEdge(vB, 10);

        assertEquals("Vertex{id='A', name='Vertex A', edges=1}", vA.toString());
    }

    @Test
    void testToStringWithMultipleEdges() {
        Vertex vC = new Vertex("C", "Vertex C");
        vA.addEdge(vB, 10);
        vA.addEdge(vC, 20);

        assertEquals("Vertex{id='A', name='Vertex A', edges=2}", vA.toString());
    }

    @Test
    void testToStringWithSpecialCharactersInName() {
        Vertex vSpecial = new Vertex("X1", "Vertex's \"Name\" with <chars>");

        assertEquals("Vertex{id='X1', name='Vertex's \"Name\" with <chars>', edges=0}", vSpecial.toString());
    }

    @Test
    void testAddEdgeSelfLoopThrowsException() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> vA.addEdge(vA, 10)
        );

        assertEquals("Self-loops are not allowed.", exception.getMessage());
    }

    @Test
    void testEqualsNull() {
        assertNotEquals(vA, null);
    }

    @Test
    void testEqualsDifferentClass() {
        assertNotEquals(vA, "Not a Vertex");
        assertNotEquals(vA, Integer.valueOf(42));
        assertNotEquals(vA, new Object());
    }

}