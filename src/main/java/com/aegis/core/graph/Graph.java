package com.aegis.core.graph;

import com.aegis.core.datastructures.MyLinkedList;
import com.aegis.core.datastructures.MyMinHeap;

/**
 * The main Graph class.
 * It manages all the vertices and provides methods to build the map.
 */
public class Graph {

    private final MyLinkedList<Vertex> vertices;
    private int dfsTime;

    public Graph() {
        this.vertices = new MyLinkedList<>();
    }

    /**
     * Adds a new vertex (location) to the graph.
     * @param id The unique ID for the vertex.
     * @param name The human-readable name.
     * @throws IllegalArgumentException if a vertex with the same ID already exists.
     */
    public void addVertex(String id, String name) {
        if (findVertex(id) != null) {
            throw new IllegalArgumentException("Vertex with ID '" + id + "' already exists.");
        }
        Vertex v = new Vertex(id, name);
        this.vertices.add(v);
    }

    /**
     * Finds and returns a Vertex object by its unique ID.
     * Time Complexity: O(n) where n is the number of vertices.
     *
     * @param id The ID of the vertex to find.
     * @return The Vertex object, or null if not found.
     */
    public Vertex findVertex(String id) {
        if (id == null) return null;

        for (int i = 0; i < vertices.size(); i++) {
            Vertex v = vertices.get(i);
            if (id.equals(v.getId())) {
                return v;
            }
        }
        return null;
    }

    /**
     * Adds a directed edge (a one-way route) between two vertices.
     * Time Complexity: O(n) due to vertex lookup.
     *
     * @param originId The ID of the starting vertex.
     * @param destId The ID of the destination vertex.
     * @param cost The risk weight of this route.
     * @throws IllegalArgumentException if either vertex ID is not found or cost is negative.
     */
    public void addDirectedEdge(String originId, String destId, int cost) {
        if (cost < 0) {
            throw new IllegalArgumentException("Risk weight cannot be negative.");
        }

        Vertex origin = findVertex(originId);
        Vertex destination = findVertex(destId);

        if (origin == null || destination == null) {
            throw new IllegalArgumentException("Invalid Vertex ID. Origin or Destination not found.");
        }
        // Explicitly block self-loops to align with tests
        if (origin.equals(destination)) {
            throw new IllegalArgumentException("Self-loops are not allowed.");
        }

        origin.addEdge(destination, cost);
    }

    /**
     * Adds an undirected edge (a two-way street) between two vertices.
     * This creates two directed edges in opposite directions.
     * Time Complexity: O(n) due to vertex lookup.
     *
     * @param vertex1Id The ID of the first vertex.
     * @param vertex2Id The ID of the second vertex.
     * @param cost The risk weight, assumed to be the same in both directions.
     * @throws IllegalArgumentException if either vertex ID is not found or cost is negative.
     */
    public void addUndirectedEdge(String vertex1Id, String vertex2Id, int cost) {
        addDirectedEdge(vertex1Id, vertex2Id, cost);
        addDirectedEdge(vertex2Id, vertex1Id, cost);
    }

    public MyLinkedList<Vertex> getVertices() {
        return vertices;
    }

    /**
     * Resets the temporary fields of all vertices.
     * Essential for running the search algorithm multiple times.
     */
    private void resetGraphState() {
        for (int i = 0; i < vertices.size(); i++) {
            Vertex v = vertices.get(i);

            // Campos do Dijkstra
            v.tempMinRisk = Integer.MAX_VALUE;
            v.tempPrevious = null;

            // Campos do DFS (Novos)
            v.isVisited = false;
            v.dfsNum = -1;
            v.lowLink = -1;
            v.parent = null;
            v.tempIsArticulationPoint = false;
        }
    }

    /**
     * Reconstructs the path (in the correct order)
     * following the 'tempPrevious' pointers of the destination vertex.
     */
    private MyLinkedList<Vertex> reconstructPath(Vertex target) {
        MyLinkedList<Vertex> path = new MyLinkedList<>();
        Vertex current = target;

        while (current != null) {
            path.addFirst(current);
            current = current.tempPrevious;
        }
        return path;
    }

    /**
     * Finds the route with the lowest accumulated risk between two locations
     * using Dijkstra's Algorithm.
     *
     * @param originId The ID of the origin vertex.
     * @param destId The ID of the destination vertex.
     * @return A MyLinkedList containing the vertices in the order of the safest route,
     * or an empty list if no path is found.
     */
    public MyLinkedList<Vertex> findSafestRoute(String originId, String destId) {
        // PASSO 1: Inicializar todos os vértices
        resetGraphState(); // Define todos com risco ∞, exceto origem (risco = 0)

        Vertex origin = findVertex(originId);
        Vertex destination = findVertex(destId);

        if (origin == null || destination == null) {
            throw new IllegalArgumentException("Origem ou Destino inválido.");
        }

        // PASSO 2: Criar fila de prioridade (Min-Heap)
        MyMinHeap<Vertex> priorityQueue = new MyMinHeap<>();

        origin.tempMinRisk = 0; // Origem tem risco zero
        priorityQueue.insert(origin);

        // PASSO 3: Processar vértices do menor ao maior risco
        while (!priorityQueue.isEmpty()) {

            Vertex currentVertex = priorityQueue.extractMin(); // O(log n)

            // Se chegou no destino, para!
            if (currentVertex.equals(destination)) {
                return reconstructPath(destination); // Reconstrói caminho
            }

            // PASSO 4: Atualizar vizinhos (relaxamento)
            MyLinkedList<Edge> edges = currentVertex.getEdges();
            for (int i = 0; i < edges.size(); i++) {
                Edge edge = edges.get(i);
                Vertex neighbor = edge.getDestination();

                int newRisk = currentVertex.tempMinRisk + edge.getCost();

                // Se encontrou caminho melhor, atualiza!
                if (newRisk < neighbor.tempMinRisk) {
                    neighbor.tempMinRisk = newRisk;       // Atualiza risco
                    neighbor.tempPrevious = currentVertex; // Marca caminho
                    priorityQueue.insert(neighbor);        // Adiciona à fila
                }
            }
        }

        return new MyLinkedList<>(); // Sem caminho
    }

    /**
     * Finds all Articulation Points (critical locations) in the graph.
     *
     * @return A MyLinkedList containing all Vertices
     * that are critical points.
     */
    public MyLinkedList<Vertex> findCriticalPoints() {

        resetGraphState(); // Limpa marcações anteriores
        dfsTime = 0;       // Contador de tempo de descoberta

        MyLinkedList<Vertex> articulationPoints = new MyLinkedList<>();

        // PASSO 1: Executar DFS em todos os componentes
        for (int i = 0; i < vertices.size(); i++) {
            Vertex v = vertices.get(i);
            if (!v.isVisited) {
                dfsArticulation(v); // Inicia exploração profunda
            }
        }

        // PASSO 2: Coletar vértices marcados como pontos de articulação
        for (int i = 0; i < vertices.size(); i++) {
            Vertex v = vertices.get(i);
            if (v.tempIsArticulationPoint) {
                articulationPoints.add(v);
            }
        }

        return articulationPoints;
    }

    /**
     * DFS recursive engine for finding Articulation Points.
     * <p>
     * This method modifies the 'temp' fields in the Vertices.
     *
     * @param u The current vertex being visited.
     */
    private void dfsArticulation(Vertex u) {
        // PASSO 1: Marcar vértice como visitado e atribuir números
        u.isVisited = true;
        dfsTime++;
        u.dfsNum = u.lowLink = dfsTime; // Tempo de descoberta
        int childrenCount = 0;          // Contador de filhos na árvore DFS

        // PASSO 2: Explorar todos os vizinhos
        MyLinkedList<Edge> edges = u.getEdges();
        for (int i = 0; i < edges.size(); i++) {
            Vertex v = edges.get(i).getDestination();

            if (v.equals(u.parent)) continue; // Ignora aresta de retorno ao pai

            if (v.isVisited) {
                // CASO 1: Vizinho já visitado (back edge)
                u.lowLink = Math.min(u.lowLink, v.dfsNum); // Atualiza lowLink
            } else {
                // CASO 2: Vizinho não visitado (tree edge)
                childrenCount++;
                v.parent = u;             // Define pai
                dfsArticulation(v);       // RECURSÃO: Explora filho

                u.lowLink = Math.min(u.lowLink, v.lowLink); // Propaga lowLink

                // REGRA 1: Raiz com múltiplos filhos é ponto crítico
                if (u.parent == null && childrenCount > 1) {
                    u.tempIsArticulationPoint = true;
                }

                // REGRA 2: Vértice interno cujo filho não consegue voltar
                if (u.parent != null && v.lowLink >= u.dfsNum) {
                    u.tempIsArticulationPoint = true;
                }
            }
        }
    }
}
