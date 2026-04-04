package com.lauritz.biobiks.Model;

public class Movie {
    private int id;
    private String title;
    private String genre;
    private int ageLimit;
    private double price;

    public Movie(){}

    public Movie(int id, String title, String genre, int ageLimit, double price){
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.ageLimit = ageLimit;
        this.price = price;
    }

    public int getId(){return id;}
    public void setId(int id){this.id = id;}

    public String getTitle(){return title;}
    public void setTitle(String title){this.title = title;}

    public String getGenre(){return genre;}
    public void setGenre(String genre){this.genre = genre;}

    public int getAgeLimit(){return ageLimit;}
    public void setAgeLimit(int ageLimit){this.ageLimit = ageLimit;}

    public double getPrice(){return price;}
    public void setPrice(double price){this.price = price;}
}
