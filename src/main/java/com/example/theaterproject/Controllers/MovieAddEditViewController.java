package com.example.theaterproject.Controllers;

import com.example.theaterproject.Models.Movie;
import com.example.theaterproject.Services.MovieService;
import com.example.theaterproject.Services.UIService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Controller class for adding or editing a movie within a theater management system.
 * This class provides an interface to input movie details such as title and runtime,
 * and handles saving or canceling actions.
 */
public class MovieAddEditViewController {
    /**
     * Represents a button in the MovieAddEditViewController used for canceling the current
     * operation. Typically triggers behavior to close the current window or reset inputs
     * without saving changes.
     */
    @FXML
    private Button aCancelButton;
    /**
     * The save button in the user interface that triggers the "save" action in the
     * MovieAddEditViewController. When pressed, this button typically validates
     * the input fields and saves or updates the movie information in the system.
     */
    @FXML
    private Button aSaveButton;
    /**
     * Represents a TextField for entering or editing the title of a movie.
     *
     * This field is part of the user interface for adding or editing movie details
     * in the MovieAddEditViewController.
     *
     * It is expected that the input provided in this field will be validated or
     * used as part of the process to save or edit movie records.
     */
    @FXML
    private TextField movieTitleTextField;
    /**
     * Represents a Spinner component within the MovieAddEditViewController UI that allows the user
     * to input or select the runtime (in minutes) for a movie.
     * This spinner is designed to handle integer values.
     *
     * This field is annotated with @FXML, indicating it is injected from the associated FXML file.
     */
    @FXML
    private Spinner<Integer> runtimeSpinner;
    /**
     * A singleton instance of the {@code MovieService} used to manage the collection of movies.
     * This service provides methods for retrieving, adding, updating, and removing movie entries.
     * It ensures consistent access to the movie data across different parts of the application.
     */
    private final MovieService movieService = MovieService.getInstance();
    private Movie editingMovie = null;
    private final UIService aUiService = UIService.getInstance();

    /**
     * Initializes the controller class.
     * This method is automatically called after the FXML file has been loaded.
     * Sets up the runtime spinner with an integer value factory, defining
     * a range from 1 to 1000, a default value of 90, and an increment step of 1.
     */
    @FXML
    private void initialize() {
        SpinnerValueFactory.IntegerSpinnerValueFactory factory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 1000, 90, 1);
        runtimeSpinner.setValueFactory(factory);
    }

    /**
     * Handles the event triggered by the Save button click.
     * Validates the input fields for movie title and runtime, and either adds a new movie or updates
     * an existing movie in the system. Displays error messages for invalid inputs or exceptions.
     * Closes the current window and opens the editor view upon successful operation.
     *
     * @param event the ActionEvent triggered by the Save button click
     */
    @FXML
    private void onSaveButtonClick(ActionEvent event) {
        String title = (movieTitleTextField != null) ? movieTitleTextField.getText() : null;
        Integer runtime = (runtimeSpinner != null) ? runtimeSpinner.getValue() : null;

        // Validate
        if (title == null || title.isBlank()) {
            aUiService.showErrorAlert("Validation error", "Title cannot be empty.");
            return;
        }

        if (runtime == null || runtime <= 0) {
            aUiService.showErrorAlert("Validation error", "Runtime must be a positive integer.");
            return;
        }

        try {
            if (editingMovie == null) {
                Movie newMovie = new Movie(title, runtime);
                movieService.addMovie(newMovie);
            } else {
                Movie updated = new Movie(title, runtime);
                movieService.removeMovie(editingMovie);
                movieService.addMovie(updated);
            }

            aUiService.closeWindow(event);
        } catch (Exception e) {
            aUiService.showErrorAlert("Save failed", e.getMessage());
        }
    }

    /**
     * Handles the event triggered by the Cancel button click.
     * Navigates back to the editor view within the same stage. If an error occurs during
     * the process of loading the FXML file, an alert is displayed with the error message.
     *
     * @param event the ActionEvent triggered by the Cancel button click
     */
    @FXML
    private void onCancelButtonClick(ActionEvent event) {
        // Navigate back to the Editor view within the same Stage
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/theaterproject/editor-view.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            aUiService.showErrorAlert("Navigation Error", e.getMessage());
        }
    }

    /**
     * Sets the movie being edited in the view controller.
     * Updates the title text field with the movie's title if the provided movie is not null.
     * The runtime spinner is not dynamically updated as the Movie class does not expose a runtime getter.
     *
     * @param movie the movie to be set as the one being edited; can be null
     */
    public void setEditingMovie(Movie movie) {
        this.editingMovie = movie;
        if (movie != null) {
            if (movieTitleTextField != null) movieTitleTextField.setText(movie.getTitle());
            // Movie model currently doesn't expose runtime getter; spinner stays at its default.
        }
    }


}