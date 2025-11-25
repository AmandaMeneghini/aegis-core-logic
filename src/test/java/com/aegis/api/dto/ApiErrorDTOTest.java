package com.aegis.api.dto;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes para a classe ApiErrorDTO.
 * Como ApiErrorDTO é um 'record', estes testes verificam o comportamento padrão
 * gerado pelo compilador Java (construtor, getters, equals, hashCode, toString).
 * Geralmente, tais testes são considerados opcionais, pois testam a funcionalidade da linguagem Java,
 * mas são incluídos aqui para fins de completude e demonstração.
 */
class ApiErrorDTOTest {

    // Verifica se o construtor do record armazena os dados corretamente e se os métodos de acesso os retornam.
    // Este teste garante que a principal função de um record (ser um portador de dados imutável) está funcionando.
    @Test
    void record_shouldHoldAndReturnDataCorrectly() {
        // Arrange
        String message = "Erro de teste";
        int status = 404;
        LocalDateTime timestamp = LocalDateTime.now();

        // Act
        ApiErrorDTO error = new ApiErrorDTO(message, status, timestamp);

        // Assert
        assertEquals(message, error.message(), "A mensagem deve ser a mesma que foi passada no construtor.");
        assertEquals(status, error.status(), "O status deve ser o mesmo que foi passado no construtor.");
        assertEquals(timestamp, error.timestamp(), "O timestamp deve ser o mesmo que foi passado no construtor.");
    }

    // Verifica se os métodos equals() e hashCode() gerados funcionam como esperado.
    // Dois records com os mesmos dados devem ser considerados iguais e ter o mesmo hashCode.
    // Dois records com dados diferentes não devem ser iguais.
    @Test
    void equalsAndHashCode_shouldBehaveAsExpected() {
        // Arrange
        LocalDateTime timestamp = LocalDateTime.now();
        ApiErrorDTO error1 = new ApiErrorDTO("Mesma mensagem", 200, timestamp);
        ApiErrorDTO error2 = new ApiErrorDTO("Mesma mensagem", 200, timestamp);
        ApiErrorDTO error3 = new ApiErrorDTO("Mensagem diferente", 500, timestamp);

        // Act & Assert
        // Verifica a igualdade
        assertEquals(error1, error2, "Dois records com os mesmos valores devem ser iguais.");
        assertNotEquals(error1, error3, "Dois records com valores diferentes não devem ser iguais.");
        assertNotEquals(null, error1, "Um record não deve ser igual a nulo.");

        // Verifica o hashCode
        assertEquals(error1.hashCode(), error2.hashCode(), "O hashCode de dois records iguais deve ser o mesmo.");
        assertNotEquals(error1.hashCode(), error3.hashCode(), "O hashCode de dois records diferentes geralmente não é o mesmo.");
    }

    // Verifica se o método toString() gerado produz uma representação de string não nula e razoável.
    // Útil para garantir que a saída de log do record seja informativa.
    @Test
    void toString_shouldReturnAStringRepresentation() {
        // Arrange
        ApiErrorDTO error = new ApiErrorDTO("Erro", 500, LocalDateTime.now());

        // Act
        String stringRepresentation = error.toString();

        // Assert
        assertNotNull(stringRepresentation, "A representação em string não deve ser nula.");
        assertTrue(stringRepresentation.contains("message=Erro"), "A string deve conter a mensagem.");
        assertTrue(stringRepresentation.contains("status=500"), "A string deve conter o status.");
    }
}
