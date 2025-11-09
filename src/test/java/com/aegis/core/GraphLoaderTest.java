package com.aegis.core;

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
        assertEquals(10, v1.getEdges().get(0).getRiskWeight());

        assertEquals(2, v2.getEdges().size());

        Edge v2_v3 = v2.getEdges().get(1);
        assertEquals(v3, v2_v3.getDestination());
        assertEquals(20, v2_v3.getRiskWeight());
    }

    @Test
    void testLoadGraphThrowsFileNotFound() {
        assertThrows(FileNotFoundException.class, () -> loader.loadGraph("bad_path.csv", "bad_path_2.csv"));
    }
}
