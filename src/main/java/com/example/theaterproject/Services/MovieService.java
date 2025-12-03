package com.example.theaterproject.Services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.example.theaterproject.Models.Movie;

import java.util.Collection;
import java.util.List;

/**
 * Service class for managing movies in the theater system.
 *
 * <p>
 * This singleton service maintains an observable list of movies and provides
 * methods for retrieving, adding, and removing movies. The observable list
 * allows UI components to automatically reflect changes to the movie collection.
 * </p>
 */
public class MovieService {

    private static MovieService aInstance;

    private final ObservableList<Movie> aMovies =
            FXCollections.observableArrayList();

    private MovieService() { }

    /**
     * Returns the singleton instance of MovieService, creating it if necessary.
     *
     * @return the singleton MovieService instance
     */
    public static MovieService getInstance() {
        if (aInstance == null) {
            aInstance = new MovieService();
        }
        return aInstance;
    }

    /**
     * Retrieves the observable list of all movies.
     *
     * @return an ObservableList containing all movies
     */
    public ObservableList<Movie> getMovies() {
        return aMovies;
    }

    /**
     * Replaces the current movie collection with a new collection.
     *
     * @param pMovies the new collection of movies to set
     */
    public void setMovies(ObservableList<Movie> pMovies) {
        aMovies.clear();
        aMovies.setAll(pMovies);
    }

    /**
     * Adds a new movie to the movie collection.
     *
     * @param pMovie the movie to add; cannot be null
     */
    public void addMovie(Movie pMovie) {
        aMovies.add(pMovie);
    }

    /**
     * Removes a movie from the movie collection.
     *
     * @param pMovie the movie to remove
     */
    public void removeMovie(Movie pMovie) {
        aMovies.remove(pMovie);
    }
}
