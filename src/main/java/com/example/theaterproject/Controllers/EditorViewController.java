package com.example.theaterproject.Controllers;

import com.example.theaterproject.Models.Movie;
import com.example.theaterproject.Services.MovieService;
import com.example.theaterproject.Services.UIService;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class EditorViewController {
    @FXML
    private Button addMovieButton;

    @FXML
    private Button showroomsViewButton;

    @FXML
    private Button statsViewButton;

    @FXML
    private GridPane moviesGridPane;

    private ObservableList<Movie> aMovies;

    private final MovieService aMovieService = MovieService.getInstance();

    private static final int COLUMNS = 3;
    private static final int PADDING = 10;
    private static final int GAP = 10;

    @FXML
    public void initialize() {
        if (moviesGridPane != null) {
            moviesGridPane.getChildren().clear();
        }

        aMovies = aMovieService.getMovies();

        // static list use-case: only need to populate once on initialize
        populateGrid(aMovies);
    }

    private void populateGrid(ObservableList<Movie> pMovies) {
        if (moviesGridPane == null || pMovies == null) return;

        moviesGridPane.getChildren().clear();
        moviesGridPane.getColumnConstraints().clear();
        moviesGridPane.setPadding(new Insets(PADDING));
        moviesGridPane.setHgap(GAP);
        moviesGridPane.setVgap(GAP);

        for (int i = 0; i < pMovies.size(); i++) {
            Movie m = pMovies.get(i);
            int col = i % COLUMNS;
            int row = i / COLUMNS;

            VBox card = loadCardForMovie(m);
            if (card != null) {
                moviesGridPane.add(card, col, row);
                GridPane.setVgrow(card, Priority.NEVER);
                GridPane.setHgrow(card, Priority.NEVER);
            }
        }
    }

    @FXML
    private void onAddMovieButtonClick(ActionEvent event) {
        try {
            FXMLLoader loader = UIService.loadFXML("add-edit-movie-view");
            Parent root = loader.getRoot();

            // Replace the current window scene
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            UIService.showErrorAlert("Error", e.getMessage());
        }
    }

    @FXML
    private void onShowroomsViewButtonClick(ActionEvent pEvent) {
        try {
            UIService.openModalWindow("showrooms-view", "Showrooms", pEvent, 900, 500);
        } catch (IOException e) {
            UIService.showErrorAlert("Error", e.getMessage());
        }
    }

    @FXML
    private void onStatsViewButtonClick(ActionEvent pEvent) {
        try {
            UIService.openModalWindow("stats-view", "Statistics", pEvent, 900, 500);
        } catch (IOException e) {
            UIService.showErrorAlert("Error", e.getMessage());
        }
    }

    private VBox loadCardForMovie(Movie movie) {
        try {
            FXMLLoader loader = UIService.loadFXML("editor-movie-card-view");
            VBox root = loader.getRoot();

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
            UIService.showErrorAlert("Error", e.getMessage());
            return null;
        }
    }
}