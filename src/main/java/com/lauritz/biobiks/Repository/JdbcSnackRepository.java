package com.lauritz.biobiks.Repository;

import com.lauritz.biobiks.Model.Snack;
import com.lauritz.biobiks.Model.intface.SnackRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class JdbcSnackRepository implements SnackRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcSnackRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public List<Snack> findAll() {
        String sql = "SELECT * FROM snack";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new Snack(
                rs.getInt("id"),
                rs.getString("type"),
                rs.getString("size"),
                rs.getDouble("price")
        ));
    }

    @Override
    public Optional<Snack> findById(int id) {
        String sql = "SELECT * FROM snack WHERE id = ?";
        List<Snack> result = jdbcTemplate.query(sql, (rs, rowNum) -> new Snack(
                rs.getInt("id"),
                rs.getString("type"),
                rs.getString("size"),
                rs.getDouble("price")
        ), id);

        return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
    }

}
