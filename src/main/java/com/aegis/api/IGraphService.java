package com.aegis.api;

import com.aegis.core.graph.Graph;

/**
 * Interface (Abstraction) for the Graph Service.
 * Defines the "contract" that the Controller expects.
 */
public interface IGraphService {

    /**
     * Gets the main graph object,
     * already loaded and ready for use.
     *
     * @return The Graph object.
     */
    Graph getGraph();
}
