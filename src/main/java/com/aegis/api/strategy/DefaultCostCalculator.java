package com.aegis.api.strategy;

import org.springframework.stereotype.Component;

/**
 * Default implementation of the cost calculation strategy.
 * Formula: (risk * 10) + (distance / 100)
 *
 * This can be easily replaced by creating another implementation
 * and marking it with @Primary or using @Qualifier.
 */
@Component
public class DefaultCostCalculator implements ICostCalculator {

    private static final int RISK_WEIGHT = 10;
    private static final int DISTANCE_DIVISOR = 100;

    @Override
    public int calculate(int risk, int distance) {
        return (risk * RISK_WEIGHT) + (distance / DISTANCE_DIVISOR);
    }
}

