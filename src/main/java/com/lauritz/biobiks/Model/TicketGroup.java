package com.lauritz.biobiks.Model;

import java.time.LocalDate;
import java.time.LocalTime;

public class TicketGroup {
    private Movie movie;
    private LocalDate showDate;
    private LocalTime showTime;
    private int quantity;

    public TicketGroup(Movie movie, LocalDate showDate, LocalTime showTime, int quantity) {
        this.movie = movie;
        this.showDate = showDate;
        this.showTime = showTime;
        this.quantity = quantity;
    }

    public Movie getMovie() { return movie; }
    public LocalDate getShowDate() { return showDate; }
    public LocalTime getShowTime() { return showTime; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    // Udregner den samlede pris for præcis denne stak billetter
    public double getSubtotal() {
        return movie.getPrice() * quantity;
    }
}