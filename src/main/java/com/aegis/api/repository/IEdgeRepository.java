package com.aegis.api.repository;

import com.aegis.api.entity.EdgeEntity;
import java.util.List;

/**
 * Repository interface for Edge data access.
 * Abstracts the data source (database, file, etc.)
 */
public interface IEdgeRepository {

    /**
     * Retrieves all edges from the data source.
     * @return List of edge entities
     */
    List<EdgeEntity> findAll();
}

