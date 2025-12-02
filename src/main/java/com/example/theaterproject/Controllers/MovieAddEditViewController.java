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


    @FXML
    private void onCancelButtonClick(ActionEvent event) {
        closeWindow(event);
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

    private void closeWindow(ActionEvent event) {
        if (event == null) return;
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    private void showAlert(String pMessage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Something went wrong");
        alert.setContentText(pMessage);
        alert.showAndWait();
    }
}