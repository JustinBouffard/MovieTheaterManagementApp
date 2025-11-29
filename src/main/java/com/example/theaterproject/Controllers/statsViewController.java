package com.example.theaterproject.Controllers;

import com.example.theaterproject.Models.Movie;
import com.example.theaterproject.Models.Screening;
import com.example.theaterproject.Services.MovieService;
import com.example.theaterproject.Services.ScreeningService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

public class statsViewController {
    // ListView for movies and screenings
    @FXML
    private ListView<Movie> moviesList;
    @FXML
    private ListView<Screening> screeningList;

    // labels for the stats
    @FXML
    private Label numberTicketsLabel;
    @FXML
    private Label priceTicketsLabel;
    @FXML
    private Label revenueLabel;

    private final MovieService movieService = MovieService.getInstance();
    private final ScreeningService screeningService = ScreeningService.getInstance();

    @FXML
    private void initialize() {
        // populate movies list. Movie class toString method implicitly called.
        moviesList.setItems(movieService.getMovies());

        // when movie is selected display its screenings with ScreeningService.getScreeningFor
        moviesList.getSelectionModel().selectedItemProperty().addListener((obs, oldMovie, newMovie) -> {
            screeningList.getItems().clear();
            clearDetails();

            // populate list of screenings for newly selected movie
            if (newMovie != null) {
                screeningList.setItems(screeningService.getScreeningFor(newMovie));
            }
        });

        // display stats when screening is selected
        screeningList.getSelectionModel().selectedItemProperty().addListener((obs,oldScr,scr) -> {
            // clear details if no screening is selected
            if (scr == null) {
                clearDetails();
                return;
            }
            // calculate revenue for selected screening
            int sold = scr.getTicketCount();
            double price = scr.getPricePerTicket();
            double revenue = sold * price;

            // set stats labels
            numberTicketsLabel.setText(Integer.toString(sold));
            priceTicketsLabel.setText(String.format("$%.2f", price));
            revenueLabel.setText(String.format("$%.2f", revenue));
        });

        // default state
        clearDetails();
    }

    private void clearDetails() {
        numberTicketsLabel.setText("-");
        priceTicketsLabel.setText("-");
        revenueLabel.setText("-");
    }
}
