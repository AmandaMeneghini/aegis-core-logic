package com.aegis.api;

import com.aegis.api.entity.EdgeEntity;
import com.aegis.api.entity.VertexEntity;
import com.aegis.api.repository.IEdgeRepository;
import com.aegis.api.repository.IVertexRepository;
import com.aegis.api.strategy.ICostCalculator;
import com.aegis.core.datastructures.MyLinkedList;
import com.aegis.core.graph.Graph;
import com.aegis.core.graph.Vertex;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Concrete implementation of IGraphService.
 * Loads graph data from repositories on startup.
 * <p>
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
    private final Graph graph = new Graph();

    public GraphService(
            IVertexRepository vertexRepository,
            IEdgeRepository edgeRepository,
            ICostCalculator costCalculator) {
        this.vertexRepository = vertexRepository;
        this.edgeRepository = edgeRepository;
        this.costCalculator = costCalculator;
    }

    @PostConstruct
    @CacheEvict(value = {"routes", "criticalPoints"}, allEntries = true)
    public void initializeGraph() {
        logger.info("🛡️ Iniciando carregamento do grafo...");

        List<VertexEntity> vertices = vertexRepository.findAll();
        for (VertexEntity vertex : vertices) {
            graph.addVertex(vertex.getId(), vertex.getName());
        }
        logger.debug("Carregados {} vértices", vertices.size());

        List<EdgeEntity> edges = edgeRepository.findAll();
        for (EdgeEntity edge : edges) {
            int finalCost = costCalculator.calculate(edge.getRisk(), edge.getDistance());
            graph.addDirectedEdge(edge.getOriginId(), edge.getDestId(), finalCost);
        }
        logger.debug("Carregadas {} arestas", edges.size());

        logger.info("✅ Grafo carregado com sucesso! Total de locais: {}", graph.getVertices().size());
    }

    @Override
    public Graph getGraph() {
        logger.info("Retornando instância do grafo.");
        return this.graph;
    }

    @Override
    @Cacheable(value = "routes", key = "#originId + '_' + #destinationId")
    public MyLinkedList<Vertex> findSafestRoute(String originId, String destinationId) {
        logger.info(">>> Calculating safest route from {} to {}...", originId, destinationId);
        return graph.findSafestRoute(originId, destinationId);
    }

    @Override
    @Cacheable("criticalPoints")
    public MyLinkedList<Vertex> findCriticalPoints() {
        logger.info(">>> Calculating critical points...");
        return graph.findCriticalPoints();
    }
}
