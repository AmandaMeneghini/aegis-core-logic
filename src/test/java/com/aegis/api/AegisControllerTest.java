package com.aegis.api;

import com.aegis.api.dto.RouteResponseDTO;
import com.aegis.core.graph.Graph;
import com.aegis.core.graph.Vertex;
import com.aegis.core.datastructures.MyLinkedList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * O foco principal é isolar o controlador e verificar se ele:
 * 1. Responde corretamente às requisições HTTP.
 * 2. Interage de forma adequada com suas dependências (neste caso, `IGraphService`).
 * 3. Mapeia os dados recebidos do serviço para os Data Transfer Objects (DTOs) corretos.
 * 4. Lida apropriadamente com cenários de sucesso, casos de borda e erros.
 * A anotação `@ExtendWith(MockitoExtension.class)` inicializa o Mockito, permitindo a criação e injeção de mocks.
 */
@ExtendWith(MockitoExtension.class)
class AegisControllerTest {

    /**
     * PAPEL DOS MOCKS E DEPENDÊNCIAS INJETADAS:
     *
     * @Mock private IGraphService graphService;
     * Cria um "dublê" (mock) da interface `IGraphService`. O objetivo é simular o comportamento
     * desta dependência sem precisar de uma implementação real. Isso isola o teste apenas para a lógica do controlador.
     *
     * @Mock private Graph graph;
     * Cria um mock da classe `Graph`. Como o controlador depende indiretamente do grafo através do `graphService`,
     * mockar o grafo nos permite controlar diretamente os resultados de suas operações (ex: `findSafestRoute`).
     *
     * @Mock private MyLinkedList<Vertex> pathOrPoints;
     * Cria um mock da estrutura de dados `MyLinkedList`. É usado em testes mais antigos para simular
     * uma lista de resultados de forma genérica.
     *
     * @InjectMocks private AegisController controller;
     * Cria uma instância real da `AegisController` e injeta automaticamente os mocks criados com `@Mock`
     * em seus campos correspondentes. Isso nos dá um objeto `AegisController` pronto para ser testado,
     * mas que utiliza nossos dublês em vez de suas dependências reais.
     */
    @Mock
    private IGraphService graphService;

    @Mock
    private Graph graph;

    @Mock
    private MyLinkedList<Vertex> pathOrPoints;

    @InjectMocks
    private AegisController controller;

    /**
     * OBJETIVO DO MÉTODO DE SETUP (@BeforeEach):
     * Este método é executado antes de cada teste (@Test) nesta classe.
     * Sua função é garantir que o ambiente de teste seja consistente e previsível para cada cenário.
     *
     * O que `when(graphService.getGraph()).thenReturn(graph);` faz:
     * Configura o comportamento do mock `graphService`. Toda vez que o método `getGraph()` for chamado
     * dentro de um teste, ele deverá retornar o nosso mock `graph`. Isso estabelece a ligação
     * entre o serviço e o grafo, permitindo que os testes controlem o comportamento do grafo.
     */
    @BeforeEach
    void setUp() {
        // Arranjo (Arrange) comum a todos os testes:
        // Instruímos o mock 'graphService' a retornar o mock 'graph' sempre que seu método 'getGraph()' for invocado.
        when(graphService.getGraph()).thenReturn(graph);
    }

    /**
     * RESPONSABILIDADE DO TESTE:
     * Verifica o comportamento do endpoint `getSafestRoute` quando nenhum caminho é encontrado.
     *
     * CENÁRIO COBERTO:
     * - Caso de Borda (Edge Case): O grafo retorna uma lista vazia.
     * - Ramo (Branch): Testa o `if (path.isEmpty())` no controlador, especificamente o caminho `true`.
     *
     * ASSERÇÕES E VERIFICAÇÕES:
     * - `assertNotNull(response)`: Garante que o controlador sempre retorna um objeto de resposta, nunca nulo.
     * - `assertTrue(response.getStatusCode().is2xxSuccessful())`: Confirma que o status HTTP é de sucesso (ex: 200 OK).
     * - `assertNotNull(response.getBody())`: Assegura que a resposta tem um corpo, mesmo que vazio.
     * - `verify(graph, times(1)).findSafestRoute("A", "B")`: Confirma que o método `findSafestRoute` do grafo foi chamado exatamente uma vez.
     * - `verify(pathOrPoints, never()).get(anyInt())`: Crucial para este cenário, verifica que o controlador não tentou acessar nenhum elemento da lista vazia, prevenindo `IndexOutOfBoundsException`.
     */
    @Test
    void getSafestRoute_whenPathEmpty_returnsOkWithEmptyRoute() {
        // Arrange: Configura o mock do grafo para retornar uma lista mockada e vazia.
        when(graph.findSafestRoute("A", "B")).thenReturn(pathOrPoints);
        when(pathOrPoints.isEmpty()).thenReturn(true);

        // Act: Executa o método do controlador.
        ResponseEntity<?> response = controller.getSafestRoute("A", "B");

        // Assert: Verifica se o resultado é o esperado.
        assertNotNull(response);
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertNotNull(response.getBody());
        verify(graph, times(1)).findSafestRoute("A", "B");
        verify(pathOrPoints, never()).get(anyInt());
    }


    /**
     * RESPONSABILIDADE DO TESTE:
     * Verifica o comportamento do endpoint `getCriticalPoints` quando pontos críticos são encontrados.
     *
     * CENÁRIO COBERTO:
     * - Caminho Feliz (Happy Path): O grafo retorna uma lista com múltiplos pontos críticos.
     * - Ramo (Branch): Testa o loop `for` no controlador, garantindo que ele itera sobre a lista.
     *
     * ASSERÇÕES E VERIFICAÇÕES:
     * - `assertNotNull` e `assertTrue`: Verificações padrão de que a resposta HTTP é válida e bem-sucedida.
     * - `verify(points, times(1)).get(0)` e `verify(points, times(1)).get(1)`: Confirmam que o controlador acessou os elementos da lista para convertê-los em DTOs.
     * - `verify(p1, atLeastOnce()).getName()`: Garante que os dados dos vértices (nome e ID) foram lidos para a criação dos DTOs.
     */
    @Test
    void getCriticalPoints_whenPointsFound_returnsOkAndConvertsVertices() {
        // Arrange: Configura mocks para simular uma lista com dois vértices.
        @SuppressWarnings("unchecked")
        MyLinkedList<Vertex> points = mock(MyLinkedList.class);
        Vertex p1 = mock(Vertex.class);
        Vertex p2 = mock(Vertex.class);

        when(graph.findCriticalPoints()).thenReturn(points);
        when(points.size()).thenReturn(2);
        when(points.get(0)).thenReturn(p1);
        when(points.get(1)).thenReturn(p2);

        when(p1.getName()).thenReturn("P1");
        when(p1.getId()).thenReturn("101");
        when(p2.getName()).thenReturn("P2");
        when(p2.getId()).thenReturn("102");

        // Act: Chama o método do controlador.
        ResponseEntity<?> response = controller.getCriticalPoints();

        // Assert: Verifica se a resposta está correta e se as interações com os mocks ocorreram como esperado.
        assertNotNull(response);
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertNotNull(response.getBody());

        verify(graph, times(1)).findCriticalPoints();
        verify(points, times(1)).get(0);
        verify(points, times(1)).get(1);
        verify(p1, atLeastOnce()).getName();
        verify(p1, atLeastOnce()).getId();
        verify(p2, atLeastOnce()).getName();
        verify(p2, atLeastOnce()).getId();
    }

    /**
     * RESPONSABILIDADE DO TESTE:
     * Verifica o comportamento do endpoint `getCriticalPoints` quando nenhum ponto crítico é encontrado.
     *
     * CENÁRIO COBERTO:
     * - Caso de Borda (Edge Case): O grafo retorna uma lista vazia.
     * - Ramo (Branch): Testa o loop `for` no controlador, que neste caso não deve executar nenhuma iteração.
     *
     * ASSERÇÕES E VERIFICAÇÕES:
     * - `verify(points, never()).get(anyInt())`: Confirma que o controlador não tentou acessar nenhum elemento da lista, pois ela está vazia. Isso é fundamental para garantir a segurança do código.
     */
    @Test
    void getCriticalPoints_whenNoPoints_returnsOkWithZeroCount() {
        // Arrange: Configura o mock do grafo para retornar uma lista mockada e vazia.
        @SuppressWarnings("unchecked")
        MyLinkedList<Vertex> points = mock(MyLinkedList.class);

        when(graph.findCriticalPoints()).thenReturn(points);
        when(points.size()).thenReturn(0);

        // Act: Chama o método do controlador.
        ResponseEntity<?> response = controller.getCriticalPoints();

        // Assert: Verifica a resposta e o comportamento.
        assertNotNull(response);
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertNotNull(response.getBody());

        verify(graph, times(1)).findCriticalPoints();
        verify(points, never()).get(anyInt());
    }

    /**
     * RESPONSABILIDADE DO TESTE:
     * Verifica o cenário de sucesso ("caminho feliz") do endpoint `getSafestRoute`.
     *
     * CENÁRIO COBERTO:
     * - Caminho Feliz (Happy Path): O grafo encontra e retorna um caminho válido com múltiplos vértices.
     * - Ramo (Branch): Testa o `if (path.isEmpty())` (caminho `false`) e o loop `for` de conversão para DTO.
     *
     * ASSERÇÕES E VERIFICAÇÕES:
     * - `assertEquals(HttpStatus.OK, ...)`: Garante que o status da resposta é exatamente 200 OK.
     * - `assertEquals(150, ...)`: Confirma que o custo total foi extraído corretamente do último vértice do caminho.
     * - `assertEquals(2, ...)`: Verifica se todos os vértices do caminho foram convertidos em passos da rota no DTO.
     * - `assertEquals("Ponto A", ...)`: Valida se os dados (nome, ID) foram mapeados corretamente para o DTO.
     */
    @Test
    void getSafestRoute_shouldReturnOkAndRoute_whenPathIsFound() {
        // Arrange: Cria objetos reais para simular um caminho válido.
        // Isso é preferível a mocks complexos para testar a lógica de iteração do controlador.
        Vertex v1 = new Vertex("A", "Ponto A");
        Vertex v2 = new Vertex("B", "Ponto B");
        v2.tempMinRisk = 150; // Simula o custo acumulado no final do caminho.

        MyLinkedList<Vertex> path = new MyLinkedList<>();
        path.add(v1);
        path.add(v2);

        // Configura o mock do grafo para retornar este caminho.
        when(graph.findSafestRoute("A", "B")).thenReturn(path);

        // Act: Chama o método do controlador.
        ResponseEntity<RouteResponseDTO> response = controller.getSafestRoute("A", "B");

        // Assert: Valida a resposta HTTP e o conteúdo do corpo da resposta.
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        RouteResponseDTO responseBody = response.getBody();
        assertNotNull(responseBody);
        assertEquals(150, responseBody.totalCalculatedCost());
        assertEquals(2, responseBody.route().size());
        assertEquals("Ponto A", responseBody.route().get(0).name());
        assertEquals("B", responseBody.route().get(1).id());

        verify(graph, times(1)).findSafestRoute("A", "B");
    }

    /**
     * RESPONSABILIDADE DO TESTE:
     * Verifica o tratamento de erro quando um vértice de origem ou destino não existe.
     *
     * CENÁRIO COBERTO:
     * - Caminho de Erro (Error Path): O serviço (`graph`) lança uma exceção indicando que um dado é inválido.
     *
     * ASSERÇÕES E VERIFICAÇÕES:
     * - `assertThrows(NoSuchElementException.class, ...)`: Esta é a asserção principal. Ela executa o código do controlador
     *   e afirma que uma exceção do tipo `NoSuchElementException` DEVE ser lançada. O teste falhará se nenhuma exceção
     *   ou uma exceção de tipo diferente for lançada. Isso confirma que o controlador não "engole" o erro.
     * - `assertEquals(errorMessage, ...)`: Garante que a mensagem da exceção original foi preservada.
     * - `verify(graph, ...)`: Confirma que a chamada ao método que causou o erro de fato ocorreu.
     */
    @Test
    void getSafestRoute_shouldThrowException_whenOriginOrDestinationNotFound() {
        // Arrange: Configura o mock para lançar uma exceção quando o método for chamado com parâmetros específicos.
        String errorMessage = "Vértice de origem 'Z' não encontrado.";
        when(graph.findSafestRoute("Z", "B")).thenThrow(new NoSuchElementException(errorMessage));

        // Act & Assert: Executa a chamada e verifica se a exceção esperada é lançada.
        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> {
            controller.getSafestRoute("Z", "B");
        });

        // Verifica se a exceção contém a mensagem correta.
        assertEquals(errorMessage, exception.getMessage());
        verify(graph, times(1)).findSafestRoute("Z", "B");
    }
}
