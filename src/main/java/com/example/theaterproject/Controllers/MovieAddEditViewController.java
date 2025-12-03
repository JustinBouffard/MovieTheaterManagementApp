package com.example.theaterproject.Controllers;

import com.example.theaterproject.Models.Movie;
import com.example.theaterproject.Services.MovieService;
import com.example.theaterproject.Services.UIService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;

import java.io.IOException;

/**
 * Controller class for adding or editing a pMovie within a theater management system.
 * This class provides an interface to input pMovie details such as title and runtime,
 * and handles saving or canceling actions.
 */
public class MovieAddEditViewController {
    /**
     * Represents a TextField for entering or editing the title of a pMovie.
     * <p>
     * This field is part of the user interface for adding or editing pMovie details
     * in the MovieAddEditViewController.
     * <p>
     * It is expected that the input provided in this field will be validated or
     * used as part of the process to save or edit pMovie records.
     */
    @FXML
    private TextField aMovieTitleTextField;
    /**
     * Represents a Spinner component within the MovieAddEditViewController UI that allows the user
     * to input or select the runtime (in minutes) for a pMovie.
     * This spinner is designed to handle integer values.
     * <p>
     * This field is annotated with @FXML, indicating it is injected from the associated FXML file.
     */
    @FXML
    private Spinner<Integer> aRuntimeSpinner;
    /**
     * A singleton instance of the {@code MovieService} used to manage the collection of movies.
     * This service provides methods for retrieving, adding, updating, and removing pMovie entries.
     * It ensures consistent access to the pMovie data across different parts of the application.
     */
    private final MovieService aMovieService = MovieService.getInstance();
    /**
     * Represents the current pMovie being edited in the MovieAddEditViewController.
     * <p>
     * This variable is used to store the Movie instance that the user
     * is modifying within the view. If no pMovie is being edited, this
     * value will be null. The title corresponding to the `editingMovie`
     * can be displayed in the input field, but runtime modifications are
     * not directly updated due to no public getter for runtime in the Movie class.
     */
    private Movie aEditingMovie = null;
    /**
     * A singleton instance of UIService used for managing common UI operations within the controller.
     * Facilitates interactions such as showing alerts, loading FXML files, managing modal dialogs,
     * and transitioning between windows.
     */
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
        aRuntimeSpinner.setValueFactory(factory);
    }

    /**
     * Handles the pEvent triggered by the Save button click.
     * Validates the input fields for pMovie title and runtime, and either adds a new pMovie or updates
     * an existing pMovie in the system. Displays error messages for invalid inputs or exceptions.
     * Closes the current window and opens the editor view upon successful operation.
     *
     * @param pEvent the ActionEvent triggered by the Save button click
     */
    @FXML
    private void onSaveButtonClick(ActionEvent pEvent) {
        String title = (aMovieTitleTextField != null) ? aMovieTitleTextField.getText() : null;
        Integer runtime = (aRuntimeSpinner != null) ? aRuntimeSpinner.getValue() : null;

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
            if (aEditingMovie == null) {
                Movie newMovie = new Movie(title, runtime);
                aMovieService.addMovie(newMovie);
            } else {
                Movie updated = new Movie(title, runtime);
                aMovieService.removeMovie(aEditingMovie);
                aMovieService.addMovie(updated);
            }

            aUiService.closeWindow(pEvent);
            aUiService.openNewWindow("editor-view", "Editor View", pEvent);
        } catch (Exception e) {
            aUiService.showErrorAlert("Save failed", e.getMessage());
        }
    }

    /**
     * Handles the pEvent triggered by the Cancel button click.
     * Navigates back to the editor view within the same stage. If an error occurs during
     * the process of loading the FXML file, an alert is displayed with the error message.
     *
     * @param pEvent the ActionEvent triggered by the Cancel button click
     */
    @FXML
    private void onCancelButtonClick(ActionEvent pEvent) {
        // Navigate back to the Editor view within the same Stage
        try {
            aUiService.openNewWindow("editor-view", "Editor View", pEvent, 900,700);
        } catch (IOException e) {
            aUiService.showErrorAlert("Navigation Error", e.getMessage());
        }
    }

    /**
     * Sets the pMovie being edited in the view controller.
     * Updates the title text field with the pMovie's title if the provided pMovie is not null.
     * The runtime spinner is not dynamically updated as the Movie class does not expose a runtime getter.
     *
     * @param pMovie the pMovie to be set as the one being edited; can be null
     */
    public void setEditingMovie(Movie pMovie) {
        this.aEditingMovie = pMovie;
        if (pMovie != null) {
            if (aMovieTitleTextField != null) aMovieTitleTextField.setText(pMovie.getTitle());
            // Movie model currently doesn't expose runtime getter; spinner stays at its default.
        }
    }


}