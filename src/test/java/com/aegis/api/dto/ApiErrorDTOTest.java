package com.aegis.api.dto;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;


class ApiErrorDTOTest {

    @Test
    void record_shouldHoldAndReturnDataCorrectly() {

        String message = "Erro de teste";
        int status = 404;
        LocalDateTime timestamp = LocalDateTime.now();

        ApiErrorDTO error = new ApiErrorDTO(message, status, timestamp);

        assertEquals(message, error.message(), "A mensagem deve ser a mesma que foi passada no construtor.");
        assertEquals(status, error.status(), "O status deve ser o mesmo que foi passado no construtor.");
        assertEquals(timestamp, error.timestamp(), "O timestamp deve ser o mesmo que foi passado no construtor.");
    }

    @Test
    void equalsAndHashCode_shouldBehaveAsExpected() {
        LocalDateTime timestamp = LocalDateTime.now();
        ApiErrorDTO error1 = new ApiErrorDTO("Mesma mensagem", 200, timestamp);
        ApiErrorDTO error2 = new ApiErrorDTO("Mesma mensagem", 200, timestamp);
        ApiErrorDTO error3 = new ApiErrorDTO("Mensagem diferente", 500, timestamp);

        assertEquals(error1, error2, "Dois records com os mesmos valores devem ser iguais.");
        assertNotEquals(error1, error3, "Dois records com valores diferentes não devem ser iguais.");
        assertNotEquals(null, error1, "Um record não deve ser igual a nulo.");

        assertEquals(error1.hashCode(), error2.hashCode(), "O hashCode de dois records iguais deve ser o mesmo.");
        assertNotEquals(error1.hashCode(), error3.hashCode(), "O hashCode de dois records diferentes geralmente não é o mesmo.");
    }

    @Test
    void toString_shouldReturnAStringRepresentation() {
        ApiErrorDTO error = new ApiErrorDTO("Erro", 500, LocalDateTime.now());

        String stringRepresentation = error.toString();

        assertNotNull(stringRepresentation, "A representação em string não deve ser nula.");
        assertTrue(stringRepresentation.contains("message=Erro"), "A string deve conter a mensagem.");
        assertTrue(stringRepresentation.contains("status=500"), "A string deve conter o status.");
    }
}
