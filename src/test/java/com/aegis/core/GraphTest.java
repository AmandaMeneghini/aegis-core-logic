package com.aegis.core;

import com.aegis.core.datastructures.MyLinkedList;
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

    @Test
    void testAddVertexWithNullId() {
        assertThrows(IllegalArgumentException.class, () -> graph.addVertex(null, "Nome"));
    }

    @Test
    void testGetVertices() {
        assertTrue(graph.getVertices().isEmpty());

        graph.addVertex("A", "Vertex A");
        graph.addVertex("B", "Vertex B");

        assertEquals(2, graph.getVertices().size());
    }

    @Test
    void testFindSafestRoute_ValidPath() {
        graph.addVertex("A", "Start");
        graph.addVertex("B", "Middle");
        graph.addVertex("C", "End");
        graph.addDirectedEdge("A", "B", 10);
        graph.addDirectedEdge("B", "C", 5);

        MyLinkedList<Vertex> path = graph.findSafestRoute("A", "C");

        assertEquals(3, path.size());
        assertEquals("A", path.get(0).getId());
        assertEquals("B", path.get(1).getId());
        assertEquals("C", path.get(2).getId());
    }

    @Test
    void testFindSafestRoute_NoPath() {
        graph.addVertex("A", "Start");
        graph.addVertex("B", "Isolated");

        MyLinkedList<Vertex> path = graph.findSafestRoute("A", "B");

        assertTrue(path.isEmpty());
    }

    @Test
    void testFindSafestRoute_InvalidOrigin() {
        graph.addVertex("A", "Start");

        assertThrows(IllegalArgumentException.class,
                () -> graph.findSafestRoute("Z", "A"));
    }

    @Test
    void testFindSafestRoute_InvalidDestination() {
        graph.addVertex("A", "Start");

        assertThrows(IllegalArgumentException.class,
                () -> graph.findSafestRoute("A", "Z"));
    }

    @Test
    void testFindSafestRoute_MultiplePathsChoosesShortest() {
        graph.addVertex("A", "Start");
        graph.addVertex("B", "Middle1");
        graph.addVertex("C", "Middle2");
        graph.addVertex("D", "End");

        graph.addDirectedEdge("A", "B", 5);
        graph.addDirectedEdge("B", "D", 10);
        graph.addDirectedEdge("A", "C", 20);
        graph.addDirectedEdge("C", "D", 1);

        MyLinkedList<Vertex> path = graph.findSafestRoute("A", "D");

        assertEquals(3, path.size());
        assertEquals("A", path.get(0).getId());
        assertEquals("B", path.get(1).getId());
        assertEquals("D", path.get(2).getId());
    }

    @Test
    void testFindSafestRoute_SameOriginAndDestination() {
        graph.addVertex("A", "Location");

        MyLinkedList<Vertex> path = graph.findSafestRoute("A", "A");

        assertEquals(1, path.size());
        assertEquals("A", path.get(0).getId());
    }

    @Test
    void testFindCriticalPoints_SimpleGraph() {
        graph.addVertex("A", "Node A");
        graph.addVertex("B", "Node B");
        graph.addVertex("C", "Node C");

        graph.addUndirectedEdge("A", "B", 1);
        graph.addUndirectedEdge("B", "C", 1);

        MyLinkedList<Vertex> criticalPoints = graph.findCriticalPoints();

        assertEquals(1, criticalPoints.size());
        assertEquals("B", criticalPoints.get(0).getId());
    }

    @Test
    void testFindCriticalPoints_NoArticulationPoints() {
        graph.addVertex("A", "Node A");
        graph.addVertex("B", "Node B");
        graph.addVertex("C", "Node C");

        graph.addUndirectedEdge("A", "B", 1);
        graph.addUndirectedEdge("B", "C", 1);
        graph.addUndirectedEdge("C", "A", 1);

        MyLinkedList<Vertex> criticalPoints = graph.findCriticalPoints();

        assertTrue(criticalPoints.isEmpty());
    }

    @Test
    void testFindCriticalPoints_DisconnectedGraph() {
        graph.addVertex("A", "Component1");
        graph.addVertex("B", "Component2");

        MyLinkedList<Vertex> criticalPoints = graph.findCriticalPoints();

        assertTrue(criticalPoints.isEmpty());
    }

    @Test
    void testFindCriticalPoints_RootWithMultipleChildren() {
        graph.addVertex("A", "Root");
        graph.addVertex("B", "Child1");
        graph.addVertex("C", "Child2");
        graph.addVertex("D", "Leaf");

        graph.addUndirectedEdge("A", "B", 1);
        graph.addUndirectedEdge("A", "C", 1);
        graph.addUndirectedEdge("B", "D", 1);

        MyLinkedList<Vertex> criticalPoints = graph.findCriticalPoints();

        assertTrue(criticalPoints.size() >= 1);
        boolean hasA = false;
        for (int i = 0; i < criticalPoints.size(); i++) {
            if ("A".equals(criticalPoints.get(i).getId())) {
                hasA = true;
                break;
            }
        }
        assertTrue(hasA);
    }

    @Test
    void testAddDirectedEdge_SelfLoop() {
        graph.addVertex("A", "Node A");

        assertThrows(IllegalArgumentException.class,
                () -> graph.addDirectedEdge("A", "A", 10));
    }

    @Test
    void testAddUndirectedEdge_InvalidVertices() {
        graph.addVertex("A", "Node A");

        assertThrows(IllegalArgumentException.class,
                () -> graph.addUndirectedEdge("A", "Z", 10));
    }

    @Test
    void testMultipleDijkstraExecutions() {
        graph.addVertex("A", "Start");
        graph.addVertex("B", "Middle");
        graph.addVertex("C", "End");
        graph.addDirectedEdge("A", "B", 5);
        graph.addDirectedEdge("B", "C", 10);

        MyLinkedList<Vertex> path1 = graph.findSafestRoute("A", "C");
        assertEquals(3, path1.size());

        MyLinkedList<Vertex> path2 = graph.findSafestRoute("A", "B");
        assertEquals(2, path2.size());
    }

    @Test
    void testFindCriticalPoints_ComplexGraph() {
        graph.addVertex("A", "Node A");
        graph.addVertex("B", "Node B");
        graph.addVertex("C", "Node C");
        graph.addVertex("D", "Node D");
        graph.addVertex("E", "Node E");

        graph.addUndirectedEdge("A", "B", 1);
        graph.addUndirectedEdge("B", "C", 1);
        graph.addUndirectedEdge("C", "D", 1);
        graph.addUndirectedEdge("D", "E", 1);
        graph.addUndirectedEdge("B", "D", 1);

        MyLinkedList<Vertex> criticalPoints = graph.findCriticalPoints();

        assertFalse(criticalPoints.isEmpty());
    }

    @Test
    void testFindSafestRoute_SkipsWorsePathToSameVertex() {
        graph.addVertex("A", "Start");
        graph.addVertex("B", "Middle1");
        graph.addVertex("C", "Middle2");
        graph.addVertex("D", "End");

        graph.addDirectedEdge("A", "B", 1);
        graph.addDirectedEdge("B", "D", 1);

        graph.addDirectedEdge("A", "C", 2);
        graph.addDirectedEdge("C", "D", 100);

        MyLinkedList<Vertex> path = graph.findSafestRoute("A", "D");

        assertEquals(3, path.size());
        assertEquals("A", path.get(0).getId());
        assertEquals("B", path.get(1).getId());
        assertEquals("D", path.get(2).getId());
    }



}