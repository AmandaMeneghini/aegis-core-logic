package com.aegis.api.repository;

import com.aegis.api.entity.VertexEntity;
import java.util.List;

/**
 * Repository interface for Vertex data access.
 * Abstracts the data source (database, file, etc.).
 */
public interface IVertexRepository {

    /**
     * Retrieves all vertices from the data source.
     *
     * @return list of vertex entities
     */
    List<VertexEntity> findAll();
}
