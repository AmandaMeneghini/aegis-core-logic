package com.aegis.api.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class VertexEntityTest {

    @Test
    void constructor_shouldSetPropertiesCorrectly() {
        String id = "1";
        String name = "Vertex A";

        VertexEntity vertex = new VertexEntity(id, name);

        assertEquals(id, vertex.getId(), "O ID deve ser '1'.");
        assertEquals(name, vertex.getName(), "O nome deve ser 'Vertex A'.");
    }

    @Test
    void constructor_shouldHandleEmptyStrings() {
        String id = "";
        String name = "";

        VertexEntity vertex = new VertexEntity(id, name);

        assertEquals(id, vertex.getId(), "O ID deve ser uma string vazia.");
        assertEquals(name, vertex.getName(), "O nome deve ser uma string vazia.");
    }

    @Test
    void constructor_shouldHandleNullValues() {
        String id = null;
        String name = null;

        VertexEntity vertex = new VertexEntity(id, name);

        assertEquals(id, vertex.getId(), "O ID deve ser nulo.");
        assertEquals(name, vertex.getName(), "O nome deve ser nulo.");
    }
}
