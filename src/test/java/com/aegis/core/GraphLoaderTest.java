package com.aegis.core;

import com.aegis.core.graph.Edge;
import com.aegis.core.graph.Graph;
import com.aegis.core.graph.GraphLoader;
import com.aegis.core.graph.Vertex;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class GraphLoaderTest {

    private GraphLoader loader;

    @TempDir
    File tempFolder;

    private String vertexFilePath;
    private String edgeFilePath;

    @BeforeEach
    void setUp() throws IOException {
        loader = new GraphLoader();

        File vertexFile = new File(tempFolder, "test_vertices.csv");
        File edgeFile = new File(tempFolder, "test_edges.csv");

        try (FileWriter writer = new FileWriter(vertexFile)) {
            writer.write("V1,Vertex 1\n");
            writer.write("V2,Vertex 2\n");
            writer.write("V3,Vertex 3\n");
        }

        try (FileWriter writer = new FileWriter(edgeFile)) {
            writer.write("V1,V2,10\n");
            writer.write("V2,V3,20\n");
        }

        vertexFilePath = vertexFile.getAbsolutePath();
        edgeFilePath = edgeFile.getAbsolutePath();
    }

    @Test
    void testLoadGraph() throws FileNotFoundException {
        Graph graph = loader.loadGraph(vertexFilePath, edgeFilePath);

        assertEquals(3, graph.getVertices().size());
        assertNotNull(graph.findVertex("V1"));
        assertNotNull(graph.findVertex("V2"));
        assertNotNull(graph.findVertex("V3"));
        assertEquals("Vertex 2", graph.findVertex("V2").getName());


        Vertex v1 = graph.findVertex("V1");
        Vertex v2 = graph.findVertex("V2");
        Vertex v3 = graph.findVertex("V3");

        assertEquals(1, v1.getEdges().size());
        assertEquals(v2, v1.getEdges().get(0).getDestination());
        assertEquals(10, v1.getEdges().get(0).getCost());

        assertEquals(2, v2.getEdges().size());

        Edge v2_v3 = v2.getEdges().get(1);
        assertEquals(v3, v2_v3.getDestination());
        assertEquals(20, v2_v3.getCost());
    }

    @Test
    void testLoadGraphThrowsFileNotFound() {
        assertThrows(FileNotFoundException.class, () -> loader.loadGraph("bad_path.csv", "bad_path_2.csv"));
    }

    @Test
    void testLoadGraphWithEmptyLinesInVertexFile() throws IOException {
        File vertexFile = new File(tempFolder, "vertices_with_empty_lines.csv");
        File edgeFile = new File(tempFolder, "edges_empty.csv");

        try (FileWriter writer = new FileWriter(vertexFile)) {
            writer.write("V1,Vertex 1\n");
            writer.write("\n");
            writer.write("   \n");
            writer.write("V2,Vertex 2\n");
        }

        edgeFile.createNewFile();

        Graph graph = loader.loadGraph(vertexFile.getAbsolutePath(), edgeFile.getAbsolutePath());

        assertEquals(2, graph.getVertices().size());
        assertNotNull(graph.findVertex("V1"));
        assertNotNull(graph.findVertex("V2"));
    }

    @Test
    void testLoadGraphWithEmptyLinesInEdgeFile() throws IOException {
        File vertexFile = new File(tempFolder, "vertices_simple.csv");
        File edgeFile = new File(tempFolder, "edges_with_empty_lines.csv");

        try (FileWriter writer = new FileWriter(vertexFile)) {
            writer.write("V1,Vertex 1\n");
            writer.write("V2,Vertex 2\n");
        }

        try (FileWriter writer = new FileWriter(edgeFile)) {
            writer.write("V1,V2,10\n");
            writer.write("\n");
            writer.write("   \n");
        }

        Graph graph = loader.loadGraph(vertexFile.getAbsolutePath(), edgeFile.getAbsolutePath());

        Vertex v1 = graph.findVertex("V1");
        assertEquals(1, v1.getEdges().size());
    }

    @Test
    void testLoadGraphWithInvalidVertexLines() throws IOException {
        File vertexFile = new File(tempFolder, "vertices_invalid.csv");
        File edgeFile = new File(tempFolder, "edges_empty2.csv");

        try (FileWriter writer = new FileWriter(vertexFile)) {
            writer.write("V1,Vertex 1\n");
            writer.write("V2\n");
            writer.write("InvalidLine\n");
            writer.write("V3,Vertex 3\n");
        }

        // Cria arquivo de arestas vazio
        edgeFile.createNewFile();

        Graph graph = loader.loadGraph(vertexFile.getAbsolutePath(), edgeFile.getAbsolutePath());

        // Apenas V1 e V3 devem ser carregados (V2 e InvalidLine são ignoradas)
        assertEquals(2, graph.getVertices().size());
        assertNotNull(graph.findVertex("V1"));
        assertNull(graph.findVertex("V2"));
        assertNotNull(graph.findVertex("V3"));
    }

    @Test
    void testLoadGraphWithInvalidEdgeLines() throws IOException {
        File vertexFile = new File(tempFolder, "vertices_for_edges.csv");
        File edgeFile = new File(tempFolder, "edges_invalid.csv");

        try (FileWriter writer = new FileWriter(vertexFile)) {
            writer.write("V1,Vertex 1\n");
            writer.write("V2,Vertex 2\n");
            writer.write("V3,Vertex 3\n");
        }

        try (FileWriter writer = new FileWriter(edgeFile)) {
            writer.write("V1,V2,10\n");
            writer.write("V2,V3\n"); // Linha com apenas 2 colunas (inválida)
            writer.write("InvalidEdge\n"); // Linha sem formato correto (inválida)
        }

        Graph graph = loader.loadGraph(vertexFile.getAbsolutePath(), edgeFile.getAbsolutePath());

        Vertex v1 = graph.findVertex("V1");
        Vertex v2 = graph.findVertex("V2");
        Vertex v3 = graph.findVertex("V3");

        // Apenas a aresta V1->V2 deve existir
        assertEquals(1, v1.getEdges().size());
        assertEquals(1, v2.getEdges().size()); // bidirecional: V2->V1
        assertEquals(0, v3.getEdges().size()); // V3 não tem arestas
    }

    @Test
    void testLoadGraphWithExtraColumnsInVertexFile() throws IOException, FileNotFoundException {
        File vertexFile = new File(tempFolder, "vertices_extra_cols.csv");
        File edgeFile = new File(tempFolder, "edges_extra.csv");

        try (FileWriter writer = new FileWriter(vertexFile)) {
            writer.write("V1,Vertex 1,ExtraColumn,AnotherExtra\n");
            writer.write("V2,Vertex 2,Extra\n");
        }

        try (FileWriter writer = new FileWriter(edgeFile)) {
            // Arquivo vazio
        }

        Graph graph = loader.loadGraph(vertexFile.getAbsolutePath(), edgeFile.getAbsolutePath());

        // Deve carregar normalmente (parts.length >= 2)
        assertEquals(2, graph.getVertices().size());
        assertNotNull(graph.findVertex("V1"));
        assertNotNull(graph.findVertex("V2"));
    }

    @Test
    void testLoadGraphWithExtraColumnsInEdgeFile() throws IOException, FileNotFoundException {
        File vertexFile = new File(tempFolder, "vertices_for_extra_edges.csv");
        File edgeFile = new File(tempFolder, "edges_extra_cols.csv");

        try (FileWriter writer = new FileWriter(vertexFile)) {
            writer.write("V1,Vertex 1\n");
            writer.write("V2,Vertex 2\n");
        }

        try (FileWriter writer = new FileWriter(edgeFile)) {
            writer.write("V1,V2,15,ExtraColumn,AnotherExtra\n");
        }

        Graph graph = loader.loadGraph(vertexFile.getAbsolutePath(), edgeFile.getAbsolutePath());

        Vertex v1 = graph.findVertex("V1");
        // Deve carregar normalmente (parts.length >= 3)
        assertEquals(1, v1.getEdges().size());
        assertEquals(15, v1.getEdges().get(0).getCost());
    }
}
