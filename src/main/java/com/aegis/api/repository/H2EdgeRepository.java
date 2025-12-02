package com.aegis.api.repository;

import com.aegis.api.entity.EdgeEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * H2 Database implementation of IEdgeRepository.
 * Can be easily replaced with other implementations (MySQL, PostgreSQL, MongoDB, etc.)
 */
@Repository
public class H2EdgeRepository implements IEdgeRepository {

    private final JdbcTemplate jdbcTemplate;

    public H2EdgeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<EdgeEntity> findAll() {
        return jdbcTemplate.query(
            "SELECT origin_id, dest_id, risk, distance FROM edges",
            (rs, rowNum) -> new EdgeEntity(
                rs.getString("origin_id"),
                rs.getString("dest_id"),
                rs.getInt("risk"),
                rs.getInt("distance")
            )
        );
    }
}

