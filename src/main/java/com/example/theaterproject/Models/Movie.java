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
    private String aGenre;
    private String aTitle;
    private String aDirector;
    private int aYear;
    private String aDescription;
    private int aRuntime;

    /**
     * Creates a new Movie with the specified details.
     *
     * @param pGenre the genre of the movie
     * @param pTitle the title of the movie
     * @param pDirector the director of the movie
     * @param pYear the year the movie was released
     * @param pDescription a description of the movie
     * @param pRuntime the runtime of the movie
     */
    public Movie(String pGenre, String pTitle, String pDirector, int pYear, String pDescription, int pRuntime) {
        validateString(pGenre);
        validateString(pTitle);
        validateString(pDirector);
        validateYear(pYear);
        validateString(pDescription);
        validateRuntime(pRuntime);


        this.aGenre = pGenre;
        this.aTitle = pTitle;
        this.aDirector = pDirector;
        this.aYear = pYear;
        this.aDescription = pDescription;
        this.aRuntime = pRuntime;
    }

    /**
     * Copy constructor that creates a new Movie instance based on
     * another existing Movie.
     *
     * @param pMovie the movie to copy from
     */
    public Movie(Movie pMovie) {
        this.aGenre = pMovie.aGenre;
        this.aTitle = pMovie.aTitle;
        this.aDirector = pMovie.aDirector;
        this.aYear = pMovie.aYear;
        this.aDescription = pMovie.aDescription;
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
     * Validates that the provided year is within acceptable bounds.
     *
     * <p>The year must:</p>
     * <ul>
     *   <li>Be at least 1888 (the year of the first motion picture)</li>
     *   <li>Not be more than one year in the future</li>
     * </ul>
     *
     * @param pYear the year to validate
     * @throws IllegalArgumentException if the year is before 1888 or more than one year in the future
     */
    private void validateYear(int pYear) {
        int currentYear = LocalDate.now().getYear();
        if (pYear < 1888) {
            throw new IllegalArgumentException("The year cannot be before 1888.");
        }

        if (pYear > currentYear + 1) {
            throw new IllegalArgumentException("The year cannot be further than the current year + 1 year");
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
        return this.aTitle + " " + this.aGenre + " " + this.aDirector + " " + this.aYear + " " + this.aDescription;
    }
}