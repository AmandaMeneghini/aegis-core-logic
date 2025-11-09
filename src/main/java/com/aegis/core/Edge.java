package com.aegis.core;

/**
 * Represents a connection (a route or street) between two vertices.
 * This edge is directed and weighted by risk.
 */
public class Edge {
    private final Vertex destination;
    private final int riskWeight;

    public Edge(Vertex destination, int riskWeight) {
        this.destination = destination;
        this.riskWeight = riskWeight;
    }

    public Vertex getDestination() {
        return destination;
    }

    public int getRiskWeight() {
        return riskWeight;
    }

    @Override
    public String toString() {
        return "Edge{to=" + destination.getId() + ", risk=" + riskWeight + '}';
    }
}

