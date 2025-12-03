package com.example.theaterproject.Models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
    /**
     * The date and time when this screening takes place.
     */
    private LocalDateTime aDateTime;

    /**
     * Formatter used to render the date-time in {@code toString()}.
     */
    private final DateTimeFormatter FORMATER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    /**
     * Constructs a Screening object representing a specific movie screening
     * with details about the movie, seat number, and price.
     *
     * @param pMovie       The movie associated with this screening.
     * @param pTicketCount The seat number for this screening.
     * @param pPricePerTicket       The price of the screening.
     * @param pDateTime    The date and time of the screening.
     */
    public Screening(Movie pMovie, int pTicketCount, double pPricePerTicket, LocalDateTime pDateTime) {
        if (pMovie == null) {
            throw new IllegalArgumentException("Movie cannot be null");
        }
        if (pTicketCount < 0) {
            throw new IllegalArgumentException("Tickets sold for this screening cannot be negative");
        }
        if (pPricePerTicket < 0) {
            throw new IllegalArgumentException("Price cannot be negative.");
        }
        if (pDateTime == null) {
            throw new IllegalArgumentException("DateTime cannot be null");
        }
        if (pDateTime.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("DateTime cannot be in the past.");
        }

        this.aMovie = pMovie;
        this.aTicketCount = pTicketCount;
        this.aPricePerTicket = pPricePerTicket;
        this.aDateTime = pDateTime;
    }

    /**
     * Retrieves the movie associated with this screening.
     *
     * @return the {@link Movie} object associated with this screening.
     */
    public Movie getMovie() {
        return this.aMovie;
    }

    /**
     * Sets the movie for this screening.
     *
     * @param pMovie the movie to associate with this screening
     */
    public void setMovie(Movie pMovie) {
        this.aMovie = pMovie;
    }

    /**
     * Retrieves the seat number associated with this screening.
     *
     * @return the seat number for this screening as an integer.
     */
    public int getTicketCount() {
        return this.aTicketCount;
    }

    /**
     * Sets the number of tickets available for this screening.
     *
     * @param aTicketCount the number of tickets
     */
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

    /**
     * Sets the price per ticket for this screening.
     *
     * @param pPricePerTicket the price per ticket
     */
    public void setPricePerTicket(double pPricePerTicket) {
        this.aPricePerTicket = pPricePerTicket;
    }

    /**
     * Retrieves the date and time of this screening.
     *
     * @return the LocalDateTime of this screening
     */
    public LocalDateTime getDateTime() {
        return aDateTime;
    }

    /**
     * Sets the date and time for this screening.
     *
     * @param pDateTime the LocalDateTime to set
     */
    public void setDateTime(LocalDateTime pDateTime) {
        if (pDateTime == null) {
            throw new IllegalArgumentException("DateTime cannot be null");
        }
        if (pDateTime.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("DateTime cannot be in the past.");
        }
        this.aDateTime = pDateTime;
    }

    /**
     * Returns a string representation of the Screening object, including the title of the
     * movie associated with the screening, the seat number, and the price.
     *
     * @return a string containing the movie title, seat number, and price separated by spaces.
     */
    @Override
    public String toString() {
        return aDateTime.format(FORMATER);
    }
}
