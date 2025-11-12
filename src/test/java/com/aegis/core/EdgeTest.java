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
    void testEdgeWithHighRiskWeight() {
        Vertex destination = new Vertex("V5", "Vertex 5");
        Edge edge = new Edge(destination, Integer.MAX_VALUE);

        assertEquals(destination, edge.getDestination());
        assertEquals(Integer.MAX_VALUE, edge.getCost());
    }
}
