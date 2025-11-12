package com.aegis.core.graph;

/**
 * Represents a connection (a route or street) between two vertices.
 * This edge is directed and weighted by risk.
 */
public class Edge {
    private final Vertex destination;
    private final int cost;
    public Vertex getDestination() {
        return destination;
    }

    public Edge(Vertex destination, int cost) {
        this.destination = destination;
        this.cost = cost;
    }

    public int getCost() {
        return this.cost;
    }

    @Override
    public String toString() {
        return "Edge{to=" + destination.getId() + ", cost=" + cost + '}';
    }
}

