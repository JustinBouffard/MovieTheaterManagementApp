package com.example.theaterproject.Models;

public class Ticket {
    private Movie aMovie;
    private Screening aScreening;
    private double aPrice;
    private Client aClient;

    public Ticket(Movie bMovie, Screening bScreening, double bPrice, Client bClient) {
        this.aMovie = bMovie;
        this.aScreening = bScreening;
        this.aPrice = bPrice;
        this.aClient = bClient;
    }
}
