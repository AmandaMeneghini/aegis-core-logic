package com.aegis.api.dto;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes para a classe CriticalPointDTO e seu record aninhado PointDTO.
 * Como são records, os testes verificam o comportamento padrão gerado pelo compilador.
 */
class CriticalPointDTOTest {

    // Verifica se o construtor do record armazena os dados corretamente e se os métodos de acesso os retornam.
    // Este teste garante que a principal função do record (ser um portador de dados imutável) está funcionando.
    @Test
    void criticalPointDTO_shouldHoldAndReturnDataCorrectly() {
        // Arrange
        var point1 = new CriticalPointDTO.PointDTO("Ponto A", "1");
        var point2 = new CriticalPointDTO.PointDTO("Ponto B", "2");
        List<CriticalPointDTO.PointDTO> points = List.of(point1, point2);
        int criticalPointsFound = points.size();

        // Act
        var dto = new CriticalPointDTO(criticalPointsFound, points);

        // Assert
        assertEquals(criticalPointsFound, dto.criticalPointsFound(), "O número de pontos críticos deve ser o mesmo que foi passado no construtor.");
        assertEquals(points, dto.points(), "A lista de pontos deve ser a mesma que foi passada no construtor.");
    }

    // Verifica se os métodos equals() e hashCode() gerados para CriticalPointDTO funcionam como esperado.
    // Dois records com os mesmos dados devem ser considerados iguais e ter o mesmo hashCode.
    @Test
    void criticalPointDTO_equalsAndHashCode_shouldBehaveAsExpected() {
        // Arrange
        var point1 = new CriticalPointDTO.PointDTO("Ponto A", "1");
        var point2 = new CriticalPointDTO.PointDTO("Ponto B", "2");
        List<CriticalPointDTO.PointDTO> points1 = List.of(point1, point2);
        List<CriticalPointDTO.PointDTO> points2 = List.of(point1, point2);
        List<CriticalPointDTO.PointDTO> points3 = List.of(point1);

        var dto1 = new CriticalPointDTO(points1.size(), points1);
        var dto2 = new CriticalPointDTO(points2.size(), points2);
        var dto3 = new CriticalPointDTO(points3.size(), points3);

        // Act & Assert
        assertEquals(dto1, dto2, "Dois DTOs com os mesmos valores devem ser iguais.");
        assertEquals(dto1.hashCode(), dto2.hashCode(), "O hashCode de dois DTOs iguais deve ser o mesmo.");
        assertNotEquals(dto1, dto3, "Dois DTOs com valores diferentes não devem ser iguais.");
    }

    /**
     * Testes para o record aninhado PointDTO.
     */
    @Nested
    class PointDTOTest {

        // Verifica se o construtor do PointDTO armazena os dados corretamente e se os métodos de acesso os retornam.
        @Test
        void pointDTO_shouldHoldAndReturnDataCorrectly() {
            // Arrange
            String name = "Ponto Teste";
            String id = "p1";

            // Act
            var point = new CriticalPointDTO.PointDTO(name, id);

            // Assert
            assertEquals(name, point.name(), "O nome deve ser o mesmo que foi passado no construtor.");
            assertEquals(id, point.id(), "O ID deve ser o mesmo que foi passado no construtor.");
        }

        // Verifica se os métodos equals() e hashCode() gerados para PointDTO funcionam como esperado.
        @Test
        void pointDTO_equalsAndHashCode_shouldBehaveAsExpected() {
            // Arrange
            var point1 = new CriticalPointDTO.PointDTO("Ponto A", "1");
            var point2 = new CriticalPointDTO.PointDTO("Ponto A", "1");

            var point3 = new CriticalPointDTO.PointDTO("Ponto B", "2");

            // Act & Assert
            assertEquals(point1, point2, "Dois PointDTOs com os mesmos valores devem ser iguais.");
            assertEquals(point1.hashCode(), point2.hashCode(), "O hashCode de dois PointDTOs iguais deve ser o mesmo.");
            assertNotEquals(point1, point3, "Dois PointDTOs com valores diferentes não devem ser iguais.");
        }
    }
}
