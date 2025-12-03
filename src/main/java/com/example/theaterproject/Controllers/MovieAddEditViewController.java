package com.example.theaterproject.Controllers;

import com.example.theaterproject.Models.Movie;
import com.example.theaterproject.Services.MovieService;
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
            showAlert("Title cannot be blank.");
            return;
        }

        if (runtime == null || runtime <= 0) {
            showAlert("Runtime must be a positive number.");
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

            closeWindow(event);
            openWindow("editor-view", event);

        } catch (Exception e) {
            showAlert(e.getMessage());
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
            showAlert(e.getMessage());
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

    /**
     * Opens a new window using the specified FXML file and closes the current window.
     * This method loads the FXML resource based on the provided name, sets up a new stage with a title
     * matching the FXML name, and displays the new stage. If an error occurs while loading the resource,
     * an alert is shown with the error message.
     *
     * @param pName the name of the FXML resource (without extension) to be loaded and displayed
     * @param pEvent the ActionEvent from the triggering control, used to close the current window
     */
    private void openWindow(String pName, ActionEvent pEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/theaterproject/" + pName + ".fxml"));
            Parent root = loader.load();

            Stage newStage = new Stage();
            newStage.setTitle(pName);
            newStage.setScene(new Scene(root, 480, 350));
            newStage.show();

            closeWindow(pEvent);
        } catch (IOException e) {
            showAlert(e.getMessage());
        }
    }

    /**
     * Closes the current window associated with the given ActionEvent.
     * This method retrieves the source of the event, obtains its scene,
     * and then identifies and closes the corresponding stage.
     *
     * @param event the ActionEvent triggered by a user interaction; cannot be null
     */
    private void closeWindow(ActionEvent event) {
        if (event == null) return;
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    /**
     * Displays an alert dialog of type ERROR with a specified message.
     * The dialog contains a predefined title and header text, and shows
     * the provided message as its content.
     *
     * @param pMessage the message to be displayed in the alert dialog; cannot be null
     */
    private void showAlert(String pMessage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Something went wrong");
        alert.setContentText(pMessage);
        alert.showAndWait();
    }
}