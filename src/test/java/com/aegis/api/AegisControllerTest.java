package com.aegis.api;

import com.aegis.api.dto.RouteResponseDTO;
import com.aegis.core.graph.Graph;
import com.aegis.core.graph.Vertex;
import com.aegis.core.datastructures.MyLinkedList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AegisControllerTest {

    @Mock
    private IGraphService graphService;

    @Mock
    private Graph graph;

    @Mock
    private MyLinkedList<Vertex> pathOrPoints;

    @InjectMocks
    private AegisController controller;

    @BeforeEach
    void setUp() {
        when(graphService.getGraph()).thenReturn(graph);
    }

    @Test
    void getSafestRoute_whenPathEmpty_returnsOkWithEmptyRoute() {

        when(graph.findSafestRoute("A", "B")).thenReturn(pathOrPoints);
        when(pathOrPoints.isEmpty()).thenReturn(true);

        ResponseEntity<?> response = controller.getSafestRoute("A", "B");

        assertNotNull(response);
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertNotNull(response.getBody());
        verify(graph, times(1)).findSafestRoute("A", "B");
        verify(pathOrPoints, never()).get(anyInt());
    }

    @Test
    void getCriticalPoints_whenPointsFound_returnsOkAndConvertsVertices() {
        @SuppressWarnings("unchecked")
        MyLinkedList<Vertex> points = mock(MyLinkedList.class);
        Vertex p1 = mock(Vertex.class);
        Vertex p2 = mock(Vertex.class);

        when(graph.findCriticalPoints()).thenReturn(points);
        when(points.size()).thenReturn(2);
        when(points.get(0)).thenReturn(p1);
        when(points.get(1)).thenReturn(p2);

        when(p1.getName()).thenReturn("P1");
        when(p1.getId()).thenReturn("101");
        when(p2.getName()).thenReturn("P2");
        when(p2.getId()).thenReturn("102");

        ResponseEntity<?> response = controller.getCriticalPoints();

        assertNotNull(response);
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertNotNull(response.getBody());

        verify(graph, times(1)).findCriticalPoints();
        verify(points, times(1)).get(0);
        verify(points, times(1)).get(1);
        verify(p1, atLeastOnce()).getName();
        verify(p1, atLeastOnce()).getId();
        verify(p2, atLeastOnce()).getName();
        verify(p2, atLeastOnce()).getId();
    }

    @Test
    void getCriticalPoints_whenNoPoints_returnsOkWithZeroCount() {
        @SuppressWarnings("unchecked")
        MyLinkedList<Vertex> points = mock(MyLinkedList.class);

        when(graph.findCriticalPoints()).thenReturn(points);
        when(points.size()).thenReturn(0);

        ResponseEntity<?> response = controller.getCriticalPoints();

        assertNotNull(response);
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertNotNull(response.getBody());

        verify(graph, times(1)).findCriticalPoints();
        verify(points, never()).get(anyInt());
    }

    @Test
    void getSafestRoute_shouldReturnOkAndRoute_whenPathIsFound() {
        Vertex v1 = new Vertex("A", "Ponto A");
        Vertex v2 = new Vertex("B", "Ponto B");
        v2.tempMinRisk = 150;

        MyLinkedList<Vertex> path = new MyLinkedList<>();
        path.add(v1);
        path.add(v2);

        when(graph.findSafestRoute("A", "B")).thenReturn(path);

        ResponseEntity<RouteResponseDTO> response = controller.getSafestRoute("A", "B");

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        RouteResponseDTO responseBody = response.getBody();
        assertNotNull(responseBody);
        assertEquals(150, responseBody.totalCalculatedCost());
        assertEquals(2, responseBody.route().size());
        assertEquals("Ponto A", responseBody.route().get(0).name());
        assertEquals("B", responseBody.route().get(1).id());

        verify(graph, times(1)).findSafestRoute("A", "B");
    }

    @Test
    void getSafestRoute_shouldThrowException_whenOriginOrDestinationNotFound() {

        String errorMessage = "Vértice de origem 'Z' não encontrado.";
        when(graph.findSafestRoute("Z", "B")).thenThrow(new NoSuchElementException(errorMessage));

        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> {
            controller.getSafestRoute("Z", "B");
        });

        assertEquals(errorMessage, exception.getMessage());
        verify(graph, times(1)).findSafestRoute("Z", "B");
    }
}
