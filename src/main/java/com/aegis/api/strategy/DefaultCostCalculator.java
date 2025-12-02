package com.aegis.api.strategy;

import org.springframework.stereotype.Component;

@Component
public class DefaultCostCalculator implements ICostCalculator {
    private static final int RISK_SCALE = 100;     // Normalização
    private static final int RISK_WEIGHT = 7;      // Peso do risco (70%)
    private static final int DISTANCE_WEIGHT = 3;  // Peso da distância (30%)

    @Override
    public int calculate(int risk, int distance) {
        // Normaliza o risco (0-10) para escala de metros
        long normalizedRisk = (long) risk * RISK_SCALE;

        // Aplica os pesos
        long riskPart = normalizedRisk * RISK_WEIGHT;          // 70%
        long distancePart = (long) distance * DISTANCE_WEIGHT; // 30%

        // Fórmula final: ((risk × 100 × 7) + (distance × 3)) / 10
        return (int) ((riskPart + distancePart) / 10);
    }
}

