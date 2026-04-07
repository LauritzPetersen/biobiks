package com.lauritz.biobiks.Model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Ticket {
    private int id;
    private Movie movie;
    private LocalDate showDate;
    private LocalTime showTime;

    public Ticket() {}

    public Ticket(Movie movie, LocalDate showDate, LocalTime showTime) {
        this.movie = movie;
        this.showDate = showDate;
        this.showTime = showTime;
    }

    // --- Getters og Setters ---
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Movie getMovie() { return movie; }
    public void setMovie(Movie movie) { this.movie = movie; }

    public LocalDate getShowDate() { return showDate; }
    public void setShowDate(LocalDate showDate) { this.showDate = showDate; }

    public LocalTime getShowTime() { return showTime; }
    public void setShowTime(LocalTime showTime) { this.showTime = showTime; }
}

