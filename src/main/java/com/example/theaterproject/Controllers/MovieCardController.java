package com.example.theaterproject.Controllers;

import com.example.theaterproject.Models.Movie;
import com.example.theaterproject.Services.UIService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.IOException;

/**
 * Controller responsible for managing the UI elements and behavior of a movie card
 * in a JavaFX application. The movie card is designed to display a movie's title,
 * screening time, and an optional poster image.
 * <p>
 * This class is intended to work with an associated FXML layout file, where UI components
 * are defined and linked to the controller using the `@FXML` annotation.
 */
public class MovieCardController {
    /**
     * Label UI component used to display the title of a movie in the movie card.
     * It is part of the FXML-defined UI and is linked to the controller with the `@FXML` annotation.
     * This label is usually populated with the movie's title when the `setaMovie` method is invoked.
     */
    @FXML
    private Label aMovieTitleLabel;
    /**
     * Label UI component used to display the screening time of a movie in the movie card.
     * It is part of the FXML-defined UI and is linked to the controller with the `@FXML` annotation.
     * This label is typically populated with the movie's screening time or a placeholder value
     * when the `setaMovie` method is invoked.
     */
    @FXML
    private Label aScreeningTimeLabel;
    /**
     * VBox container that serves as the root element of the movie card UI.
     * It encapsulates child components such as the movie title label, screening time label,
     * and optional poster image view. This container may also handle user interactions,
     * such as click events, for the movie card.
     * <p>
     * This field is injected via FXML and linked to the corresponding VBox element
     * defined in the associated FXML file.
     */
    @FXML
    private VBox aRoot;
    /**
     * The movie associated with this controller.
     * <p>
     * Represents a specific Movie object whose details may be utilized to populate
     * the associated movie card view in the user interface. This object is generally
     * set using the setaMovie(Movie pMovie) method and accessed via getaMovie().
     */
    private Movie aMovie;

    private final UIService aUIService = UIService.getInstance();

    /**
     * Updates the movie data displayed in the card view and sets up relevant UI interactions.
     * This method updates the movie title label, sets a default value for the screening time label
     * if no screening information is present, and assigns a mouse-click event handler for the root element.
     *
     * @param pMovie the Movie object to display in this card. If null, the card will show placeholder values.
     */
    public void setaMovie(Movie pMovie) {
        this.aMovie = pMovie;
        if (aMovieTitleLabel != null && pMovie != null) {
            aMovieTitleLabel.setText(pMovie.getTitle());
        }
        if (aScreeningTimeLabel != null) {
            // Movie doesn't currently carry screening/time info; show placeholder or other movie data
            aScreeningTimeLabel.setText("Screening: TBD");
        }
        if (aRoot != null) {
            aRoot.setOnMouseClicked(evt -> {
                // open details, navigate, etc.
                System.out.println("Clicked card for: " + (pMovie != null ? pMovie.getTitle() : "unknown"));
            });
        }
    }

    /**
     * Retrieves the currently assigned Movie object associated with this controller.
     *
     * @return the Movie instance linked to this controller, or null if no movie is assigned
     */
    public Movie getaMovie() {
        return aMovie;
    }

    /**
     * Handles the "See Screenings" button click event.
     * Opens the show-screenings-view as a modal dialog with the screenings for the current movie.
     *
     * @param pEvent the action event triggered by clicking the "See Screenings" button
     */
    @FXML
    private void onSeeScreeningsButtonClick(ActionEvent pEvent) {
        if (aMovie == null) {
            aUIService.showErrorAlert("Error", "No movie selected");
            return;
        }

        try {
            // Load the FXML first without showing it
            FXMLLoader loader = aUIService.loadFXML("show-screenings-view");
            Object controller = loader.getController();

            // Set the movie on the controller before showing the modal
            if (controller instanceof ShowScreeningsViewController) {
                ((ShowScreeningsViewController) controller).setMovie(aMovie);
            }

            // Now open it as a modal dialog
            Parent root = loader.getRoot();
            javafx.stage.Stage modal = new javafx.stage.Stage();
            modal.setScene(new javafx.scene.Scene(root, 600, 400));
            modal.initModality(javafx.stage.Modality.APPLICATION_MODAL);
            modal.setTitle("Screenings for " + aMovie.getTitle());
            modal.showAndWait();
        } catch (IOException e) {
            aUIService.showErrorAlert("Error", "Failed to load screenings view: " + e.getMessage());
        }
    }
}
