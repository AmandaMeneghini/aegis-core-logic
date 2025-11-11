package com.aegis.core;

/**
 * Represents a single node (or location) in the graph.
 * A Vertex holds its own list of outgoing edges (routes).
 */

public class Vertex implements Comparable<Vertex> {

    private final String id;
    private final String name;
    private MyLinkedList<Edge> edges;

    public int tempMinRisk = Integer.MAX_VALUE;
    public Vertex tempPrevious = null;
    public boolean isVisited = false;
    public int dfsNum = -1;
    public int lowLink = -1;
    public Vertex parent = null;
    public boolean tempIsArticulationPoint = false;

    public Vertex(String id, String name) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("Vertex ID cannot be null or empty.");
        }
        this.id = id;
        this.name = name;
        this.edges = new MyLinkedList<>();
    }

    /**
     * Adds an outgoing edge (a route) from this vertex to another.
     * Time Complexity: O(1) for adding to the list.
     *
     * @param destination The vertex this edge leads to.
     * @param cost  The cost/risk of taking this route.
     * @throws IllegalArgumentException if destination is null or cost is negative.
     */
    public void addEdge(Vertex destination, int cost) {
        if (destination == null) {
            throw new IllegalArgumentException("Destination vertex cannot be null.");
        }
        if (cost < 0) {
            throw new IllegalArgumentException("Risk weight cannot be negative.");
        }
        Edge newEdge = new Edge(destination, cost);
        this.edges.add(newEdge);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public MyLinkedList<Edge> getEdges() {
        return this.edges;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Vertex vertex = (Vertex) obj;
        return id.equals(vertex.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return String.format("Vertex{id='%s', name='%s', edges=%d}",
                id, name, edges.size());
    }

    /**
     * Compares this Vertex to another, based on the
     * 'tempMinRisk' (temporary minimum risk).
     * Essential for MyMinHeap to function.
     */
    @Override
    public int compareTo(Vertex other) {
        return Integer.compare(this.tempMinRisk, other.tempMinRisk);
    }
}
