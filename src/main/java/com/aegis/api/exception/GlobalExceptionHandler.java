package com.aegis.api.exception;

import com.aegis.api.dto.ApiErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

/**
 * Global exception handler for the AEGIS API.
 * Centralizes error handling and response formatting.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Handles IllegalArgumentException - typically for invalid input parameters.
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiErrorDTO> handleIllegalArgument(
            IllegalArgumentException ex,
            WebRequest request) {

        logger.warn("Invalid argument: {} - Request: {}", ex.getMessage(), request.getDescription(false));

        ApiErrorDTO error = new ApiErrorDTO(
                ex.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles NoSuchElementException - typically when a vertex/resource is not found.
     */
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ApiErrorDTO> handleNoSuchElement(
            NoSuchElementException ex,
            WebRequest request) {

        logger.warn("Resource not found: {} - Request: {}", ex.getMessage(), request.getDescription(false));

        ApiErrorDTO error = new ApiErrorDTO(
                ex.getMessage() != null ? ex.getMessage() : "Recurso não encontrado.",
                HttpStatus.NOT_FOUND.value(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles all other uncaught exceptions.
     * This is the fallback handler.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorDTO> handleGlobalException(
            Exception ex,
            WebRequest request) {

        logger.error("Unexpected error occurred: {} - Request: {}",
                ex.getMessage(),
                request.getDescription(false),
                ex);

        ApiErrorDTO error = new ApiErrorDTO(
                "Um erro interno inesperado ocorreu.",
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}