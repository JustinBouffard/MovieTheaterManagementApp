package com.example.theaterproject.Models;

public class Ticket {
    private Movie aMovie;
    private Screening aScreening;
    private double aPrice;
    private Client aClient;

    public Ticket(Movie pMovie, Screening pScreening, double pPrice, Client pClient) {
        this.aMovie = pMovie;
        this.aScreening = pScreening;
        this.aPrice = pPrice;
        this.aClient = pClient;
    }

    public Movie getMovie() {
        return new Movie(this.aMovie);
    }

    public Screening getScreening() {
        return this.aScreening;
    }

    public double getPrice() {
        return this.aPrice;
    }

    public Client getClient() {
        return new Client(this.aClient);
    }
}
