package com.aegis.api;

import com.aegis.core.graph.Graph;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;

import java.sql.ResultSet; // Importa ResultSet
import java.sql.SQLException; // Importa SQLException

@Service
public class GraphService {

    private final JdbcTemplate jdbcTemplate;
    private Graph graph;

    public GraphService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostConstruct
    public void initializeGraph() {
        this.graph = new Graph();
        System.out.println("--- 🛡️ Carregando Grafo do Banco H2 ---");

        // --- Passo 1: Carrega Vértices do H2 ---
        // CORREÇÃO: Usando uma classe anônima 'RowCallbackHandler'
        // para remover 100% da ambiguidade do compilador.
        jdbcTemplate.query("SELECT id, name FROM vertices", new RowCallbackHandler() {
            @Override
            public void processRow(ResultSet rs) throws SQLException {
                graph.addVertex(
                        rs.getString("id"),
                        rs.getString("name")
                );
            }
        });

        // --- Passo 2: Carrega Arestas do H2 ---
        // CORREÇÃO: Usando a mesma técnica aqui.
        jdbcTemplate.query("SELECT origin_id, dest_id, risk, distance FROM edges", new RowCallbackHandler() {
            @Override
            public void processRow(ResultSet rs) throws SQLException {
                int risk = rs.getInt("risk");
                int distance = rs.getInt("distance");

                // --- A SUA MÉTRICA ESTÁ AQUI ---
                int finalCost = (risk * 10) + (distance / 100);

                graph.addDirectedEdge(
                        rs.getString("origin_id"),
                        rs.getString("dest_id"),
                        finalCost
                );
            }
        });

        System.out.println("Grafo carregado com " + graph.getVertices().size() + " locais.");
    }

    public Graph getGraph() {
        return this.graph;
    }
}