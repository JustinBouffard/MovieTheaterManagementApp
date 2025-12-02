package com.example.theaterproject.Controllers;

import com.example.theaterproject.Models.Movie;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;

/**
 * Controller for the movie card used in the editor grid.
 * The EditorViewController loads the FXML, obtains this controller, and calls setData(...)
 *
 * Screening label removed; setData now takes only the movie and two actions.
 */
public class EditorMovieCardController {

    @FXML
    private VBox root;

    @FXML
    private Label movieTitleLabel;

    @FXML
    private Button editButton;

    @FXML
    private Button removeButton;

    private Movie movie;

    /**
     * Set data for the card and wire actions.
     *
     * @param pMovie the movie to display
     * @param editAction runnable executed when Edit is clicked
     * @param removeAction runnable executed when Remove is clicked
     */
    public void setData(Movie pMovie, Runnable editAction, Runnable removeAction) {
        this.movie = pMovie;

        if (movieTitleLabel != null) {
            movieTitleLabel.setText(pMovie != null ? pMovie.getTitle() : "");
        }

        if (editButton != null) {
            editButton.setOnAction(e -> {
                if (editAction != null) editAction.run();
            });
        }

        if (removeButton != null) {
            removeButton.setOnAction(e -> {
                if (removeAction != null) removeAction.run();
            });
        }
    }

    public Movie getMovie() {
        return movie;
    }

    public VBox getRoot() {
        return root;
    }
}