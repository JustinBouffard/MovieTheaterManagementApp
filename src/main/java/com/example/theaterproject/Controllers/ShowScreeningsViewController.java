package com.example.theaterproject.Controllers;

import com.example.theaterproject.Models.Movie;
import com.example.theaterproject.Models.Screening;
import com.example.theaterproject.Services.ShowroomService;
import com.example.theaterproject.Services.UIService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.io.IOException;

/**
 * Controller responsible for managing the show screenings view.
 * Displays all available screenings for a selected movie in a list view.
 */
public class ShowScreeningsViewController {
    /**
     * Label that displays the title of the movie being shown.
     */
    @FXML
    private Label aMovieTitleLabel;

    /**
     * ListView that displays all available screenings for the movie.
     * Each item shows the date and time of the screening.
     */
    @FXML
    private ListView<Screening> aScreeningsListView;

    private final ShowroomService aShowroomService = ShowroomService.getInstance();
    private final UIService aUIService = UIService.getInstance();

    /**
     * Sets the movie for this view and populates the screenings list.
     *
     * @param pMovie the movie to display screenings for
     */
    public void setMovie(Movie pMovie) {
        if (aMovieTitleLabel != null && pMovie != null) {
            aMovieTitleLabel.setText(pMovie.getTitle());
        }

        if (aScreeningsListView != null && pMovie != null) {
            // Get all screenings for this movie
            var screenings = aShowroomService.getScreeningFor(pMovie);
            aScreeningsListView.setItems(screenings);
        }
    }

    /**
     * Handles the back button click event.
     * Returns to the previous view (main view).
     *
     * @param pEvent the action event triggered by clicking the back button
     */
    @FXML
    private void onBackButtonClick(ActionEvent pEvent) {
        aUIService.closeWindow(pEvent);
    }
}
