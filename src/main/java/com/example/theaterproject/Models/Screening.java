package com.example.theaterproject.Models;

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
    private int aSeatNumber;
    /**
     * Represents the price of a specific movie screening.
     * This variable stores the monetary cost associated with attending a particular screening.
     */
    private double aPrice;

    /**
     * Constructs a Screening object representing a specific movie screening
     * with details about the movie, seat number, and price.
     *
     * @param pMovie The movie associated with this screening.
     * @param pSeatNumber The seat number for this screening.
     * @param pPrice The price of the screening.
     */
    public Screening(Movie pMovie, int pSeatNumber, double pPrice) {
        this.aMovie = pMovie;
        this.aSeatNumber = pSeatNumber;
        this.aPrice = pPrice;
    }

    /**
     * Retrieves the movie associated with this screening.
     *
     * @return the {@link Movie} object associated with this screening.
     */
    public Movie getMovie() {
        return aMovie;
    }

    /**
     * Retrieves the seat number associated with this screening.
     *
     * @return the seat number for this screening as an integer.
     */
    public int getSeatNumber() {
        return aSeatNumber;
    }

    /**
     * Retrieves the price of the screening.
     *
     * @return the price of the screening as a double.
     */
    public double getPrice() {
        return aPrice;
    }

    /**
     * Returns a string representation of the Screening object, including the title of the
     * movie associated with the screening, the seat number, and the price.
     *
     * @return a string containing the movie title, seat number, and price separated by spaces.
     */
    @Override
    public String toString() {
        return aMovie.getTitle() + " " + aSeatNumber + " " + aPrice;
    }
}
