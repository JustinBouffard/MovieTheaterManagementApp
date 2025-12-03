package com.example.theaterproject.Models;

import java.time.LocalDate;

/**
 * Represents a movie in the theater project system.
 *
 * <p>
 * This class encapsulates information about a movie including its genre, title,
 * director, year of release, description, and runtime.
 * </p>
 */
public class Movie {
    private final String aTitle;
    private final int aRuntime;

    /**
     * Creates a new Movie with the specified details.
     *
     * @param pTitle the title of the movie
     * @param pRuntime the runtime of the movie
     */
    public Movie(String pTitle, int pRuntime) {
        validateString(pTitle);
        validateRuntime(pRuntime);

        this.aTitle = pTitle;
        this.aRuntime = pRuntime;
    }

    /**
     * Copy constructor that creates a new Movie instance based on
     * another existing Movie.
     *
     * @param pMovie the movie to copy from
     */
    public Movie(Movie pMovie) {
        this.aTitle = pMovie.aTitle;
        this.aRuntime = pMovie.aRuntime;
    }

    /**
     * Returns the title of this movie.
     *
     * @return the movie title
     */
    public String getTitle() {
        return this.aTitle;
    }

    /**
     * Returns the runtime of this movie.
     *
     * @return the runtime in minutes
     */
    public int getRuntime() {
        return this.aRuntime;
    }

    /**
     * Validates that the provided string is not null or blank.
     *
     * @param pString the string to validate
     * @throws IllegalArgumentException if the string is null or blank
     */
    private void validateString(String pString) {
        if (pString == null || pString.isBlank()) {
            throw new IllegalArgumentException("Cannot be empty");
        }
    }

    /**
     * Validates that the provided runtime is within acceptable bounds.
     *
     * <p>The runtime must be between 1 and 51420 minutes (inclusive),
     * where 51420 minutes equals approximately 857.5 hours or 35.7 days.</p>
     *
     * @param pRuntime the runtime in minutes to validate
     * @throws IllegalArgumentException if the runtime is not between 1 and 51420 minutes
     */
    private void validateRuntime(int pRuntime) {
        if (pRuntime <= 0 || pRuntime > 51420) {
            throw new IllegalArgumentException("Please enter a valid runtime in minutes.");
        }
    }

    /**
     * Returns a string representation of this movie.
     *
     * <p>
     * The string includes the title, genre, director, year, and description.
     * </p>
     *
     * @return a formatted string containing the movie's information
     */
    @Override
    public String toString() {
        return this.aTitle;
    }
}