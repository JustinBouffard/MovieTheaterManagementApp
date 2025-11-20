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

    private void validateString(String pString) {
        if (pString == null || pString.isBlank()) {
            throw new IllegalArgumentException("Cannot be empty");
        }
    }

    private void validateYear(int pYear) {
        int currentYear = LocalDate.now().getYear();
        if (pYear < 1888) {
            throw new IllegalArgumentException("The year cannot be before 1888.");
        }

        if (pYear > currentYear + 1) {
            throw new IllegalArgumentException("The year cannot be further than the current year + 1 year");
        }
    }

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