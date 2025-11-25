package com.aegis.api.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class VertexEntityTest {

    // Verifica se o construtor inicializa corretamente as propriedades e se os getters retornam os valores esperados.
    // Este é o teste principal para garantir a integridade dos dados da entidade.
    @Test
    void constructor_shouldSetPropertiesCorrectly() {
        // Arrange
        String id = "1";
        String name = "Vertex A";

        // Act
        VertexEntity vertex = new VertexEntity(id, name);

        // Assert
        assertEquals(id, vertex.getId(), "O ID deve ser '1'.");
        assertEquals(name, vertex.getName(), "O nome deve ser 'Vertex A'.");
    }

    // Verifica o comportamento da entidade com valores de string vazios para id e nome.
    // Este teste de caso de borda confirma que a entidade armazena os valores como fornecidos, sem realizar validações.
    @Test
    void constructor_shouldHandleEmptyStrings() {
        // Arrange
        String id = "";
        String name = "";

        // Act
        VertexEntity vertex = new VertexEntity(id, name);

        // Assert
        assertEquals(id, vertex.getId(), "O ID deve ser uma string vazia.");
        assertEquals(name, vertex.getName(), "O nome deve ser uma string vazia.");
    }

    // Verifica o comportamento da entidade com valores nulos para id e nome.
    // Este teste garante que a entidade pode conter valores nulos, refletindo que não há validação de não nulidade no construtor.
    @Test
    void constructor_shouldHandleNullValues() {
        // Arrange
        String id = null;
        String name = null;

        // Act
        VertexEntity vertex = new VertexEntity(id, name);

        // Assert
        assertEquals(id, vertex.getId(), "O ID deve ser nulo.");
        assertEquals(name, vertex.getName(), "O nome deve ser nulo.");
    }
}
