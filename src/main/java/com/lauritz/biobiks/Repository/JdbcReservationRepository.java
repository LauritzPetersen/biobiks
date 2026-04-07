package com.lauritz.biobiks.Repository;

import com.lauritz.biobiks.Model.Movie;
import com.lauritz.biobiks.Model.Reservation;
import com.lauritz.biobiks.Model.Snack;
import com.lauritz.biobiks.Model.Ticket;
import com.lauritz.biobiks.Model.intface.ReservationRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcReservationRepository implements ReservationRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Reservation> findById(int reservationId) {

        Optional<Reservation> reservationOpt = fetchBaseReservation(reservationId);

        if (reservationOpt.isPresent()) {
            Reservation reservation = reservationOpt.get();

            // Dirigenten samler delene via vores små SRP hjælpe-metoder
            reservation.setTickets(fetchTicketsFor(reservationId));
            reservation.setSnacks(fetchSnacksFor(reservationId));

            return Optional.of(reservation);
        }

        return Optional.empty(); // Ordren fandtes slet ikke
    }


    // HJÆLPE-METODER (Private, da kun klassen selv skal bruge dem)

    private Optional<Reservation> fetchBaseReservation(int reservationId) {
        String sql = "SELECT * FROM reservation WHERE id = ?";
        List<Reservation> result = jdbcTemplate.query(sql, (rs, rowNum) -> {
            Reservation r = new Reservation();
            r.setId(rs.getInt("id"));
            r.setUserId(rs.getInt("user_id"));
            r.setReservationDate(rs.getTimestamp("reservation_date").toLocalDateTime());
            r.setTotalPrice(rs.getDouble("total_price"));
            return r;
        }, reservationId);

        return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
    }

    private List<Ticket> fetchTicketsFor(int reservationId) {
        String sql = """
                SELECT t.id as ticket_id, t.show_date, t.show_time, m.* FROM ticket t
                JOIN movie m ON t.movie_id = m.id
                WHERE t.reservation_id = ?
                """;
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Movie m = new Movie();
            m.setId(rs.getInt("id"));
            m.setTitle(rs.getString("title"));
            m.setGenre(rs.getString("genre"));
            m.setAgeLimit(rs.getInt("age_limit"));
            m.setPrice(rs.getDouble("price"));

            Ticket t = new Ticket();
            t.setId(rs.getInt("ticket_id"));
            t.setMovie(m);
            t.setShowDate(rs.getDate("show_date").toLocalDate());
            t.setShowTime(rs.getTime("show_time").toLocalTime());
            return t;
        }, reservationId);
    }

    private List<Snack> fetchSnacksFor(int reservationId) {
        String sql = """
                SELECT s.* FROM snack s
                JOIN reservation_snack rs ON s.id = rs.snack_id
                WHERE rs.reservation_id = ?
                """;
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Snack s = new Snack();
            s.setId(rs.getInt("id"));
            s.setType(rs.getString("type"));
            s.setSize(rs.getString("size"));
            s.setPrice(rs.getDouble("price"));
            return s;
        }, reservationId);
    }


    @Override
    public void save(Reservation reservation) {
        // 1. Dirigenten beder først om at få gemt hoved-ordren og får det magiske ID tilbage
        int newReservationId = insertBaseReservation(reservation);

        // 2. Derefter beder dirigenten om at få gemt tilbehøret med det nye ID
        insertTickets(reservation.getTickets(), newReservationId);
        insertSnacks(reservation.getSnacks(), newReservationId);
    }


    // HJÆLPE-METODER TIL SAVE (Private)

    private int insertBaseReservation(Reservation reservation) {
        String sqlReservation = "INSERT INTO reservation (user_id, reservation_date, total_price) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sqlReservation, new String[]{"id"});
            ps.setInt(1, reservation.getUserId());
            ps.setTimestamp(2, Timestamp.valueOf(reservation.getReservationDate()));
            ps.setDouble(3, reservation.getTotalPrice());
            return ps;
        }, keyHolder);

        // Returner det nye ID, så vi kan bruge det til billetter og snacks
        return keyHolder.getKey().intValue();
    }

    private void insertTickets(List<Ticket> tickets, int reservationId) {
        String sqlTicket = "INSERT INTO ticket (reservation_id, movie_id, show_date, show_time) VALUES (?, ?, ?, ?)";
        for (Ticket ticket : tickets) {
            jdbcTemplate.update(sqlTicket,
                    reservationId,
                    ticket.getMovie().getId(),
                    ticket.getShowDate(),
                    ticket.getShowTime()
            );
        }
    }

    private void insertSnacks(List<Snack> snacks, int reservationId) {
        String sqlSnack = "INSERT INTO reservation_snack (reservation_id, snack_id) VALUES (?, ?)";
        for (Snack snack : snacks) {
            jdbcTemplate.update(sqlSnack, reservationId, snack.getId());
        }
    }
}
