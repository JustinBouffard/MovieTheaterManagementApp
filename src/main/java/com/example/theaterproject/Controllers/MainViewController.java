package com.example.theaterproject.Controllers;

import com.example.theaterproject.Models.Movie;
import com.example.theaterproject.Services.MovieService;
import com.example.theaterproject.Services.UIService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.geometry.Insets;
import javafx.scene.control.Labeled;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Map;

/**
 * The MainViewController class is responsible for managing the main view of the application.
 *
 * <p>
 * This class loads and displays a grid of movie cards and handles user interactions such as
 * signing out. It primarily interacts with the {@code movieGridPane} to display movie information
 * and utilizes the {@code MovieService} singleton to retrieve the list of movies.
 * It uses FXML annotations for initializing and managing UI elements.
 * </p>
 */
public class MainViewController {
    /**
     * Represents a grid pane used to display a collection of movies in the main view.
     *
     * This field is linked to the FXML file associated with the MainViewController and is used
     * to create a structured layout of movie-related components or information.
     *
     * The grid pane is populated dynamically with movie data, where each movie is represented
     * as a visual component within the pane. It allows seamless organization and presentation
     * of movies in a grid-based arrangement.
     *
     * This field is initialized and managed as part of the application's user interface for
     * displaying movies to the user.
     */
    @FXML
    private GridPane movieGridPane;
    /**
     * Represents a singleton instance of the {@link MovieService} class,
     * which provides centralized management of movie-related data within the application.
     *
     * This variable is used by the {@code MainViewController} to populate and interact
     * with the list of movies displayed in the user interface. It allows access to the
     * movie data and supports operations such as retrieving, adding, and removing movies.
     */
    private final MovieService movieService = MovieService.getInstance();

    private final UIService aUiService = UIService.getInstance();

    /**
     * Initializes the main view controller. This method is automatically invoked
     * when the associated FXML is loaded. It prepares the view by populating the
     * grid with movie cards using the current list of movies provided by the
     * movieService.
     */
    @FXML
    private void initialize() {
        // populate once at startup
        populateGrid(movieService.getMovies());
    }

    /**
     * Populates the grid pane with movie cards based on the provided list of movies.
     * Each movie is represented as a card created from an FXML template.
     * The grid is cleared before populating it with the new set of movie cards.
     *
     * @param movies the list of movies to be displayed in the grid. If the list is null
     *               or empty, the grid will not be populated.
     */
    private void populateGrid(javafx.collections.ObservableList<Movie> movies) {
        movieGridPane.getChildren().clear();

        if (movies == null || movies.isEmpty()) {
            return;
        }

        // Determine number of columns from GridPane constraints if present, otherwise default to 3
        int columns = 3;
        if (movieGridPane.getColumnConstraints() != null && !movieGridPane.getColumnConstraints().isEmpty()) {
            columns = movieGridPane.getColumnConstraints().size();
        }

        int index = 0;
        for (Movie movie : movies) {
            try {
                // Load via UIService using application resource path
                FXMLLoader loader = aUiService.loadFXML("movie-card-view");
                Parent card = loader.getRoot();

                // If the FXML declares a controller, and it supports setMovie, use it.
                Object cardController = loader.getController();
                if (cardController != null) {
                    try {
                        // Try to call setMovie reflectively (keeps coupling low)
                        java.lang.reflect.Method setMovie = cardController.getClass().getMethod("setMovie", Movie.class);
                        setMovie.invoke(cardController, movie);
                    } catch (NoSuchMethodException name) {
                        // Controller exists but does not have setMovie(Movie) - fall back to namespace below
                    } catch (Exception ex) {
                        // Reflection invocation problem - print and continue with fallback
                        ex.printStackTrace();
                    }
                }

                // If controller was null or did not set the title, attempt namespace-based label injection as fallback
                Map<String, Object> ns = loader.getNamespace();
                Object titleObj = ns.get("movieTitleLabel");
                Object screeningObj = ns.get("screeningTimeLabel");

                if (titleObj instanceof Labeled) {
                    ((Labeled) titleObj).setText(movie.getTitle());
                }
                if (screeningObj instanceof Labeled) {
                    ((Labeled) screeningObj).setText("Screening: TBD");
                }

                int col = index % columns;
                int row = index / columns;
                movieGridPane.add(card, col, row);
                GridPane.setMargin(card, new Insets(8));
                index++;
            } catch (IOException e) {
                // Loading failed for this card; print stack trace and continue so other cards show
                e.printStackTrace();
            } catch (Exception e) {
                // Catch-all to avoid one bad card breaking the grid
                e.printStackTrace();
            }
        }

    }

    /**
     * Handles the sign out button click event.
     * Opens the login view and closes the current window.
     *
     * @param pEvent the action event triggered by the sign out button
     */
    @FXML
    private void onSignOutButtonClick(ActionEvent pEvent) {
        try {
            aUiService.openNewWindow("login-view", "Log In", pEvent, 900, 500);
        } catch (IOException e) {
            aUiService.showErrorAlert("Error", e.getMessage());
        }
    }
}