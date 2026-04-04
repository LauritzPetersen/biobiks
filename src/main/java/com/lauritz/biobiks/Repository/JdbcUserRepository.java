package com.lauritz.biobiks.Repository;

import com.lauritz.biobiks.Model.User;
import com.lauritz.biobiks.Model.intface.UserRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class JdbcUserRepository implements UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcUserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        String sql = "SELECT * FROM user WHERE email = ?";

        List<User> result = jdbcTemplate.query(sql, (rs, rowNum) -> new User(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("email"),
                rs.getInt("age")
        ), email);

        // Hvis listen er tom, returnerer vi bare en tom boks (ingen fejl/crashes)
        if (result.isEmpty()) {
            return Optional.empty();
        }

        // Ellers pakker vi den første (og eneste) bruger ind og sender tilbage
        return Optional.of(result.get(0));
    }

    @Override
    public void save(User user) {
        String sql = "INSERT INTO user (name, email, age) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, user.getName(), user.getEmail(), user.getAge());
    }
}