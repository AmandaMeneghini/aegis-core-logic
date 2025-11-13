package com.aegis.api.repository;

import com.aegis.api.entity.VertexEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * H2 Database implementation of IVertexRepository.
 * Can be easily replaced with other implementations (MySQL, PostgreSQL, MongoDB, etc.)
 */
@Repository
public class H2VertexRepository implements com.aegis.api.repository.IVertexRepository {

    private final JdbcTemplate jdbcTemplate;

    public H2VertexRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<VertexEntity> findAll() {
        return jdbcTemplate.query(
            "SELECT id, name FROM vertices",
            (rs, rowNum) -> new VertexEntity(
                rs.getString("id"),
                rs.getString("name")
            )
        );
    }
}
