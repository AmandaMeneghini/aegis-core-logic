package com.aegis.core;

import com.aegis.core.graph.Edge;
import com.aegis.core.graph.Vertex;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EdgeTest {

    @Test
    void testEdgeConstructorAndGetters() {
        Vertex sourceVertex = new Vertex("V1", "Vertex 1");
        Edge edge = new Edge(sourceVertex, 50);

        assertEquals(sourceVertex, edge.getDestination());
        assertEquals(50, edge.getCost());
    }

    @Test
    void testEdgeWithZeroRiskWeight() {
        Vertex destination = new Vertex("V2", "Vertex 2");
        Edge edge = new Edge(destination, 0);

        assertEquals(destination, edge.getDestination());
        assertEquals(0, edge.getCost());
    }

    @Test
    void testEdgeWithNegativeRiskWeight() {
        Vertex destination = new Vertex("V3", "Vertex 3");
        Edge edge = new Edge(destination, -10);

        assertEquals(destination, edge.getDestination());
        assertEquals(-10, edge.getCost());
    }

    @Test
    void testEdgeWithNullDestination() {
        assertDoesNotThrow(() -> {
            Edge edge = new Edge(null, 25);
            assertNull(edge.getDestination());
            assertEquals(25, edge.getCost());
        });
    }

    @Test
    void testToStringWithValidDestination() {
        Vertex destination = new Vertex("V1", "Vertex 1");
        Edge edge = new Edge(destination, 50);

        assertEquals("Edge{to=V1, cost=50}", edge.toString());
    }

    @Test
    void testToStringWithDifferentCostValues() {
        Vertex destination = new Vertex("V2", "Vertex 2");
        Edge edgeZero = new Edge(destination, 0);
        Edge edgeNegative = new Edge(destination, -10);
        Edge edgeMax = new Edge(destination, Integer.MAX_VALUE);

        assertEquals("Edge{to=V2, cost=0}", edgeZero.toString());
        assertEquals("Edge{to=V2, cost=-10}", edgeNegative.toString());
        assertEquals("Edge{to=V2, cost=" + Integer.MAX_VALUE + "}", edgeMax.toString());
    }

    @Test
    void testToStringWithNullDestination_ThrowsNPE() {
        Edge edge = new Edge(null, 25);

        assertThrows(NullPointerException.class, edge::toString);
    }


    @Test
    void testEdgeWithHighRiskWeight() {
        Vertex destination = new Vertex("V5", "Vertex 5");
        Edge edge = new Edge(destination, Integer.MAX_VALUE);

        assertEquals(destination, edge.getDestination());
        assertEquals(Integer.MAX_VALUE, edge.getCost());
    }
}
