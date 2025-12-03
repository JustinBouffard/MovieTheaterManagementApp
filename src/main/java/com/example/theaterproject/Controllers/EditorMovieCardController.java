package com.example.theaterproject.Controllers;

import com.example.theaterproject.Models.Movie;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;

/**
 * Controls the behavior and display of a movie card in the editor interface.
 *
 * This class is responsible for binding a Movie object to a user interface card and
 * providing functionality for editing and removing the movie through user actions.
 */
public class EditorMovieCardController {

    /**
     * Represents the root layout container for the user interface of the editor movie card.
     *
     * This VBox serves as the top-level container for organizing the layout of the movie card’s
     * visual elements and controls within the application. It is an FXML-injected field and is
     * primarily used for grouping child components such as labels, buttons, and other UI elements
     * related to a specific movie card.
     */
    @FXML
    private VBox root;
    /**
     * Displays the title of the movie associated with this movie card.
     *
     * This FXML-injected Label element is bound to the corresponding Movie object
     * and dynamically updated to reflect the title of the movie being represented
     * by the EditorMovieCardController. It serves as a key visual element in the
     * user interface, allowing users to identify the movie associated with the
     * editor card.
     */
    @FXML
    private Label movieTitleLabel;
    /**
     * Represents a button for editing the movie associated with this editor movie card.
     *
     * This FXML-injected Button element allows users to initiate the editing process for the movie
     * represented by the EditorMovieCardController. The edit action is configured dynamically using
     * the {@link #setData(Movie, Runnable, Runnable)} method, where a provided Runnable is executed
     * when the button is clicked.
     */
    @FXML
    private Button editButton;
    /**
     * Represents a button for removing the movie associated with this editor movie card.
     *
     * This FXML-injected Button element triggers the removal action for the movie
     * represented by the EditorMovieCardController. The removal behavior is dynamically
     * configured using the {@link #setData(Movie, Runnable, Runnable)} method, where a
     * provided Runnable is executed when the button is clicked.
     */
    @FXML
    private Button removeButton;
    /**
     * Holds a Movie object associated with the editor movie card.
     *
     * This field represents the movie currently being managed by the EditorMovieCardController.
     * It is set through the {@link #setData(Movie, Runnable, Runnable)} method, which binds
     * the movie to the card and updates the user interface accordingly.
     *
     * The movie's data, such as its title, can be displayed or managed using this instance.
     */
    private Movie movie;

    /**
     * Updates the data displayed in the user interface with the specified movie
     * and sets up actions for editing and removing the movie.
     *
     * @param pMovie      the movie to display; can be null, in which case no movie data
     *                    is displayed
     * @param editAction  a runnable defining the action to perform when the edit
     *                    button is clicked; can be null
     * @param removeAction a runnable defining the action to perform when the
     *                     remove button is clicked; can be null
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

    /**
     * Retrieves the movie associated with this instance.
     *
     * @return the movie object if one is set, or null if no movie is associated
     */
    public Movie getMovie() {
        return movie;
    }

    /**
     * Retrieves the root VBox container associated with this instance.
     *
     * @return the root VBox of this controller
     */
    public VBox getRoot() {
        return root;
    }
}