package com.lauritz.biobiks.Model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Reservation {
    private int id;
    private int userId; // Hvem lavede reservationen?
    private LocalDateTime reservationDate;
    private double totalPrice = 0.0;

    private List<Movie> movies = new ArrayList<>();
    private List<Snack> snacks = new ArrayList<>();

    public Reservation() {}

    public Reservation(int id, int userId, LocalDateTime reservationDate, double totalPrice, List<Movie> movies, List<Snack> snacks) {
        this.id = id;
        this.userId = userId;
        this.reservationDate = reservationDate;
        this.totalPrice = totalPrice;
        this.movies = movies;
        this.snacks = snacks;
    }

    public int getId(){return id;}
    public void setId(int id){this.id = id;}

    public int getUserId(){return userId;}
    public void setUserId(int userId){this.userId = userId;}

    public LocalDateTime getReservationDate(){return reservationDate;}
    public void setReservationDate(LocalDateTime reservationDate){this.reservationDate = reservationDate;}

    public double getTotalPrice(){return totalPrice;}
    public void setTotalPrice(double totalPrice){this.totalPrice = totalPrice;}

    public List<Movie> getMovies() {return movies;}
    public void setMovies(List<Movie> movies) {this.movies = movies;}

    public List<Snack> getSnacks() {return snacks;}
    public void setSnacks(List<Snack> snacks) {this.snacks = snacks;}

    public void orderMovie(Movie movie) {
        this.movies.add(movie);
        this.totalPrice += movie.getPrice();
    }

    public void orderSnack(Snack snack) {
        this.snacks.add(snack);
        this.totalPrice += snack.getPrice();
    }
}
