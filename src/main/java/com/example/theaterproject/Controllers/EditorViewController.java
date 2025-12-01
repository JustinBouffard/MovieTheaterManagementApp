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
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

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
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/theaterproject/showroom-add-edit-view.fxml"));
            Parent root = fxmlLoader.load();

            Stage newStage = new Stage();
            newStage.setTitle("Showrooms");
            newStage.setScene(new Scene(root, 480, 350));
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
            newStage.setScene(new Scene(root, 480, 350));
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