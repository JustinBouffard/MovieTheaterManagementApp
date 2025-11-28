package com.example.theaterproject.Services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.example.theaterproject.Models.Movie;

public class MovieService {

    private static MovieService aInstance;

    private final ObservableList<Movie> aMovies =
            FXCollections.observableArrayList();

    private final double aDefaultTicketPrice = 12;

    private MovieService() { }

    public static MovieService getInstance() {
        if (aInstance == null) {
            aInstance = new MovieService();
        }
        return aInstance;
    }

    public ObservableList<Movie> getMovies() {
        return aMovies;
    }

    public void addMovie(Movie movie) {
        aMovies.add(movie);
    }

    public void removeMovie(Movie movie) {
        aMovies.remove(movie);
    }

    public double getaDefaultTicketPrice() {
        return aDefaultTicketPrice;
    }
}
