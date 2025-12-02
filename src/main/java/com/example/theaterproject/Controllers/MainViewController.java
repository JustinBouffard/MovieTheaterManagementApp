package com.example.theaterproject.Controllers;

import com.example.theaterproject.Models.Movie;
import com.example.theaterproject.Services.MovieService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Labeled;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Map;

/**
 * Controller for the main view. Populates the GridPane once with movie cards
 * loaded from movie-card-view.fxml for each Movie provided by MovieService.
 *
 * This version is more robust about FXML resource paths and supports two ways
 * of populating a loaded card:
 *  - If the card FXML specifies a controller with a setMovie(...) API, that controller is used.
 *  - Otherwise, the code falls back to using the FXMLLoader namespace to find fx:id nodes.
 */
public class MainViewController {

    @FXML
    private GridPane movieGridPane;

    private final MovieService movieService = MovieService.getInstance();

    @FXML
    private void initialize() {
        // populate once at startup
        populateGrid(movieService.getMovies());
    }

    /**
     * Loads a movie-card-view.fxml for each movie and places it into the GridPane.
     *
     * This method tries first to use a controller defined inside the card FXML
     * (and call setMovie(movie) on it). If that is not present it falls back
     * to the namespace label-setting approach used previously.
     *
     * @param movies the list of movies to display (may be empty)
     */
    private void populateGrid(javafx.collections.ObservableList<Movie> movies) {
        movieGridPane.getChildren().clear();

        if (movies == null || movies.isEmpty()) {
            return;
        }

        // Determine number of columns from GridPane constraints if present, otherwise default to 3
        int columns = 3;
        if (movieGridPane.getColumnConstraints() != null && movieGridPane.getColumnConstraints().size() > 0) {
            columns = Math.max(1, movieGridPane.getColumnConstraints().size());
        }

        int index = 0;
        for (Movie movie : movies) {
            try {
                // Try relative resource first (same package), fall back to absolute path
                URL fxmlUrl = getClass().getResource("movie-card-view.fxml");
                if (fxmlUrl == null) {
                    fxmlUrl = getClass().getResource("/com/example/theaterproject/movie-card-view.fxml");
                }
                if (fxmlUrl == null) {
                    // nothing we can do for this card; print and skip
                    System.err.println("Could not locate movie-card-view.fxml resource for movie: " + movie.getTitle());
                    continue;
                }

                FXMLLoader loader = new FXMLLoader(fxmlUrl);
                Parent card = loader.load();

                // If the FXML declares a controller and it supports setMovie, use it.
                Object cardController = loader.getController();
                if (cardController != null) {
                    try {
                        // Try to call setMovie reflectively (keeps coupling low)
                        java.lang.reflect.Method setMovie = cardController.getClass().getMethod("setMovie", Movie.class);
                        if (setMovie != null) {
                            setMovie.invoke(cardController, movie);
                        }
                    } catch (NoSuchMethodException nsme) {
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

    @FXML
    private void onSignOutButtonClick(ActionEvent pEvent){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/theaterproject/login-view.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Log In");
            stage.setScene(new Scene(root, 700, 400));
            stage.show();

            // Close current window
            Stage currentStage = (Stage) ((Node) pEvent.getSource()).getScene().getWindow();
            currentStage.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}