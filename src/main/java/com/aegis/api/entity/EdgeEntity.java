package com.aegis.api.entity;

/**
 * Entity representing an Edge in the data layer.
 * Separates data representation from domain model.
 */
public class EdgeEntity {

    private final String originId;
    private final String destId;
    private final int risk;
    private final int distance;

    public EdgeEntity(String originId, String destId, int risk, int distance) {
        this.originId = originId;
        this.destId = destId;
        this.risk = risk;
        this.distance = distance;
    }

    public String getOriginId() {
        return originId;
    }

    public String getDestId() {
        return destId;
    }

    public int getRisk() {
        return risk;
    }

    public int getDistance() {
        return distance;
    }
}

