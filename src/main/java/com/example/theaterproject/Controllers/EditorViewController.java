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
// import javafx.scene.layout.GridPane; // no longer used after switching to TilePane
import javafx.scene.layout.TilePane;
import javafx.scene.layout.Region;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

import javafx.scene.control.ButtonType;
import javafx.scene.control.ScrollPane;

public class EditorViewController {

    @FXML private Button addMovieButton;
    @FXML private Button showroomsViewButton;
    @FXML private Button statsViewButton;

    @FXML private TilePane movieGridPane;
    @FXML private ScrollPane movieScrollPane;

    private ObservableList<Movie> aMovies;
    private final MovieService aMovieService = MovieService.getInstance();

    private static final int DEFAULT_COLUMNS = 3;
    private static final double CARD_TILE_WIDTH = 160.0; // matches editor-movie-card-view.fxml prefWidth

    @FXML
    public void initialize() {
        movieGridPane.getChildren().clear();
        // Ensure consistent spacing around and between cards
        movieGridPane.setHgap(16);
        movieGridPane.setVgap(16);
        movieGridPane.setPadding(new Insets(10));
        movieGridPane.setPrefColumns(DEFAULT_COLUMNS);
        movieGridPane.setTileAlignment(Pos.TOP_LEFT);
        // Use tile width to help TilePane wrap dynamically instead of forcing fixed columns/width
        movieGridPane.setPrefTileWidth(CARD_TILE_WIDTH);

        // Recalculate columns based on available viewport width to make layout responsive
        if (movieScrollPane != null) {
            movieScrollPane.viewportBoundsProperty().addListener((obs, oldBounds, newBounds) -> updateColumnsForWidth(newBounds.getWidth()));
            // initialize once with current width if available
            updateColumnsForWidth(movieScrollPane.getViewportBounds().getWidth());
        }
        aMovies = aMovieService.getMovies();
        populateGrid(aMovies);
    }

    private void updateColumnsForWidth(double viewportWidth) {
        if (viewportWidth <= 0) return;
        double hgap = movieGridPane.getHgap();
        double leftRightPad = movieGridPane.getPadding().getLeft() + movieGridPane.getPadding().getRight();
        double available = Math.max(0, viewportWidth - leftRightPad);
        int cols = (int) Math.floor((available + hgap) / (CARD_TILE_WIDTH + hgap));
        if (cols < 1) cols = 1;
        movieGridPane.setPrefColumns(cols);
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

                // TilePane handles layout and spacing; just add the card
                movieGridPane.getChildren().add(card);

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
