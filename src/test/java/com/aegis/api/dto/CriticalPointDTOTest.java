package com.aegis.api.dto;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;


class CriticalPointDTOTest {

    @Test
    void criticalPointDTO_shouldHoldAndReturnDataCorrectly() {
        var point1 = new CriticalPointDTO.PointDTO("Ponto A", "1");
        var point2 = new CriticalPointDTO.PointDTO("Ponto B", "2");
        List<CriticalPointDTO.PointDTO> points = List.of(point1, point2);
        int criticalPointsFound = points.size();
        var dto = new CriticalPointDTO(criticalPointsFound, points);

        assertEquals(criticalPointsFound, dto.criticalPointsFound(), "O número de pontos críticos deve ser o mesmo que foi passado no construtor.");
        assertEquals(points, dto.points(), "A lista de pontos deve ser a mesma que foi passada no construtor.");
    }

    @Test
    void criticalPointDTO_equalsAndHashCode_shouldBehaveAsExpected() {
        var point1 = new CriticalPointDTO.PointDTO("Ponto A", "1");
        var point2 = new CriticalPointDTO.PointDTO("Ponto B", "2");
        List<CriticalPointDTO.PointDTO> points1 = List.of(point1, point2);
        List<CriticalPointDTO.PointDTO> points2 = List.of(point1, point2);
        List<CriticalPointDTO.PointDTO> points3 = List.of(point1);

        var dto1 = new CriticalPointDTO(points1.size(), points1);
        var dto2 = new CriticalPointDTO(points2.size(), points2);
        var dto3 = new CriticalPointDTO(points3.size(), points3);

        assertEquals(dto1, dto2, "Dois DTOs com os mesmos valores devem ser iguais.");
        assertEquals(dto1.hashCode(), dto2.hashCode(), "O hashCode de dois DTOs iguais deve ser o mesmo.");
        assertNotEquals(dto1, dto3, "Dois DTOs com valores diferentes não devem ser iguais.");
    }


    @Nested
    class PointDTOTest {

        @Test
        void pointDTO_shouldHoldAndReturnDataCorrectly() {

            String name = "Ponto Teste";
            String id = "p1";

            var point = new CriticalPointDTO.PointDTO(name, id);

            assertEquals(name, point.name(), "O nome deve ser o mesmo que foi passado no construtor.");
            assertEquals(id, point.id(), "O ID deve ser o mesmo que foi passado no construtor.");
        }

        @Test
        void pointDTO_equalsAndHashCode_shouldBehaveAsExpected() {
            var point1 = new CriticalPointDTO.PointDTO("Ponto A", "1");
            var point2 = new CriticalPointDTO.PointDTO("Ponto A", "1");
            var point3 = new CriticalPointDTO.PointDTO("Ponto B", "2");

            assertEquals(point1, point2, "Dois PointDTOs com os mesmos valores devem ser iguais.");
            assertEquals(point1.hashCode(), point2.hashCode(), "O hashCode de dois PointDTOs iguais deve ser o mesmo.");
            assertNotEquals(point1, point3, "Dois PointDTOs com valores diferentes não devem ser iguais.");
        }
    }
}
