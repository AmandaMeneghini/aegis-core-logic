package com.aegis.api.dto;

import java.time.LocalDateTime;

public record ApiErrorDTO(
        String message,
        int status,
        LocalDateTime timestamp
) {}
