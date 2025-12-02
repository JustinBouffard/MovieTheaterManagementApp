package com.example.theaterproject.Controllers;

import com.example.theaterproject.Models.Movie;
import com.example.theaterproject.Services.MovieService;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Map;

public class EditorViewController {
    @FXML
    private Button addMovieButton;

    @FXML
    private Button showroomsViewButton;

    @FXML
    private Button statsViewButton;

    @FXML
    private GridPane movieGridPane;

    private ObservableList<Movie> aMovies;

    private final MovieService aMovieService = MovieService.getInstance();

    private static final int COLUMNS = 3;
    private static final int PADDING = 10;
    private static final int GAP = 10;

    @FXML
    public void initialize() {
        if (movieGridPane != null) {
            movieGridPane.getChildren().clear();
        }

        aMovies = aMovieService.getMovies();

        // static list use-case: only need to populate once on initialize
        populateGrid(aMovies);
    }

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
                URL fxmlUrl = getClass().getResource("/com/example/theaterproject/movie-card-view.fxml");
                if (fxmlUrl == null) {
                    System.err.println("Could not locate movie-card-view.fxml");
                    continue;
                }

                FXMLLoader loader = new FXMLLoader(fxmlUrl);
                Parent card = loader.load();

                if (fxmlUrl == null) {
                    // nothing we can do for this card; print and skip
                    System.err.println("Could not locate movie-card-view.fxml resource for movie: " + movie.getTitle());
                    continue;
                }

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
    private void onAddMovieButtonClick(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/example/theaterproject/add-edit-movie-view.fxml")
            );
            Parent root = loader.load();

            // Replace the current window scene
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onShowroomsViewButtonClick(ActionEvent pEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/theaterproject/showrooms-view.fxml"));
            Parent root = fxmlLoader.load();

            Stage newStage = new Stage();
            newStage.setTitle("Statistics");
            newStage.setScene(new Scene(root, 700, 500));
            newStage.show();

            Stage currentStage = (Stage) ((Node) pEvent.getSource()).getScene().getWindow();
            currentStage.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onStatsViewButtonClick(ActionEvent pEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/theaterproject/stats-view.fxml"));
            Parent root = fxmlLoader.load();

            Stage newStage = new Stage();
            newStage.setTitle("Statistics");
            newStage.setScene(new Scene(root, 700, 500));
            newStage.show();

            Stage currentStage = (Stage) ((Node) pEvent.getSource()).getScene().getWindow();
            currentStage.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private VBox loadCardForMovie(Movie movie) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/example/theaterproject/editor-movie-card-view.fxml")
            );
            VBox root = loader.load();

            EditorMovieCardController controller = loader.getController();
//            if (controller != null) {
//                controller.setData(
//                        movie,
//                        () -> onAddMovieButtonClick(),             // EDIT action
//                        () -> {                                 // DELETE action
//                            aMovieService.removeMovie(movie);
//                            populateGrid(aMovies);
//                        }
//                );
//            }

            return root;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }



    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert a = new Alert(type);
        a.setTitle(title);
        a.setHeaderText(null);
        a.setContentText(message);
        a.initModality(Modality.APPLICATION_MODAL);
        a.showAndWait();
    }
}