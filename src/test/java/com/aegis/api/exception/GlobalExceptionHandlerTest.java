package com.aegis.api.exception;

import com.aegis.api.dto.ApiErrorDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler exceptionHandler;

    @Mock
    private WebRequest webRequest;

    @BeforeEach
    void setUp() {
        exceptionHandler = new GlobalExceptionHandler();
        when(webRequest.getDescription(false)).thenReturn("test request");
    }

    // Verifica se o handler para IllegalArgumentException retorna uma resposta de BAD_REQUEST (400).
    // Isso é crucial para notificar o cliente que a requisição continha parâmetros inválidos.
    @Test
    void handleIllegalArgument_shouldReturnBadRequest() {
        // Arrange
        IllegalArgumentException ex = new IllegalArgumentException("Argumento inválido fornecido.");

        // Act
        ResponseEntity<ApiErrorDTO> response = exceptionHandler.handleIllegalArgument(ex, webRequest);

        // Assert
        assertNotNull(response, "A resposta não deve ser nula.");
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode(), "O status HTTP deve ser 400 BAD_REQUEST.");

        ApiErrorDTO error = response.getBody();
        assertNotNull(error, "O corpo do erro não deve ser nulo.");
        assertEquals(400, error.status(), "O status no corpo do erro deve ser 400.");
        assertEquals("Argumento inválido fornecido.", error.message(), "A mensagem de erro deve corresponder à da exceção.");
    }

    // Verifica se o handler para NoSuchElementException retorna uma resposta de NOT_FOUND (404).
    // Essencial para indicar que um recurso solicitado não foi encontrado no sistema.
    @Test
    void handleNoSuchElement_shouldReturnNotFound() {
        // Arrange
        NoSuchElementException ex = new NoSuchElementException("Recurso não localizado.");

        // Act
        ResponseEntity<ApiErrorDTO> response = exceptionHandler.handleNoSuchElement(ex, webRequest);

        // Assert
        assertNotNull(response, "A resposta não deve ser nula.");
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode(), "O status HTTP deve ser 404 NOT_FOUND.");

        ApiErrorDTO error = response.getBody();
        assertNotNull(error, "O corpo do erro não deve ser nulo.");
        assertEquals(404, error.status(), "O status no corpo do erro deve ser 404.");
        assertEquals("Recurso não localizado.", error.message(), "A mensagem de erro deve corresponder à da exceção.");
    }

    // Verifica o caso de borda onde NoSuchElementException é lançada com uma mensagem nula.
    // O handler deve fornecer uma mensagem padrão para garantir que o cliente sempre receba um erro claro.
    @Test
    void handleNoSuchElement_shouldReturnDefaultMessage_whenExceptionMessageIsNull() {
        // Arrange
        NoSuchElementException ex = new NoSuchElementException(); // Mensagem é nula por padrão

        // Act
        ResponseEntity<ApiErrorDTO> response = exceptionHandler.handleNoSuchElement(ex, webRequest);

        // Assert
        assertNotNull(response, "A resposta não deve ser nula.");
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode(), "O status HTTP deve ser 404 NOT_FOUND.");

        ApiErrorDTO error = response.getBody();
        assertNotNull(error, "O corpo do erro não deve ser nulo.");
        assertEquals(404, error.status(), "O status no corpo do erro deve ser 404.");
        assertEquals("Recurso não encontrado.", error.message(), "A mensagem de erro deve ser a padrão.");
    }

    // Verifica se o handler genérico para Exception retorna uma resposta de INTERNAL_SERVER_ERROR (500).
    // Este é o handler "catch-all" para quaisquer outras exceções não tratadas, prevenindo o vazamento de detalhes de implementação.
    @Test
    void handleGlobalException_shouldReturnInternalServerError() {
        // Arrange
        Exception ex = new RuntimeException("Erro inesperado.");

        // Act
        ResponseEntity<ApiErrorDTO> response = exceptionHandler.handleGlobalException(ex, webRequest);

        // Assert
        assertNotNull(response, "A resposta não deve ser nula.");
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode(), "O status HTTP deve ser 500 INTERNAL_SERVER_ERROR.");

        ApiErrorDTO error = response.getBody();
        assertNotNull(error, "O corpo do erro não deve ser nulo.");
        assertEquals(500, error.status(), "O status no corpo do erro deve ser 500.");
        assertEquals("Um erro interno inesperado ocorreu.", error.message(), "A mensagem de erro deve ser a genérica para erros internos.");
    }
}
