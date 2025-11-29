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

    @Test
    void handleIllegalArgument_shouldReturnBadRequest() {

        IllegalArgumentException ex = new IllegalArgumentException("Argumento inválido fornecido.");

        ResponseEntity<ApiErrorDTO> response = exceptionHandler.handleIllegalArgument(ex, webRequest);

        assertNotNull(response, "A resposta não deve ser nula.");
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode(), "O status HTTP deve ser 400 BAD_REQUEST.");

        ApiErrorDTO error = response.getBody();
        assertNotNull(error, "O corpo do erro não deve ser nulo.");
        assertEquals(400, error.status(), "O status no corpo do erro deve ser 400.");
        assertEquals("Argumento inválido fornecido.", error.message(), "A mensagem de erro deve corresponder à da exceção.");
    }

    @Test
    void handleNoSuchElement_shouldReturnNotFound() {
        NoSuchElementException ex = new NoSuchElementException("Recurso não localizado.");

        ResponseEntity<ApiErrorDTO> response = exceptionHandler.handleNoSuchElement(ex, webRequest);

        assertNotNull(response, "A resposta não deve ser nula.");
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode(), "O status HTTP deve ser 404 NOT_FOUND.");

        ApiErrorDTO error = response.getBody();
        assertNotNull(error, "O corpo do erro não deve ser nulo.");
        assertEquals(404, error.status(), "O status no corpo do erro deve ser 404.");
        assertEquals("Recurso não localizado.", error.message(), "A mensagem de erro deve corresponder à da exceção.");
    }

    @Test
    void handleNoSuchElement_shouldReturnDefaultMessage_whenExceptionMessageIsNull() {
        NoSuchElementException ex = new NoSuchElementException(); // Mensagem é nula por padrão

        ResponseEntity<ApiErrorDTO> response = exceptionHandler.handleNoSuchElement(ex, webRequest);

        assertNotNull(response, "A resposta não deve ser nula.");
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode(), "O status HTTP deve ser 404 NOT_FOUND.");

        ApiErrorDTO error = response.getBody();
        assertNotNull(error, "O corpo do erro não deve ser nulo.");
        assertEquals(404, error.status(), "O status no corpo do erro deve ser 404.");
        assertEquals("Recurso não encontrado.", error.message(), "A mensagem de erro deve ser a padrão.");
    }

    @Test
    void handleGlobalException_shouldReturnInternalServerError() {
        Exception ex = new RuntimeException("Erro inesperado.");

        ResponseEntity<ApiErrorDTO> response = exceptionHandler.handleGlobalException(ex, webRequest);

        assertNotNull(response, "A resposta não deve ser nula.");
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode(), "O status HTTP deve ser 500 INTERNAL_SERVER_ERROR.");

        ApiErrorDTO error = response.getBody();
        assertNotNull(error, "O corpo do erro não deve ser nulo.");
        assertEquals(500, error.status(), "O status no corpo do erro deve ser 500.");
        assertEquals("Um erro interno inesperado ocorreu.", error.message(), "A mensagem de erro deve ser a genérica para erros internos.");
    }
}
