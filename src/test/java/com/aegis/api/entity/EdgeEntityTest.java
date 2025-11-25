package com.aegis.api.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class EdgeEntityTest {

    // Verifica se o construtor inicializa corretamente as propriedades e se os getters retornam os valores esperados.
    // Este é o teste principal para garantir a integridade dos dados da entidade.
    @Test
    void constructor_shouldSetPropertiesCorrectly() {
        // Arrange
        String originId = "A";
        String destId = "B";
        int risk = 5;
        int distance = 100;

        // Act
        EdgeEntity edge = new EdgeEntity(originId, destId, risk, distance);

        // Assert
        assertEquals(originId, edge.getOriginId(), "O ID de origem deve ser 'A'.");
        assertEquals(destId, edge.getDestId(), "O ID de destino deve ser 'B'.");
        assertEquals(risk, edge.getRisk(), "O risco deve ser 5.");
        assertEquals(distance, edge.getDistance(), "A distância deve ser 100.");
    }

    // Verifica se a entidade pode ser criada com valores zero para risco e distância.
    // Este é um teste de caso de borda para garantir que valores zero, que são entradas válidas, sejam tratados corretamente.
    @Test
    void constructor_shouldHandleZeroValues() {
        // Arrange
        String originId = "C";
        String destId = "D";
        int risk = 0;
        int distance = 0;

        // Act
        EdgeEntity edge = new EdgeEntity(originId, destId, risk, distance);

        // Assert
        assertEquals(risk, edge.getRisk(), "O risco deve ser 0.");
        assertEquals(distance, edge.getDistance(), "A distância deve ser 0.");
    }

    // Verifica o comportamento da entidade com valores de string vazios.
    // Embora não seja ideal, o teste confirma que a entidade armazena os valores como fornecidos, sem validação.
    @Test
    void constructor_shouldHandleEmptyStringIds() {
        // Arrange
        String originId = "";
        String destId = "";
        int risk = 1;
        int distance = 10;

        // Act
        EdgeEntity edge = new EdgeEntity(originId, destId, risk, distance);

        // Assert
        assertEquals(originId, edge.getOriginId(), "O ID de origem deve ser uma string vazia.");
        assertEquals(destId, edge.getDestId(), "O ID de destino deve ser uma string vazia.");
    }
}
