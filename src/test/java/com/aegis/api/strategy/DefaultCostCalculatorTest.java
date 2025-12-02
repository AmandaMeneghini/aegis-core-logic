package com.aegis.api.strategy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class DefaultCostCalculatorTest {

    private DefaultCostCalculator costCalculator;

    @BeforeEach
    void setUp() {
        costCalculator = new DefaultCostCalculator();
    }

    @Test
    void calculatesCostCorrectly_whenGivenValidRiskAndDistance() {
        int risk = 5;
        int distance = 1000;
        int cost = costCalculator.calculate(risk, distance);

        assertEquals(650, cost, "O custo deve ser 650 para risco 5 e distância 1000");
    }

    @Test
    void calculatesCostCorrectly_whenRiskIsZero() {

        int risk = 0;
        int distance = 2500;
        int cost = costCalculator.calculate(risk, distance);
        assertEquals(750, cost, "O custo deve ser 750 para risco 0 e distância 2500");
    }

    @Test
    void calculatesCostCorrectly_whenDistanceIsZero() {
        int risk = 8;
        int distance = 0;
        int cost = costCalculator.calculate(risk, distance);

        assertEquals(560, cost, "O custo deve ser 560 para risco 8 e distância 0");
    }

    @Test
    void calculatesCostCorrectly_whenRiskAndDistanceAreZero() {
        int risk = 0;
        int distance = 0;
        int cost = costCalculator.calculate(risk, distance);

        assertEquals(0, cost, "O custo deve ser 0 para risco 0 e distância 0");
    }

    @Test
    void calculatesCostCorrectly_whenDistanceIsLessThanDivisor() {
        int risk = 3;
        int distance = 99;
        int cost = costCalculator.calculate(risk, distance);

        assertEquals(239, cost, "O custo deve ser 239 para risco 3 e distância 99");
    }

    @Test
    void calculatesCostCorrectly_withLargeValues() {
        int risk = 10;
        int distance = 100000;
        int cost = costCalculator.calculate(risk, distance);

        assertEquals(30700, cost, "O custo deve ser 30700 para risco 10 e distância 100000");
    }
}
