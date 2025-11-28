package com.aegis.api.dto;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;


class RouteResponseDTOTest {

    @Test
    void routeResponseDTO_shouldHoldAndReturnDataCorrectly() {
        var step1 = new RouteResponseDTO.RouteStepDTO("Passo A", "1");
        var step2 = new RouteResponseDTO.RouteStepDTO("Passo B", "2");
        List<RouteResponseDTO.RouteStepDTO> route = List.of(step1, step2);
        int totalCost = 150;
        var dto = new RouteResponseDTO(totalCost, route);

        assertEquals(totalCost, dto.totalCalculatedCost(), "O custo total calculado deve ser o mesmo que foi passado no construtor.");
        assertEquals(route, dto.route(), "A lista de passos da rota deve ser a mesma que foi passada no construtor.");
    }

    @Test
    void routeResponseDTO_equalsAndHashCode_shouldBehaveAsExpected() {
        var step1 = new RouteResponseDTO.RouteStepDTO("Passo A", "1");
        var step2 = new RouteResponseDTO.RouteStepDTO("Passo B", "2");
        List<RouteResponseDTO.RouteStepDTO> route1 = List.of(step1, step2);
        List<RouteResponseDTO.RouteStepDTO> route2 = List.of(step1, step2);
        List<RouteResponseDTO.RouteStepDTO> route3 = List.of(step1);

        var dto1 = new RouteResponseDTO(150, route1);
        var dto2 = new RouteResponseDTO(150, route2);
        var dto3 = new RouteResponseDTO(100, route3);

        assertEquals(dto1, dto2, "Dois DTOs com os mesmos valores devem ser iguais.");
        assertEquals(dto1.hashCode(), dto2.hashCode(), "O hashCode de dois DTOs iguais deve ser o mesmo.");
        assertNotEquals(dto1, dto3, "Dois DTOs com valores diferentes não devem ser iguais.");
    }

    @Nested
    class RouteStepDTOTest {

        @Test
        void routeStepDTO_shouldHoldAndReturnDataCorrectly() {
            String name = "Passo Teste";
            String id = "s1";

            var step = new RouteResponseDTO.RouteStepDTO(name, id);

            assertEquals(name, step.name(), "O nome deve ser o mesmo que foi passado no construtor.");
            assertEquals(id, step.id(), "O ID deve ser o mesmo que foi passado no construtor.");
        }

        @Test
        void routeStepDTO_equalsAndHashCode_shouldBehaveAsExpected() {
            var step1 = new RouteResponseDTO.RouteStepDTO("Passo A", "1");
            var step2 = new RouteResponseDTO.RouteStepDTO("Passo A", "1");
            var step3 = new RouteResponseDTO.RouteStepDTO("Passo B", "2");

            assertEquals(step1, step2, "Dois RouteStepDTOs com os mesmos valores devem ser iguais.");
            assertEquals(step1.hashCode(), step2.hashCode(), "O hashCode de dois RouteStepDTOs iguais deve ser o mesmo.");
            assertNotEquals(step1, step3, "Dois RouteStepDTOs com valores diferentes não devem ser iguais.");
        }
    }
}
