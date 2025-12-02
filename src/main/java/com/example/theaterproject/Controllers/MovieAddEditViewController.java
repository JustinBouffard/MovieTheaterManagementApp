package com.example.theaterproject.Controllers;

import com.example.theaterproject.Models.Movie;
import com.example.theaterproject.Services.MovieService;
import com.example.theaterproject.Services.UIService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.LocalDate;

/**
 * Controller for add-edit-movie-view.fxml / add-edit-movie UI.
 *
 * Notes:
 * - The Movie model currently requires (genre, title, director, year, description, runtime).
 *   The UI only exposes title and runtime, so sensible defaults are used for the
 *   other fields ("Unknown" / current year / empty description).
 * - If you extend the FXML with more fields (genre, director, year, description),
 *   update this controller to read those values and pass them to the Movie constructor.
 */
public class MovieAddEditViewController {
    @FXML
    private Button aCancelButton;

    @FXML
    private Button aSaveButton;

    @FXML
    private TextField movieTitleTextField;

    @FXML
    private Spinner<Integer> runtimeSpinner;

    private final MovieService movieService = MovieService.getInstance();
    private Movie editingMovie = null;

    @FXML
    private void initialize() {
        SpinnerValueFactory.IntegerSpinnerValueFactory factory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 1000, 90, 1);
        runtimeSpinner.setValueFactory(factory);
    }

    @FXML
    private void onSaveButtonClick(ActionEvent event) {
        String title = (movieTitleTextField != null) ? movieTitleTextField.getText() : null;
        Integer runtime = (runtimeSpinner != null) ? runtimeSpinner.getValue() : null;

        if (title == null || title.isBlank()) {
            UIService.showErrorAlert("Validation error", "Title cannot be empty.");
            return;
        }
        if (runtime == null || runtime <= 0) {
            UIService.showErrorAlert("Validation error", "Runtime must be a positive integer.");
            return;
        }

        try {
            if (editingMovie == null) {
                Movie newMovie = new Movie(
                        "Unknown",                 // genre
                        title,                     // title
                        "Unknown",                 // director
                        LocalDate.now().getYear(), // year
                        "",                        // description
                        runtime                    // runtime (minutes)
                );
                movieService.addMovie(newMovie);
            } else {
                // Movie has no setters in the current model, so replace the object.
                Movie updated = new Movie(
                        "Unknown",
                        title,
                        "Unknown",
                        LocalDate.now().getYear(),
                        "",
                        runtime
                );
                movieService.removeMovie(editingMovie);
                movieService.addMovie(updated);
            }

            UIService.closeWindow(event);
        } catch (Exception e) {
            UIService.showErrorAlert("Save failed", e.getMessage());
        }
    }

    @FXML
    private void onCancelButtonClick(ActionEvent event) {
        UIService.closeWindow(event);
    }

    /**
     * Call this to set the controller into "edit" mode for an existing Movie.
     * Populate fields that are available in the current UI.
     */
    public void setEditingMovie(Movie movie) {
        this.editingMovie = movie;
        if (movie != null) {
            if (movieTitleTextField != null) movieTitleTextField.setText(movie.getTitle());
            // Movie model currently doesn't expose runtime getter; spinner stays at its default.
        }
    }
}