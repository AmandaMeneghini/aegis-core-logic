package com.aegis.api.repository;

import com.aegis.api.entity.EdgeEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class H2EdgeRepositoryTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private H2EdgeRepository h2EdgeRepository;

    // Verifica se o método findAll retorna uma lista de arestas quando o banco de dados contém dados.
    // Este é o cenário de caso de uso principal para garantir que a recuperação de dados funcione como esperado.
    @Test
    void findAll_shouldReturnListOfEdges_whenDataExists() {
        // Arrange
        EdgeEntity edge1 = new EdgeEntity("A", "B", 5, 100);
        EdgeEntity edge2 = new EdgeEntity("B", "C", 3, 150);
        List<EdgeEntity> expectedEdges = List.of(edge1, edge2);

        when(jdbcTemplate.query(anyString(), any(RowMapper.class))).thenReturn(expectedEdges);

        // Act
        List<EdgeEntity> actualEdges = h2EdgeRepository.findAll();

        // Assert
        assertNotNull(actualEdges, "A lista de arestas não deve ser nula.");
        assertEquals(expectedEdges.size(), actualEdges.size(), "O tamanho da lista de arestas deve ser o esperado.");
        assertEquals(expectedEdges, actualEdges, "A lista de arestas retornada deve ser igual à lista esperada.");
    }

    // Verifica se o método findAll retorna uma lista vazia quando o banco de dados não contém dados.
    // Este teste de caso de borda garante que o método se comporte corretamente e não retorne nulo ou lance uma exceção quando a tabela estiver vazia.
    @Test
    void findAll_shouldReturnEmptyList_whenNoDataExists() {
        // Arrange
        when(jdbcTemplate.query(anyString(), any(RowMapper.class))).thenReturn(Collections.emptyList());

        // Act
        List<EdgeEntity> actualEdges = h2EdgeRepository.findAll();

        // Assert
        assertNotNull(actualEdges, "A lista de arestas não deve ser nula.");
        assertEquals(0, actualEdges.size(), "A lista de arestas deve estar vazia.");
    }
}
