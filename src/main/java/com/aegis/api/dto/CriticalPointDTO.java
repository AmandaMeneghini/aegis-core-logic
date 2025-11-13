package com.aegis.api.dto;

import java.util.List;

public record CriticalPointDTO(
        int criticalPointsFound,
        List<PointDTO> points
) {
    public record PointDTO(String name, String id) {}
}
