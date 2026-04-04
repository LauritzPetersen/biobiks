package com.lauritz.biobiks.Repository;

import com.lauritz.biobiks.Model.Movie;
import com.lauritz.biobiks.Model.Reservation;
import com.lauritz.biobiks.Model.Snack;
import com.lauritz.biobiks.Model.intface.ReservationRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class JdbcReservationRepository implements ReservationRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcReservationRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Reservation> findById(int reservationId){

        String sqlReservation = "SELECT * FROM reservation WHERE id = ?";

        List<Reservation> result = jdbcTemplate.query(sqlReservation, (rs, rowNum) -> {
            Reservation r = new Reservation();
            r.setId(rs.getInt("id"));
            r.setUserId(rs.getInt("user_id"));
            r.setReservationDate(rs.getTimestamp("reservation_date").toLocalDateTime());
            r.setTotalPrice(rs.getDouble("total_price"));
            return r;
        }, reservationId);

        if(result.isEmpty()){
            return Optional.empty();
        }

        Reservation reservation = result.get(0);

        String sqlMovies = """
                SELECT m.* FROM movie m
                JOIN reservation_movie rm ON m.id = rm.movie_id
                where rm.reservation_id = ?
                """;

        List<Movie> movies = jdbcTemplate.query(sqlMovies, (rs, rowNum) -> {
            Movie m = new Movie();
            m.setId(rs.getInt("id"));
            m.setTitle(rs.getString("title"));
            m.setGenre(rs.getString("genre"));
            m.setAgeLimit(rs.getInt("age_limit"));
            m.setPrice(rs.getDouble("price"));
            return m;
        }, reservationId);

        for(Movie movie : movies){
            reservation.getMovies().add(movie);
        }

        String sqlSnacks = """
                SELECT s.* FROM snack s
                JOIN reservation_snack rs ON s.id = rs.snack_id
                WHERE rs.reservation_id = ?
                """;

        List<Snack> snacks = jdbcTemplate.query(sqlSnacks, (rs, rowNum) -> {
            Snack s = new Snack();
            s.setId(rs.getInt("id"));
            s.setType(rs.getString("type"));
            s.setSize(rs.getString("size"));
            s.setPrice(rs.getDouble("price"));
            return s;
        }, reservationId);

        for (Snack snack : snacks) {
            reservation.getSnacks().add(snack);
        }

        return Optional.of(reservation);
    }

    @Override
    public void save(Reservation reservation){

    }
}
