package com.aegis.api;

import com.aegis.api.dto.RouteResponseDTO;
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
    private MyLinkedList<Vertex> pathOrPoints;

    @InjectMocks
    private AegisController controller;

    @BeforeEach
    void setUp() {

    }

    @Test
    void getSafestRoute_whenPathEmpty_returnsOkWithEmptyRoute() {
        when(graphService.findSafestRoute("AG-01", "ATM-01")).thenReturn(pathOrPoints);
        when(pathOrPoints.isEmpty()).thenReturn(true);

        ResponseEntity<?> response = controller.getSafestRoute("AG-01", "ATM-01");

        assertNotNull(response);
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertNotNull(response.getBody());
        verify(graphService, times(1)).findSafestRoute("AG-01", "ATM-01");
        verify(pathOrPoints, never()).get(anyInt());
    }

    @Test
    void getCriticalPoints_whenPointsFound_returnsOkAndConvertsVertices() {
        @SuppressWarnings("unchecked")
        MyLinkedList<Vertex> points = mock(MyLinkedList.class);
        Vertex p1 = mock(Vertex.class);
        Vertex p2 = mock(Vertex.class);

        when(graphService.findCriticalPoints()).thenReturn(points);
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

        verify(graphService, times(1)).findCriticalPoints();
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

        when(graphService.findCriticalPoints()).thenReturn(points);
        when(points.size()).thenReturn(0);

        ResponseEntity<?> response = controller.getCriticalPoints();

        assertNotNull(response);
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertNotNull(response.getBody());

        verify(graphService, times(1)).findCriticalPoints();
        verify(points, never()).get(anyInt());
    }

    @Test
    void getSafestRoute_shouldReturnOkAndRoute_whenPathIsFound() {
        Vertex v1 = new Vertex("AG-01", "Agência Central");
        Vertex v2 = new Vertex("ATM-01", "Caixa Shopping");
        v2.tempMinRisk = 150;

        MyLinkedList<Vertex> path = new MyLinkedList<>();
        path.add(v1);
        path.add(v2);

        when(graphService.findSafestRoute("AG-01", "ATM-01")).thenReturn(path);

        ResponseEntity<RouteResponseDTO> response = controller.getSafestRoute("AG-01", "ATM-01");

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        RouteResponseDTO responseBody = response.getBody();
        assertNotNull(responseBody);
        assertEquals(150, responseBody.totalCalculatedCost());
        assertEquals(2, responseBody.route().size());
        assertEquals("Agência Central", responseBody.route().get(0).name());
        assertEquals("ATM-01", responseBody.route().get(1).id());

        verify(graphService, times(1)).findSafestRoute("AG-01", "ATM-01");
    }

    @Test
    void getSafestRoute_shouldThrowException_whenOriginOrDestinationNotFound() {
        String errorMessage = "Vértice de origem 'Z' não encontrado.";
        when(graphService.findSafestRoute("AG-01", "ATM-99")).thenThrow(new NoSuchElementException(errorMessage));

        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> {
            controller.getSafestRoute("AG-01", "ATM-99");
        });

        assertEquals(errorMessage, exception.getMessage());
        verify(graphService, times(1)).findSafestRoute("AG-01", "ATM-99");
    }

    @Test
    void getSafestRoute_shouldThrowIllegalArgumentException_whenDestinationIsInvalid() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            controller.getSafestRoute("AG-01", "INVALID-ID");
        });

        assertEquals("Invalid destination type. Destination must be an Agency (AG-) or ATM (ATM-).",
                     exception.getMessage());
        verify(graphService, never()).findSafestRoute(anyString(), anyString());
    }

    @Test
    void getSafestRoute_whenPathIsNull_returnsOkWithEmptyRoute() {
        when(graphService.findSafestRoute("AG-01", "ATM-01")).thenReturn(null);

        ResponseEntity<RouteResponseDTO> response = controller.getSafestRoute("AG-01", "ATM-01");

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        RouteResponseDTO responseBody = response.getBody();
        assertNotNull(responseBody);
        assertEquals(0, responseBody.totalCalculatedCost());
        assertTrue(responseBody.route().isEmpty());

        verify(graphService, times(1)).findSafestRoute("AG-01", "ATM-01");
    }

    @Test
    void getSafestRoute_whenDestinationStartsWithAG_shouldAcceptAndProcess() {
        Vertex v1 = new Vertex("ATM-01", "Caixa Central");
        Vertex v2 = new Vertex("AG-02", "Agência Secundária");
        v2.tempMinRisk = 200;

        MyLinkedList<Vertex> path = new MyLinkedList<>();
        path.add(v1);
        path.add(v2);

        when(graphService.findSafestRoute("ATM-01", "AG-02")).thenReturn(path);

        ResponseEntity<RouteResponseDTO> response = controller.getSafestRoute("ATM-01", "AG-02");

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        RouteResponseDTO responseBody = response.getBody();
        assertNotNull(responseBody);
        assertEquals(200, responseBody.totalCalculatedCost());
        assertEquals(2, responseBody.route().size());
        assertEquals("AG-02", responseBody.route().get(1).id());

        verify(graphService, times(1)).findSafestRoute("ATM-01", "AG-02");
    }
}
