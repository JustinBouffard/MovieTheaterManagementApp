package com.example.theaterproject.Controllers;

import com.example.theaterproject.Models.Movie;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

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
     * This label is usually populated with the movie's title when the `setMovie` method is invoked.
     */
    @FXML
    private Label movieTitleLabel;
    /**
     * Label UI component used to display the screening time of a movie in the movie card.
     * It is part of the FXML-defined UI and is linked to the controller with the `@FXML` annotation.
     * This label is typically populated with the movie's screening time or a placeholder value
     * when the `setMovie` method is invoked.
     */
    @FXML
    private Label screeningTimeLabel;
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
    private VBox root;
    /**
     * The movie associated with this controller.
     * <p>
     * Represents a specific Movie object whose details may be utilized to populate
     * the associated movie card view in the user interface. This object is generally
     * set using the setMovie(Movie pMovie) method and accessed via getMovie().
     */
    private Movie movie;

    /**
     * Initializes the controller after its root element has been completely processed.
     * This method sets default values to ensure fields are in a known and predictable state.
     * Specifically, it clears the text values of the movie title and screening time labels
     * if they are not null.
     */
    @FXML
    private void initialize() {
        // set sensible defaults if needed
        if (movieTitleLabel != null) {
            movieTitleLabel.setText("");
        }
        if (screeningTimeLabel != null) {
            screeningTimeLabel.setText("");
        }
    }

    /**
     * Updates the movie data displayed in the card view and sets up relevant UI interactions.
     * This method updates the movie title label, sets a default value for the screening time label
     * if no screening information is present, and assigns a mouse-click event handler for the root element.
     *
     * @param pMovie the Movie object to display in this card. If null, the card will show placeholder values.
     */
    public void setMovie(Movie pMovie) {
        this.movie = pMovie;
        if (movieTitleLabel != null && pMovie != null) {
            movieTitleLabel.setText(pMovie.getTitle());
        }
        if (screeningTimeLabel != null) {
            // Movie doesn't currently carry screening/time info; show placeholder or other movie data
            screeningTimeLabel.setText("Screening: TBD");
        }
        if (root != null) {
            root.setOnMouseClicked(evt -> {
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
    public Movie getMovie() {
        return movie;
    }
}