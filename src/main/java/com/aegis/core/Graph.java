package com.aegis.core;

/**
 * The main Graph class.
 * It manages all the vertices and provides methods to build the map.
 */
public class Graph {

    private final MyLinkedList<Vertex> vertices;

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
     * @param riskWeight The risk weight of this route.
     * @throws IllegalArgumentException if either vertex ID is not found or riskWeight is negative.
     */
    public void addDirectedEdge(String originId, String destId, int riskWeight) {
        if (riskWeight < 0) {
            throw new IllegalArgumentException("Risk weight cannot be negative.");
        }

        Vertex origin = findVertex(originId);
        Vertex destination = findVertex(destId);

        if (origin == null || destination == null) {
            throw new IllegalArgumentException("Invalid Vertex ID. Origin or Destination not found.");
        }

        origin.addEdge(destination, riskWeight);
    }

    /**
     * Adds an undirected edge (a two-way street) between two vertices.
     * This creates two directed edges in opposite directions.
     * Time Complexity: O(n) due to vertex lookup.
     *
     * @param vertex1Id The ID of the first vertex.
     * @param vertex2Id The ID of the second vertex.
     * @param riskWeight The risk weight, assumed to be the same in both directions.
     * @throws IllegalArgumentException if either vertex ID is not found or riskWeight is negative.
     */
    public void addUndirectedEdge(String vertex1Id, String vertex2Id, int riskWeight) {
        addDirectedEdge(vertex1Id, vertex2Id, riskWeight);
        addDirectedEdge(vertex2Id, vertex1Id, riskWeight);
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
            v.tempMinRisk = Integer.MAX_VALUE;
            v.tempPrevious = null;
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
     * Encontra a rota com o menor risco acumulado entre dois locais
     * usando o Algoritmo de Dijkstra.
     *
     * @param originId ID do vértice de origem.
     * @param destId ID do vértice de destino.
     * @return Uma MyLinkedList com os vértices na ordem da rota mais segura,
     * ou uma lista vazia se nenhum caminho for encontrado.
     */
    public MyLinkedList<Vertex> findSafestRoute(String originId, String destId) {
        resetGraphState();

        Vertex origin = findVertex(originId);
        Vertex destination = findVertex(destId);

        if (origin == null || destination == null) {
            throw new IllegalArgumentException("Origem ou Destino inválido.");
        }

        MyMinHeap<Vertex> priorityQueue = new MyMinHeap<>();

        origin.tempMinRisk = 0;
        priorityQueue.insert(origin);

        while (!priorityQueue.isEmpty()) {

            Vertex currentVertex = priorityQueue.extractMin();

            if (currentVertex.equals(destination)) {
                return reconstructPath(destination);
            }

            MyLinkedList<Edge> edges = currentVertex.getEdges();
            for (int i = 0; i < edges.size(); i++) {
                Edge edge = edges.get(i);
                Vertex neighbor = edge.getDestination();

                int newRisk = currentVertex.tempMinRisk + edge.getRiskWeight();

                if (newRisk < neighbor.tempMinRisk) {
                    neighbor.tempMinRisk = newRisk;
                    neighbor.tempPrevious = currentVertex;
                    priorityQueue.insert(neighbor);
                }
            }
        }

        return new MyLinkedList<>();
    }
}
