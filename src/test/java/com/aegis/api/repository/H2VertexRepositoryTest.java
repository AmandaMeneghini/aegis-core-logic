package com.aegis.api.repository;

import com.aegis.api.entity.VertexEntity;
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
class H2VertexRepositoryTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private H2VertexRepository h2VertexRepository;

    // Verifica se o método findAll retorna uma lista de vértices quando o banco de dados contém dados.
    // Este é o cenário de caso de uso principal para garantir que a recuperação de dados funcione como esperado.
    @Test
    void findAll_shouldReturnListOfVertices_whenDataExists() {
        // Arrange
        VertexEntity vertex1 = new VertexEntity("1", "Vertex A");
        VertexEntity vertex2 = new VertexEntity("2", "Vertex B");
        List<VertexEntity> expectedVertices = List.of(vertex1, vertex2);

        when(jdbcTemplate.query(anyString(), any(RowMapper.class))).thenReturn(expectedVertices);

        // Act
        List<VertexEntity> actualVertices = h2VertexRepository.findAll();

        // Assert
        assertNotNull(actualVertices, "A lista de vértices não deve ser nula.");
        assertEquals(2, actualVertices.size(), "O tamanho da lista de vértices deve ser 2.");
        assertEquals(expectedVertices, actualVertices, "A lista de vértices retornada deve ser igual à lista esperada.");
    }

    // Verifica se o método findAll retorna uma lista vazia quando o banco de dados não contém dados.
    // Este teste de caso de borda garante que o método se comporte corretamente e não retorne nulo ou lance uma exceção quando a tabela estiver vazia.
    @Test
    void findAll_shouldReturnEmptyList_whenNoDataExists() {
        // Arrange
        when(jdbcTemplate.query(anyString(), any(RowMapper.class))).thenReturn(Collections.emptyList());

        // Act
        List<VertexEntity> actualVertices = h2VertexRepository.findAll();

        // Assert
        assertNotNull(actualVertices, "A lista de vértices não deve ser nula.");
        assertEquals(0, actualVertices.size(), "A lista de vértices deve estar vazia.");
    }
}
