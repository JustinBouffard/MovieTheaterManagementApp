package com.example.theaterproject.Services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.example.theaterproject.Models.Movie;

import java.util.Collection;

public class MovieService {

    private static MovieService aInstance;

    private final ObservableList<Movie> aMovies =
            FXCollections.observableArrayList();

    private MovieService() { }

    public static MovieService getInstance() {
        if (aInstance == null) {
            aInstance = new MovieService();
        }
        return aInstance;
    }

    public ObservableList<Movie> getMovies() {
        return FXCollections.unmodifiableObservableList(aMovies);
    }

    public void addMovie(Movie pMovie) {
        aMovies.add(pMovie);
    }

    public void removeMovie(Movie pMovie) {
        aMovies.remove(pMovie);
    }
}
