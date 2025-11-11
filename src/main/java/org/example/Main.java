package org.example;

import com.aegis.core.Graph;
import com.aegis.core.GraphLoader;
import com.aegis.core.MyLinkedList;
import com.aegis.core.Vertex;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        System.out.println("=== 🛡️  Inicializando Sistema Aegis... ===");

        // --- Passo 1: Carregar o Grafo ---
        GraphLoader loader = new GraphLoader();
        Graph graph;

        // Caminhos relativos da raiz do projeto
        String verticesPath = "src/main/resources/vertices.csv";
        String edgesPath = "src/main/resources/edges.csv";

        try {
            graph = loader.loadGraph(verticesPath, edgesPath);
            System.out.println("Mapa de risco carregado com sucesso!");
            System.out.println("Locais carregados: " + graph.getVertices().size());
        } catch (FileNotFoundException e) {
            System.err.println("ERRO CRÍTICO: Não foi possível carregar os arquivos do mapa.");
            System.err.println(e.getMessage());
            return; // Encerra o programa se não puder carregar o mapa
        }

        // --- Passo 2: Loop Interativo (CLI) ---
        Scanner inputScanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n--- 🛡️  Menu Principal Aegis 🛡️ ---");
            System.out.println("1. Calcular Rota Mais Segura");
            System.out.println("2. Identificar Locais Críticos (Pontos de Falha)");
            System.out.println("Digite 'sair' para encerrar.");
            System.out.print("Escolha uma opção: ");

            String choice = inputScanner.nextLine().trim();

            switch (choice) {
                case "1":
                    // --- Lógica da Rota Segura (que já tínhamos) ---
                    System.out.print("Digite o ID da Origem (ex: AG-01): ");
                    String originId = inputScanner.nextLine().trim();
                    System.out.print("Digite o ID do Destino (ex: ATM-02): ");
                    String destId = inputScanner.nextLine().trim();

                    try {
                        MyLinkedList<Vertex> path = graph.findSafestRoute(originId, destId);
                        printPath(path, originId, destId); // (o método printPath continua o mesmo)
                    } catch (IllegalArgumentException e) {
                        System.err.println("ERRO: ID de origem ou destino não encontrado.");
                    }
                    break;

                case "2":
                    // --- Lógica dos Pontos Críticos (NOVO) ---
                    System.out.println("\nCalculando locais críticos na rede...");
                    MyLinkedList<Vertex> criticalPoints = graph.findCriticalPoints();

                    if (criticalPoints.isEmpty()) {
                        System.out.println("Resultado: Nenhum ponto crítico encontrado. A rede é robusta.");
                    } else {
                        System.out.println("Resultado: Pontos de falha encontrados!");
                        System.out.println("Bloquear qualquer um destes locais pode isolar partes da rede:");
                        for (int i = 0; i < criticalPoints.size(); i++) {
                            Vertex v = criticalPoints.get(i);
                            System.out.println("- " + v.getName() + " (ID: " + v.getId() + ")");
                        }
                    }
                    break;

                case "sair":
                    inputScanner.close();
                    System.out.println("=== 🛡️  Sistema Aegis Desligado. ===");
                    return; // Encerra o main()

                default:
                    System.err.println("Opção inválida. Por favor, digite 1, 2 ou 'sair'.");
            }
        }
    }

    /**
     * Auxiliary method for printing the route in a readable format.
     *
     * @param path The list of vertices returned by Dijkstra.
     */
    private static void printPath(MyLinkedList<Vertex> path, String originId, String destId) {
        if (path.isEmpty()) {
            System.out.println("\nResultado: Nenhuma rota segura encontrada entre " + originId + " e " + destId + ".");
            return;
        }

        System.out.println("\nResultado: Rota mais segura encontrada!");

        int totalRisk = path.get(path.size() - 1).tempMinRisk; // Risco total está no destino
        System.out.println("Risco Total Acumulado: " + totalRisk);

        for (int i = 0; i < path.size(); i++) {
            Vertex v = path.get(i);
            System.out.print(v.getName() + " (ID: " + v.getId() + ")");
            if (i < path.size() - 1) {
                System.out.print(" -> ");
            }
        }
        System.out.println("\n--- Fim da Rota ---");
    }
}