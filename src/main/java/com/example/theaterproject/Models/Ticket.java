package com.example.theaterproject.Models;

/**
 * Represents a ticket for a movie screening in the theater project system.
 *
 * <p>
 * A {@code Ticket} associates a movie, a specific screening, a purchase price,
 * and the client who purchased it. This class ensures that all required
 * information is valid upon creation.
 * </p>
 */
public class Ticket {
    /**
     * The specific screening (date, time, auditorium) tied to this ticket.
     */
    private Screening aScreening;

    /**
     * The price paid for this ticket.
     */
    private double aPrice;

    /**
     * The client who purchased this ticket.
     */
    private Client aClient;

    /**
     * Creates a new {@code Ticket} with the specified details.
     *
     * @param pMovie the movie for this ticket; must not be {@code null}
     * @param pScreening the screening for this ticket; must not be {@code null}
     * @param pPrice the price of the ticket; must be zero or positive
     * @param pClient the client purchasing the ticket; must not be {@code null}
     *
     * @throws IllegalArgumentException if any parameter is invalid
     */
    public Ticket(Movie pMovie, Screening pScreening, double pPrice, Client pClient) {

        if (pMovie == null) {
            throw new IllegalArgumentException("Movie cannot be null");
        }
        if (pScreening == null) {
            throw new IllegalArgumentException("Screening cannot be null");
        }
        if (pPrice < 0) {
            throw new IllegalArgumentException("Price cannot be below zero");
        }
        if (pClient == null) {
            throw new IllegalArgumentException("Client cannot be null");
        }

        this.aScreening = pScreening;
        this.aPrice = pPrice;
        this.aClient = pClient;
    }

    /**
     * Returns the screening information for this ticket.
     *
     * @return the screening object
     */
    public Screening getScreening() {
        return aScreening;
    }

    /**
     * Returns the price of this ticket.
     *
     * @return the ticket price as a double
     */
    public double getPrice() {
        return aPrice;
    }

    /**
     * Returns the client who purchased this ticket.
     *
     * @return the client object
     */
    public Client getClient() {
        return aClient;
    }

    /**
     * Returns a string representation of this ticket, including movie,
     * screening, price, and client.
     *
     * @return a formatted string describing the ticket
     */
    @Override
    public String toString() {
        return "Ticket{" +
                ", screening=" + aScreening +
                ", price=" + aPrice +
                ", client=" + aClient +
                '}';
    }
}
