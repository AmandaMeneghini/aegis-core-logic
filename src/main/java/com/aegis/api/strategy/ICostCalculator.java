package com.aegis.api.strategy;

/**
 * Strategy interface for calculating edge costs.
 * Allows different cost calculation strategies without changing the code.
 */
public interface ICostCalculator {

    /**
     * Calculates the final cost based on risk and distance.
     *
     * @param risk The risk level of the route
     * @param distance The distance of the route
     * @return The calculated cost
     */
    int calculate(int risk, int distance);
}

