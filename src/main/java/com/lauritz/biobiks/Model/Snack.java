package com.lauritz.biobiks.Model;

public class Snack {
    private int id;
    private String type;
    private String size;
    private double price;

    public Snack(){}

    public Snack(int id, String type, String size, double price){
        this.id = id;
        this.type = type;
        this.size = size;
        this.price = price;
    }

    public int getId(){return id;}
    public void setId(int id){this.id = id;}

    public String getType(){return type;}
    public void setType(String type){this.type = type;}

    public String getSize(){return size;}
    public void setSize(String size){this.size = size;}

    public double getPrice(){return price;}
    public void setPrice(double price){this.price = price;}
}
