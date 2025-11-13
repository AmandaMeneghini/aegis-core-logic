package com.aegis.api.entity;

/**
 * Entity representing a Vertex in the data layer.
 * Separates data representation from domain model.
 */
public class VertexEntity {

    private final String id;
    private final String name;

    public VertexEntity(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}

