package com.aegis.api.strategy;

import org.springframework.stereotype.Component;

@Component
public class DefaultCostCalculator implements ICostCalculator {
    private static final int RISK_SCALE = 100;
    private static final int RISK_WEIGHT = 7;
    private static final int DISTANCE_WEIGHT = 3;

    @Override
    public int calculate(int risk, int distance) {
        long normalizedRisk = (long) risk * RISK_SCALE;

        long riskPart = normalizedRisk * RISK_WEIGHT;
        long distancePart = (long) distance * DISTANCE_WEIGHT;

        return (int) ((riskPart + distancePart) / 10);
    }
}

