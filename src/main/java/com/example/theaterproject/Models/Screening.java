package com.example.theaterproject.Models;

import java.time.LocalDate;

/**
 * The Screening class represents information about a specific movie screening,
 * including the movie being screened, the seat number, and the price of the screening.
 */
public class Screening {
    /**
     * Represents the movie associated with a specific screening in the theater system.
     * This variable stores an instance of the {@link Movie} class, which provides
     * details about the movie such as its genre, title, director, release year,
     * and description.
     */
    private Movie aMovie;
    /**
     * Represents the seat number associated with a specific movie screening.
     * This variable stores an integer that corresponds to the assigned seat
     * for a particular screening.
     */
    private int aTicketCount;
    /**
     * Represents the price of a single ticket of that screening
     */
    private double aPricePerTicket;

    private LocalDate aDate;

    /**
     * Constructs a Screening object representing a specific movie screening
     * with details about the movie, seat number, and price.
     *
     * @param pMovie       The movie associated with this screening.
     * @param pTicketCount The seat number for this screening.
     * @param pPricePerTicket       The price of the screening.
     */
    public Screening(Movie pMovie, int pTicketCount, double pPricePerTicket, LocalDate pDate) {
        if (pMovie == null) {
            throw new IllegalArgumentException("Movie cannot be null");
        }
        if (pTicketCount < 0) {
            throw new IllegalArgumentException("Tickets sold for this screening cannot be negative");
        }
        if (pPricePerTicket < 0) {
            throw new IllegalArgumentException("Price cannot be negative.");
        }

        this.aMovie = pMovie;
        this.aTicketCount = pTicketCount;
        this.aPricePerTicket = pPricePerTicket;
        this.aDate = pDate;
    }

    /**
     * Retrieves the movie associated with this screening.
     *
     * @return the {@link Movie} object associated with this screening.
     */
    public Movie getMovie() {
        return this.aMovie;
    }

    public void setMovie(Movie pMovie) {
        this.aMovie = pMovie;
    }

    /**
     * Retrieves the seat number associated with this screening.
     *
     * @return the seat number for this screening as an integer.
     */
    public int geTicketCount() {
        return this.aTicketCount;
    }

    public void setTicketCount(int aTicketCount) {
        this.aTicketCount = aTicketCount;
    }

    /**
     * Retrieves the price of the screening.
     *
     * @return the price of the screening as a double.
     */
    public double getPricePerTicket() {
        return aPricePerTicket;
    }

    public void setPricePerTicket(double pPricePerTicket) {
        this.aPricePerTicket = pPricePerTicket;
    }

    public LocalDate getDate() {
        return aDate;
    }

    public void setDate(LocalDate pDate) {
        if(pDate.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Date cannot be in the past.");
        }

        this.aDate = pDate;
    }

    /**
     * Returns a string representation of the Screening object, including the title of the
     * movie associated with the screening, the seat number, and the price.
     *
     * @return a string containing the movie title, seat number, and price separated by spaces.
     */
    @Override
    public String toString() {
        return aMovie.getTitle() + " " + this.aTicketCount + " " + aPricePerTicket;
    }
}
