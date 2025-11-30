package com.example.theaterproject.Controllers;

import com.example.theaterproject.Models.Movie;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

/**
 * Controller for movie-card-view.fxml.
 *
 * Notes:
 * - The provided FXML has fx:id set for movieTitleLabel and screeningTimeLabel.
 * - The ImageView in the FXML does not currently have an fx:id; if you want to set
 *   poster images from code, add fx:id="posterImageView" to the ImageView in movie-card-view.fxml.
 *
 * Usage:
 * - After loading the FXML with FXMLLoader, call getController() and then setMovie(movie).
 *   Example:
 *     FXMLLoader loader = new FXMLLoader(getClass().getResource("/movie-card-view.fxml"));
 *     Parent card = loader.load();
 *     MovieCardController ctrl = loader.getController();
 *     ctrl.setMovie(movie);
 */
public class MovieCardController {

    @FXML
    private Label movieTitleLabel;

    @FXML
    private Label screeningTimeLabel;

    // Optional: only injected if you add fx:id="posterImageView" to the ImageView in the FXML
    @FXML
    private ImageView posterImageView;

    // Optional root if you give the root VBox an fx:id in the FXML (useful for click handlers)
    @FXML
    private VBox root;

    private Movie movie;

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
     * Populate the card with data from the given Movie.
     * Call this after loading the FXML (loader.getController()).
     *
     * @param pMovie the movie to display
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

        // Example of loading a poster image if you later add a path to Movie:
        if (posterImageView != null && pMovie != null) {
            // Suppose Movie had a getPosterPath() returning a resource path or URL.
            // String path = pMovie.getPosterPath();
            // if (path != null) posterImageView.setImage(new Image(path));
        }

        // Example: set a click handler on the card root (if root is wired in FXML)
        if (root != null) {
            root.setOnMouseClicked(evt -> {
                // open details, navigate, etc.
                System.out.println("Clicked card for: " + (pMovie != null ? pMovie.getTitle() : "unknown"));
            });
        }
    }

    public Movie getMovie() {
        return movie;
    }
}