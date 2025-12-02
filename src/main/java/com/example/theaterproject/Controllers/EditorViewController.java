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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

import javafx.scene.control.ButtonType;

public class EditorViewController {

    @FXML private Button addMovieButton;
    @FXML private Button showroomsViewButton;
    @FXML private Button statsViewButton;

    @FXML private GridPane movieGridPane;

    private ObservableList<Movie> aMovies;
    private final MovieService aMovieService = MovieService.getInstance();

    private static final int COLUMNS = 3;

    @FXML
    public void initialize() {
        movieGridPane.getChildren().clear();
        aMovies = aMovieService.getMovies();
        populateGrid(aMovies);
    }

    private void populateGrid(ObservableList<Movie> movies) {
        movieGridPane.getChildren().clear();

        if (movies == null || movies.isEmpty()) return;

        int index = 0;

        for (Movie movie : movies) {
            try {
                FXMLLoader loader = new FXMLLoader(
                        getClass().getResource("/com/example/theaterproject/editor-movie-card-view.fxml")
                );

                VBox card = loader.load();
                EditorMovieCardController cardController = loader.getController();

                if (cardController != null) {
                    cardController.setData(
                            movie,

                            // EDIT ACTION: switch to Add/Edit view and close Editor view (same window)
                            () -> {
                                try {
                                    FXMLLoader editLoader = new FXMLLoader(
                                            getClass().getResource("/com/example/theaterproject/add-edit-movie-view.fxml")
                                    );
                                    Parent root = editLoader.load();
                                    MovieAddEditViewController editController = editLoader.getController();
                                    if (editController != null) {
                                        editController.setEditingMovie(movie);
                                    }

                                    // Replace current scene so the editor window is effectively closed
                                    Stage stage = (Stage) movieGridPane.getScene().getWindow();
                                    stage.setScene(new Scene(root));
                                    stage.show();
                                } catch (IOException ex) {
                                    ex.printStackTrace();
                                }
                            },

                            // REMOVE ACTION with confirmation
                            () -> {
                                Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
                                confirm.setTitle("Confirm Deletion");
                                confirm.setHeaderText(null);
                                confirm.setContentText("Remove '" + movie.getTitle() + "'? This cannot be undone.");
                                Optional<ButtonType> result = confirm.showAndWait();
                                if (result.isPresent() && result.get() == ButtonType.OK) {
                                    aMovieService.removeMovie(movie);
                                    populateGrid(aMovies);
                                }
                            }
                    );
                }

                int col = index % COLUMNS;
                int row = index / COLUMNS;

                movieGridPane.add(card, col, row);
                GridPane.setMargin(card, new Insets(8));

                index++;

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    @FXML
    private void onAddMovieButtonClick(ActionEvent event) {
        switchView(event, "/com/example/theaterproject/add-edit-movie-view.fxml");
    }

    @FXML
    private void onShowroomsViewButtonClick(ActionEvent event) {
        switchView(event, "/com/example/theaterproject/showrooms-view.fxml");
    }

    @FXML
    private void onStatsViewButtonClick(ActionEvent event) {
        switchView(event, "/com/example/theaterproject/stats-view.fxml");
    }


    /** Generic Scene Switcher */
    private void switchView(ActionEvent event, String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
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
