package com.aegis.api.dto;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes para a classe RouteResponseDTO e seu record aninhado RouteStepDTO.
 * Como são records, os testes verificam o comportamento padrão gerado pelo compilador.
 */
class RouteResponseDTOTest {

    // Verifica se o construtor do record armazena os dados corretamente e se os métodos de acesso os retornam.
    // Este teste garante que a principal função do record (ser um portador de dados imutável) está funcionando.
    @Test
    void routeResponseDTO_shouldHoldAndReturnDataCorrectly() {
        // Arrange
        var step1 = new RouteResponseDTO.RouteStepDTO("Passo A", "1");
        var step2 = new RouteResponseDTO.RouteStepDTO("Passo B", "2");
        List<RouteResponseDTO.RouteStepDTO> route = List.of(step1, step2);
        int totalCost = 150;

        // Act
        var dto = new RouteResponseDTO(totalCost, route);

        // Assert
        assertEquals(totalCost, dto.totalCalculatedCost(), "O custo total calculado deve ser o mesmo que foi passado no construtor.");
        assertEquals(route, dto.route(), "A lista de passos da rota deve ser a mesma que foi passada no construtor.");
    }

    // Verifica se os métodos equals() e hashCode() gerados para RouteResponseDTO funcionam como esperado.
    // Dois records com os mesmos dados devem ser considerados iguais e ter o mesmo hashCode.
    @Test
    void routeResponseDTO_equalsAndHashCode_shouldBehaveAsExpected() {
        // Arrange
        var step1 = new RouteResponseDTO.RouteStepDTO("Passo A", "1");
        var step2 = new RouteResponseDTO.RouteStepDTO("Passo B", "2");
        List<RouteResponseDTO.RouteStepDTO> route1 = List.of(step1, step2);
        List<RouteResponseDTO.RouteStepDTO> route2 = List.of(step1, step2);
        List<RouteResponseDTO.RouteStepDTO> route3 = List.of(step1);

        var dto1 = new RouteResponseDTO(150, route1);
        var dto2 = new RouteResponseDTO(150, route2);
        var dto3 = new RouteResponseDTO(100, route3);

        // Act & Assert
        assertEquals(dto1, dto2, "Dois DTOs com os mesmos valores devem ser iguais.");
        assertEquals(dto1.hashCode(), dto2.hashCode(), "O hashCode de dois DTOs iguais deve ser o mesmo.");
        assertNotEquals(dto1, dto3, "Dois DTOs com valores diferentes não devem ser iguais.");
    }

    /**
     * Testes para o record aninhado RouteStepDTO.
     */
    @Nested
    class RouteStepDTOTest {

        // Verifica se o construtor do RouteStepDTO armazena os dados corretamente e se os métodos de acesso os retornam.
        @Test
        void routeStepDTO_shouldHoldAndReturnDataCorrectly() {
            // Arrange
            String name = "Passo Teste";
            String id = "s1";

            // Act
            var step = new RouteResponseDTO.RouteStepDTO(name, id);

            // Assert
            assertEquals(name, step.name(), "O nome deve ser o mesmo que foi passado no construtor.");
            assertEquals(id, step.id(), "O ID deve ser o mesmo que foi passado no construtor.");
        }

        // Verifica se os métodos equals() e hashCode() gerados para RouteStepDTO funcionam como esperado.
        @Test
        void routeStepDTO_equalsAndHashCode_shouldBehaveAsExpected() {
            // Arrange
            var step1 = new RouteResponseDTO.RouteStepDTO("Passo A", "1");
            var step2 = new RouteResponseDTO.RouteStepDTO("Passo A", "1");
            var step3 = new RouteResponseDTO.RouteStepDTO("Passo B", "2");

            // Act & Assert
            assertEquals(step1, step2, "Dois RouteStepDTOs com os mesmos valores devem ser iguais.");
            assertEquals(step1.hashCode(), step2.hashCode(), "O hashCode de dois RouteStepDTOs iguais deve ser o mesmo.");
            assertNotEquals(step1, step3, "Dois RouteStepDTOs com valores diferentes não devem ser iguais.");
        }
    }
}
