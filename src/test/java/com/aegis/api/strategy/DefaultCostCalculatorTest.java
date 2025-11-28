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

        assertEquals(60, cost, "O custo deve ser 60 para risco 5 e distância 1000");
    }

    @Test
    void calculatesCostCorrectly_whenRiskIsZero() {

        int risk = 0;
        int distance = 2500;
        int cost = costCalculator.calculate(risk, distance);
        assertEquals(25, cost, "O custo deve ser 25 para risco 0 e distância 2500");
    }

    @Test
    void calculatesCostCorrectly_whenDistanceIsZero() {
        int risk = 8;
        int distance = 0;
        int cost = costCalculator.calculate(risk, distance);

        assertEquals(80, cost, "O custo deve ser 80 para risco 8 e distância 0");
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

        assertEquals(30, cost, "O custo deve ser 30 para risco 3 e distância 99, devido à divisão de inteiros");
    }

    @Test
    void calculatesCostCorrectly_withLargeValues() {
        int risk = 10;
        int distance = 100000;
        int cost = costCalculator.calculate(risk, distance);

        assertEquals(1100, cost, "O custo deve ser 1100 para risco 10 e distância 100000");
    }
}
