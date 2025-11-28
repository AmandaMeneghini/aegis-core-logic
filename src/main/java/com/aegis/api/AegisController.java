package com.aegis.api;

import com.aegis.api.dto.CriticalPointDTO;
import com.aegis.api.dto.RouteResponseDTO;
import com.aegis.core.graph.Graph;
import com.aegis.core.datastructures.MyLinkedList;
import com.aegis.core.graph.Vertex;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.List;


/**
 * REST Controller for AEGIS API endpoints.
 * Uses dependency injection with IGraphService interface (Dependency Inversion Principle).
 */
@RestController
@RequestMapping("/api/aegis")
@Tag(name = "Aegis API", description = "Endpoints for secure routing and graph analysis")
public class AegisController {

    private final IGraphService graphService;

    /**
     * Constructor injection using the interface abstraction.
     * Spring will automatically inject the concrete implementation (GraphService).
     *
     * @param graphService The graph service implementation
     */
    public AegisController(IGraphService graphService) {
        this.graphService = graphService;
    }

    @Operation(summary = "Find the safest route", description = "Calculates the route with the minimum accumulated risk between an origin and a destination.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Route found successfully or no path exists",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = RouteResponseDTO.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid input parameters", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping("/route")
    public ResponseEntity<RouteResponseDTO> getSafestRoute(
            @Parameter(description = "ID of the origin vertex", required = true, example = "AG-01") @RequestParam String origin,
            @Parameter(description = "ID of the destination vertex", required = true, example = "ATM-01") @RequestParam String destination) {

        if (!destination.startsWith("AG-") && !destination.startsWith("ATM-")) {
            throw new IllegalArgumentException("Invalid destination type. Destination must be an Agency (AG-) or ATM (ATM-).");
        }

        MyLinkedList<Vertex> path = graphService.findSafestRoute(origin, destination);

        if (path == null || path.isEmpty()) {
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
    @Operation(summary = "Find all critical points", description = "Identifies all articulation points (critical points) in the graph. These are vertices whose removal would increase the number of connected components.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Critical points found successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CriticalPointDTO.class)) }),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping("/critical-points")
    public ResponseEntity<CriticalPointDTO> getCriticalPoints() {
        MyLinkedList<Vertex> points = graphService.findCriticalPoints();
        List<CriticalPointDTO.PointDTO> criticalPoints = new ArrayList<>();

        for (int i = 0; i < points.size(); i++) {
            Vertex v = points.get(i);
            criticalPoints.add(new CriticalPointDTO.PointDTO(v.getName(), v.getId()));
        }

        CriticalPointDTO response = new CriticalPointDTO(criticalPoints.size(), criticalPoints);

        return ResponseEntity.ok(response);
    }
}