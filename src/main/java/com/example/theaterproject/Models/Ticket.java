package com.example.theaterproject.Models;

/**
 * Represents a ticket for a movie screening in the theater project system.
 *
 * <p>
 * This class associates a movie, screening, price, and client together to form
 * a complete ticket record.
 * </p>
 */
public class Ticket {
    private Movie aMovie;
    private Screening aScreening;
    private double aPrice;
    private Client aClient;

    /**
     * Creates a new Ticket with the specified details.
     *
     * @param pMovie the movie for this ticket
     * @param pScreening the screening for this ticket
     * @param pPrice the price of the ticket
     * @param pClient the client purchasing the ticket
     */
    public Ticket(Movie pMovie, Screening pScreening, double pPrice, Client pClient) {
        this.aMovie = pMovie;
        this.aScreening = pScreening;
        this.aPrice = pPrice;
        this.aClient = pClient;
    }

    /**
     * Returns a copy of the movie associated with this ticket.
     *
     * @return a copy of the movie
     */
    public Movie getMovie() {
        return new Movie(this.aMovie);
    }

    /**
     * Returns the screening associated with this ticket.
     *
     * @return the screening
     */
    public Screening getScreening() {
        return this.aScreening;
    }

    /**
     * Returns the price of this ticket.
     *
     * @return the ticket price
     */
    public double getPrice() {
        return this.aPrice;
    }

    /**
     * Returns a copy of the client associated with this ticket.
     *
     * @return a copy of the client
     */
    public Client getClient() {
        return new Client(this.aClient);
    }
}