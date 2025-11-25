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

    // Verifica se o custo é calculado corretamente para valores normais e válidos de risco e distância.
    // Este é o cenário de caso de uso mais comum.
    @Test
    void calculatesCostCorrectly_whenGivenValidRiskAndDistance() {
        // Arrange
        int risk = 5;
        int distance = 1000; // 10km

        // Act
        int cost = costCalculator.calculate(risk, distance);

        // Assert
        assertEquals(60, cost, "O custo deve ser 60 para risco 5 e distância 1000");
    }

    // Verifica se o custo é calculado corretamente quando o risco é zero.
    // Este teste de caso de borda garante que o componente de risco da fórmula seja zero quando a entrada de risco for zero.
    @Test
    void calculatesCostCorrectly_whenRiskIsZero() {
        // Arrange
        int risk = 0;
        int distance = 2500; // 25km

        // Act
        int cost = costCalculator.calculate(risk, distance);

        // Assert
        assertEquals(25, cost, "O custo deve ser 25 para risco 0 e distância 2500");
    }

    // Verifica se o custo é calculado corretamente quando a distância é zero.
    // Este teste de caso de borda garante que o componente de distância da fórmula seja zero quando a entrada de distância for zero.
    @Test
    void calculatesCostCorrectly_whenDistanceIsZero() {
        // Arrange
        int risk = 8;
        int distance = 0;

        // Act
        int cost = costCalculator.calculate(risk, distance);

        // Assert
        assertEquals(80, cost, "O custo deve ser 80 para risco 8 e distância 0");
    }

    // Verifica se o custo é zero quando tanto o risco quanto a distância são zero.
    // Este é um teste de sanidade para garantir que a calculadora produza um resultado zero para entradas zero.
    @Test
    void calculatesCostCorrectly_whenRiskAndDistanceAreZero() {
        // Arrange
        int risk = 0;
        int distance = 0;

        // Act
        int cost = costCalculator.calculate(risk, distance);

        // Assert
        assertEquals(0, cost, "O custo deve ser 0 para risco 0 e distância 0");
    }

    // Verifica como a calculadora lida com a divisão de inteiros quando a distância é menor que o divisor.
    // O resultado da divisão da distância deve ser zero, resultando em um custo baseado apenas no risco.
    @Test
    void calculatesCostCorrectly_whenDistanceIsLessThanDivisor() {
        // Arrange
        int risk = 3;
        int distance = 99;

        // Act
        int cost = costCalculator.calculate(risk, distance);

        // Assert
        assertEquals(30, cost, "O custo deve ser 30 para risco 3 e distância 99, devido à divisão de inteiros");
    }

    // Verifica se o cálculo permanece correto com valores de entrada grandes.
    // Isso testa os limites superiores dos tipos de dados e garante que não haja overflow inesperado dentro do escopo de valores `int` válidos.
    @Test
    void calculatesCostCorrectly_withLargeValues() {
        // Arrange
        int risk = 10;
        int distance = 100000; // 1000km

        // Act
        int cost = costCalculator.calculate(risk, distance);

        // Assert
        assertEquals(1100, cost, "O custo deve ser 1100 para risco 10 e distância 100000");
    }
}
