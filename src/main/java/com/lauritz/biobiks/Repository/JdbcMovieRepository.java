package com.lauritz.biobiks.Repository;

import com.lauritz.biobiks.Model.Movie;
import com.lauritz.biobiks.Model.intface.MovieRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class JdbcMovieRepository implements MovieRepository {
    private final JdbcTemplate jdbcTemplate;

    public JdbcMovieRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Movie> findAll(){
        String sql = "SELECT * FROM movie";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new Movie(
                rs.getInt("id"),
                rs.getString("title"),
                rs.getString("genre"),
                rs.getInt("age_limit"),
                rs.getDouble("price")
                ));
    }

    @Override
    public Optional<Movie> findById(int id){
        String sql = "SELECT * FROM movie WHERE id = ?";
        List<Movie> result = jdbcTemplate.query(sql, (rs, rowNum) -> new Movie(
                rs.getInt("id"),
                rs.getString("title"),
                rs.getString("genre"),
                rs.getInt("age_limit"),
                rs.getDouble("price")
        ), id);

        return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
    }
}
