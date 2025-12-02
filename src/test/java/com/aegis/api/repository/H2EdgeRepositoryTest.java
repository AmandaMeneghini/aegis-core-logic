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

    @Test
    void findAll_shouldReturnListOfEdges_whenDataExists() {
        EdgeEntity edge1 = new EdgeEntity("A", "B", 5, 100);
        EdgeEntity edge2 = new EdgeEntity("B", "C", 3, 150);
        List<EdgeEntity> expectedEdges = List.of(edge1, edge2);

        when(jdbcTemplate.query(anyString(), any(RowMapper.class))).thenReturn(expectedEdges);

        List<EdgeEntity> actualEdges = h2EdgeRepository.findAll();

        assertNotNull(actualEdges, "A lista de arestas não deve ser nula.");
        assertEquals(expectedEdges.size(), actualEdges.size(), "O tamanho da lista de arestas deve ser o esperado.");
        assertEquals(expectedEdges, actualEdges, "A lista de arestas retornada deve ser igual à lista esperada.");
    }

    @Test
    void findAll_shouldReturnEmptyList_whenNoDataExists() {
        when(jdbcTemplate.query(anyString(), any(RowMapper.class))).thenReturn(Collections.emptyList());

        List<EdgeEntity> actualEdges = h2EdgeRepository.findAll();

        assertNotNull(actualEdges, "A lista de arestas não deve ser nula.");
        assertEquals(0, actualEdges.size(), "A lista de arestas deve estar vazia.");
    }
}
