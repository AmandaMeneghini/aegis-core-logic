package com.aegis.api;

import com.aegis.api.entity.EdgeEntity;
import com.aegis.api.entity.VertexEntity;
import com.aegis.api.repository.IEdgeRepository;
import com.aegis.api.repository.IVertexRepository;
import com.aegis.api.strategy.ICostCalculator;
import com.aegis.core.graph.Graph;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class GraphServiceTest {

    @Mock
    private IVertexRepository vertexRepository;

    @Mock
    private IEdgeRepository edgeRepository;

    @Mock
    private ICostCalculator costCalculator;

    private GraphService graphService;

    @BeforeEach
    void setUp() {
        graphService = new GraphService(vertexRepository, edgeRepository, costCalculator);
    }

    @Test
    void testInitializeGraphLoadsVerticesAndEdges() {

        List<VertexEntity> mockVertices = Arrays.asList(
            new VertexEntity("A", "Location A"),
            new VertexEntity("B", "Location B"),
            new VertexEntity("C", "Location C")
        );

        List<EdgeEntity> mockEdges = Arrays.asList(
            new EdgeEntity("A", "B", 5, 1000),
            new EdgeEntity("B", "C", 3, 2000)
        );

        when(vertexRepository.findAll()).thenReturn(mockVertices);
        when(edgeRepository.findAll()).thenReturn(mockEdges);
        when(costCalculator.calculate(anyInt(), anyInt())).thenReturn(60);

        graphService.initializeGraph();
        Graph graph = graphService.getGraph();

        assertNotNull(graph, "Graph should not be null after initialization");
        assertEquals(3, graph.getVertices().size(), "Graph should have 3 vertices");

        verify(vertexRepository, times(1)).findAll();
        verify(edgeRepository, times(1)).findAll();
        verify(costCalculator, times(2)).calculate(anyInt(), anyInt());
    }

    @Test
    void testCostCalculatorIsCalledWithCorrectParameters() {

        List<VertexEntity> mockVertices = Arrays.asList(
            new VertexEntity("A", "Location A"),
            new VertexEntity("B", "Location B")
        );

        List<EdgeEntity> mockEdges = List.of(
            new EdgeEntity("A", "B", 7, 3000)
        );

        when(vertexRepository.findAll()).thenReturn(mockVertices);
        when(edgeRepository.findAll()).thenReturn(mockEdges);
        when(costCalculator.calculate(7, 3000)).thenReturn(100);

        graphService.initializeGraph();

        verify(costCalculator).calculate(7, 3000);
    }

    @Test
    void testGraphServiceHandlesEmptyData() {

        when(vertexRepository.findAll()).thenReturn(List.of());
        when(edgeRepository.findAll()).thenReturn(List.of());

        graphService.initializeGraph();
        Graph graph = graphService.getGraph();

        assertNotNull(graph);
        assertEquals(0, graph.getVertices().size(), "Graph should be empty");

        verify(vertexRepository, times(1)).findAll();
        verify(edgeRepository, times(1)).findAll();
        verify(costCalculator, never()).calculate(anyInt(), anyInt());
    }
}

