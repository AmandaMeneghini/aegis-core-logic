package com.aegis.api.dto;

import java.util.List;

public record RouteResponseDTO(
        int totalCalculatedCost,
        List<RouteStepDTO> route
) {
    public record RouteStepDTO(String name, String id) {}
}