package com.aegis.api;

import com.aegis.api.dto.CriticalPointDTO;
import com.aegis.api.dto.RouteResponseDTO;
import com.aegis.core.graph.Graph;
import com.aegis.core.datastructures.MyLinkedList;
import com.aegis.core.graph.Vertex;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/api/aegis")
public class AegisController {

    private final GraphService graphService;

    public AegisController(GraphService graphService) {
        this.graphService = graphService;
    }

    @GetMapping("/route")
    public ResponseEntity<RouteResponseDTO> getSafestRoute(
            @RequestParam String origin,
            @RequestParam String destination) {

        Graph graph = graphService.getGraph();
        MyLinkedList<Vertex> path = graph.findSafestRoute(origin, destination);

        if (path.isEmpty()) {
            return ResponseEntity.ok(new RouteResponseDTO(0, List.of()));
        }

        int totalCost = path.get(path.size() - 1).tempMinRisk;
        List<RouteResponseDTO.RouteStepDTO> routeSteps = new ArrayList<>();

        for (int i = 0; i < path.size(); i++) {
            Vertex v = path.get(i);
            routeSteps.add(new RouteResponseDTO.RouteStepDTO(v.getName(), v.getId()));
        }

        RouteResponseDTO response = new RouteResponseDTO(totalCost, routeSteps);
        return ResponseEntity.ok(response);
    }

    /**
     * Endpoint for Critical Points (DFS / Articulation).
     * Mapped to: GET /api/aegis/critical-points
     */
    @GetMapping("/critical-points")
    public ResponseEntity<CriticalPointDTO> getCriticalPoints() {
        Graph graph = graphService.getGraph();
        MyLinkedList<Vertex> points = graph.findCriticalPoints();
        List<CriticalPointDTO.PointDTO> criticalPoints = new ArrayList<>();

        for (int i = 0; i < points.size(); i++) {
            Vertex v = points.get(i);
            criticalPoints.add(new CriticalPointDTO.PointDTO(v.getName(), v.getId()));
        }

        CriticalPointDTO response = new CriticalPointDTO(criticalPoints.size(), criticalPoints);

        return ResponseEntity.ok(response);
    }
}