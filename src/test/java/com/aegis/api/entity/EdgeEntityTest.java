package com.aegis.api.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class EdgeEntityTest {

    @Test
    void constructor_shouldSetPropertiesCorrectly() {
        String originId = "A";
        String destId = "B";
        int risk = 5;
        int distance = 100;

        EdgeEntity edge = new EdgeEntity(originId, destId, risk, distance);

        assertEquals(originId, edge.getOriginId(), "O ID de origem deve ser 'A'.");
        assertEquals(destId, edge.getDestId(), "O ID de destino deve ser 'B'.");
        assertEquals(risk, edge.getRisk(), "O risco deve ser 5.");
        assertEquals(distance, edge.getDistance(), "A distância deve ser 100.");
    }

    @Test
    void constructor_shouldHandleZeroValues() {
        String originId = "C";
        String destId = "D";
        int risk = 0;
        int distance = 0;

        EdgeEntity edge = new EdgeEntity(originId, destId, risk, distance);

        assertEquals(risk, edge.getRisk(), "O risco deve ser 0.");
        assertEquals(distance, edge.getDistance(), "A distância deve ser 0.");
    }

    @Test
    void constructor_shouldHandleEmptyStringIds() {
        String originId = "";
        String destId = "";
        int risk = 1;
        int distance = 10;

        EdgeEntity edge = new EdgeEntity(originId, destId, risk, distance);

        assertEquals(originId, edge.getOriginId(), "O ID de origem deve ser uma string vazia.");
        assertEquals(destId, edge.getDestId(), "O ID de destino deve ser uma string vazia.");
    }
}
