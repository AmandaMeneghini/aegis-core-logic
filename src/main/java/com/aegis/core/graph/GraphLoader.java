package com.aegis.core.graph;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Utility class to load a graph from external .csv files.
 * This decouples the graph data from the application logic.
 */
public class GraphLoader {

    /**
     * Loads a graph from specified vertex and edge file paths.
     *
     * @param vertexFilePath Path to the vertices.csv file.
     * @param edgeFilePath   Path to the edges.csv file.
     * @return A new Graph object populated with data.
     * @throws FileNotFoundException if any file path is invalid.
     */
    public Graph loadGraph(String vertexFilePath, String edgeFilePath) throws FileNotFoundException {
        Graph graph = new Graph();

        // --- Passo 1: Carregar Vértices ---
        // Usamos try-with-resources para fechar o Scanner automaticamente
        File vertexFile = new File(vertexFilePath);
        try (Scanner scanner = new Scanner(vertexFile)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.trim().isEmpty()) { // Pula linhas em branco
                    continue;
                }

                String[] parts = line.split(",");
                if (parts.length >= 2) { // Garante que temos 2 colunas
                    String id = parts[0].trim();
                    String name = parts[1].trim();
                    graph.addVertex(id, name);
                }
            }
        } // Scanner é fechado aqui (mesmo se der erro)

        // --- Passo 2: Carregar Arestas ---
        File edgeFile = new File(edgeFilePath);
        try (Scanner scanner = new Scanner(edgeFile)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] parts = line.split(",");
                if (parts.length >= 3) { // Garante que temos 3 colunas
                    String originId = parts[0].trim();
                    String destId = parts[1].trim();
                    // Converte a String de risco para inteiro
                    int risk = Integer.parseInt(parts[2].trim());
                    graph.addUndirectedEdge(originId, destId, risk);
                }
            }
        } // Scanner é fechado aqui

        // --- Passo 3: Retornar o Grafo ---
        return graph;
    }
}
