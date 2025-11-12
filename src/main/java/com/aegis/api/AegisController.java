package com.aegis.api;

import com.aegis.core.Graph;
import com.aegis.core.MyLinkedList;
import com.aegis.core.Vertex;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map; // Para respostas JSON simples

@RestController
@RequestMapping("/api/aegis")
public class AegisController {

    private final GraphService graphService;

    public AegisController(GraphService graphService) {
        this.graphService = graphService;
    }

    /**
     * Endpoint para a Rota Mais Segura (Dijkstra).
     * Mapeado para: GET /api/aegis/route?origin=ID1&destination=ID2
     */
    @GetMapping("/route")
    public ResponseEntity<?> getSafestRoute(
            @RequestParam String origin,
            @RequestParam String destination) {

        try {
            Graph graph = graphService.getGraph();
            MyLinkedList<Vertex> path = graph.findSafestRoute(origin, destination);

            if (path.isEmpty()) {
                return ResponseEntity.ok(Map.of("message", "Nenhuma rota encontrada."));
            }

            int totalCost = path.get(path.size() - 1).tempMinRisk;

            String[] routeSteps = new String[path.size()];
            for (int i = 0; i < path.size(); i++) {
                Vertex v = path.get(i);
                routeSteps[i] = v.getName() + " (ID: " + v.getId() + ")";
            }

            return ResponseEntity.ok(Map.of(
                    "totalCalculatedCost", totalCost,
                    "route", routeSteps
            ));

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * Endpoint para os Pontos Críticos (DFS / Articulação).
     * Mapeado para: GET /api/aegis/critical-points
     */
    @GetMapping("/critical-points")
    public ResponseEntity<?> getCriticalPoints() {
        Graph graph = graphService.getGraph();

        // --- CÓDIGO ATIVADO ---
        // Agora podemos chamar a função real, pois sabemos que ela existe
        // no seu Graph.java!
        MyLinkedList<Vertex> points = graph.findCriticalPoints();
        // ---------------------

        String[] criticalPoints = new String[points.size()];
        for (int i = 0; i < points.size(); i++) {
            criticalPoints[i] = points.get(i).getName() + " (ID: " + points.get(i).getId() + ")";
        }

        return ResponseEntity.ok(Map.of(
                "criticalPointsFound", points.size(),
                "points", criticalPoints
        ));
    }
}