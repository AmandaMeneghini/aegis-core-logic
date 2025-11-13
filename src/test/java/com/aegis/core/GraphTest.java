package com.aegis.core;

import com.aegis.core.graph.Graph;
import com.aegis.core.graph.Vertex;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GraphTest {

    private Graph graph;

    @BeforeEach
    void setUp() {
        graph = new Graph();
    }

    @Test
    void testAddVertexAndFindVertex() {
        assertTrue(graph.getVertices().isEmpty());

        graph.addVertex("A", "Agência A");
        graph.addVertex("B", "Banco B");

        assertEquals(2, graph.getVertices().size());

        Vertex foundA = graph.findVertex("A");
        assertNotNull(foundA);
        assertEquals("Agência A", foundA.getName());

        Vertex foundB = graph.findVertex("B");
        assertNotNull(foundB);

        assertNull(graph.findVertex("C"));
        assertNull(graph.findVertex(null));
    }

    @Test
    void testAddVertexThrowsOnDuplicateId() {

        graph.addVertex("A", "Agência A");

        assertThrows(IllegalArgumentException.class, () -> graph.addVertex("A", "Outra Agência A"));
    }

    @Test
    void testAddDirectedEdge() {
        graph.addVertex("A", "Origem");
        graph.addVertex("B", "Destino");
        graph.addDirectedEdge("A", "B", 25);

        Vertex vA = graph.findVertex("A");
        Vertex vB = graph.findVertex("B");

        assertEquals(1, vA.getEdges().size());
        assertEquals(vB, vA.getEdges().get(0).getDestination());
        assertEquals(25, vA.getEdges().get(0).getCost());

        assertEquals(0, vB.getEdges().size());
    }

    @Test
    void testAddDirectedEdgeValidation() {
        graph.addVertex("A", "Agência A");

        assertThrows(IllegalArgumentException.class, () -> graph.addDirectedEdge("A", "Z", 10));
        assertThrows(IllegalArgumentException.class, () -> graph.addDirectedEdge("Z", "A", 10));

        graph.addVertex("B", "Agência B");
        assertThrows(IllegalArgumentException.class, () -> graph.addDirectedEdge("A", "B", -5));
    }

    @Test
    void testAddUndirectedEdge() {
        graph.addVertex("A", "Casa");
        graph.addVertex("B", "Trabalho");
        graph.addUndirectedEdge("A", "B", 15);

        Vertex vA = graph.findVertex("A");
        Vertex vB = graph.findVertex("B");

        assertEquals(1, vA.getEdges().size());
        assertEquals(vB, vA.getEdges().get(0).getDestination());
        assertEquals(15, vA.getEdges().get(0).getCost());

        assertEquals(1, vB.getEdges().size());
        assertEquals(vA, vB.getEdges().get(0).getDestination());
        assertEquals(15, vB.getEdges().get(0).getCost());
    }
}