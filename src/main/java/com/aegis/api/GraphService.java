package com.aegis.api;

import com.aegis.api.entity.EdgeEntity;
import com.aegis.api.entity.VertexEntity;
import com.aegis.api.repository.IEdgeRepository;
import com.aegis.api.repository.IVertexRepository;
import com.aegis.api.strategy.ICostCalculator;
import com.aegis.core.graph.Graph;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;

import java.util.List;

/**
 * Concrete implementation of IGraphService.
 * Loads graph data from repositories on startup.
 *
 * This class is now FULLY DECOUPLED:
 * - Uses IVertexRepository instead of direct database access
 * - Uses IEdgeRepository instead of direct database access
 * - Uses ICostCalculator strategy for cost calculation
 * - Uses SLF4J Logger instead of System.out.println
 */
@Service
public class GraphService implements IGraphService {

    private static final Logger logger = LoggerFactory.getLogger(GraphService.class);

    private final IVertexRepository vertexRepository;
    private final IEdgeRepository edgeRepository;
    private final ICostCalculator costCalculator;
    private Graph graph;

    /**
     * Constructor with dependency injection.
     * All dependencies are interfaces, following the Dependency Inversion Principle.
     */
    public GraphService(
            IVertexRepository vertexRepository,
            IEdgeRepository edgeRepository,
            ICostCalculator costCalculator) {
        this.vertexRepository = vertexRepository;
        this.edgeRepository = edgeRepository;
        this.costCalculator = costCalculator;
    }

    @PostConstruct
    public void initializeGraph() {
        this.graph = new Graph();
        logger.info("🛡️ Iniciando carregamento do grafo...");

        // Load vertices from repository
        List<VertexEntity> vertices = vertexRepository.findAll();
        for (VertexEntity vertex : vertices) {
            graph.addVertex(vertex.getId(), vertex.getName());
        }
        logger.debug("Carregados {} vértices", vertices.size());

        // Load edges from repository
        List<EdgeEntity> edges = edgeRepository.findAll();
        for (EdgeEntity edge : edges) {
            int finalCost = costCalculator.calculate(edge.getRisk(), edge.getDistance());
            graph.addDirectedEdge(edge.getOriginId(), edge.getDestId(), finalCost);
        }
        logger.debug("Carregadas {} arestas", edges.size());

        logger.info("✅ Grafo carregado com sucesso! Total de locais: {}", graph.getVertices().size());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Graph getGraph() {
        return this.graph;
    }
}