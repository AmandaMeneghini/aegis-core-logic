package com.aegis.api;

import com.aegis.core.datastructures.MyLinkedList;
import com.aegis.core.graph.Graph;
import com.aegis.core.graph.Vertex;

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

    MyLinkedList<Vertex> findSafestRoute(String originId, String destinationId);

    MyLinkedList<Vertex> findCriticalPoints();
}
