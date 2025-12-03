package com.example.theaterproject.Controllers;

import com.example.theaterproject.Models.Movie;
import com.example.theaterproject.Services.MovieService;
import com.example.theaterproject.Services.UIService;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.TilePane;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class EditorViewController {

    /**
     * The TilePane that serves as a dynamic container for displaying movie cards.
     * Each movie card represents individual movie data and is added to this pane
     * dynamically based on content provided by the MovieService.
     *
     * This pane displays movie cards in a flexible grid-like layout. The number
     * of columns adjusts based on constraints of the pane or viewport width.
     *
     * The movie cards are populated from an associated movie-card-view.fxml file.
     * Each card is loaded with movie-specific data such as title or other attributes.
     *
     * This component is typically updated during initialization or when new movie
     * data is loaded, utilizing methods such as {@code populateGrid}.
     */

    @FXML private TilePane aMovieGridPane;
    /**
     * ScrollPane used to display movies in the editor view.
     * The content of this ScrollPane is dynamically managed to show a grid of movie cards.
     * Allows users to scroll through the list of movies.
     */
    @FXML private ScrollPane aMovieScrollPane;
    /**
     * Stores a list of Movie objects displayed or managed within the user interface.
     *
     * This observable list dynamically tracks changes to its content and updates
     * the UI components as needed. It is primarily used within the context of the
     * EditorViewController to manage and display a collection of movies.
     */

    private ObservableList<Movie> aMovies;
    /**
     * Provides a singleton instance of the MovieService for managing movie-related operations
     * within the application. This service is responsible for maintaining an observable list
     * of movies and offers methods to retrieve, add, set, or remove movies from the collection.
     *
     * The instance is initialized using the `MovieService.getInstance()` method to ensure
     * that a single shared instance is utilized throughout the application, maintaining a
     * consistent source of movie data.
     */
    private final MovieService aMovieService = MovieService.getInstance();

    private final UIService aUIService = UIService.getInstance();

    /**
     * Defines the default number of columns used to arrange movie cards
     * in the grid layout within the {@code EditorViewController}.
     * <p>
     * This value is utilized as a fallback when the column constraints
     * of the GridPane are not explicitly defined or unavailable.
     */
    private static final int DEFAULT_COLUMNS = 3;

    /**
     * Represents the default width of individual movie cards in the movie grid view.
     *
     * This value corresponds to the preferred width defined in the associated FXML file
     * (editor-movie-card-view.fxml) and is used to ensure consistency in the layout of
     * movie cards across the application.
     *
     * It is commonly referenced in calculations for grid adjustment and layout updates
     * in the editor view controller.
     */
    private static final double CARD_TILE_WIDTH = 160.0;

    /**
     * Initializes the editor view when it is first loaded. This method is invoked automatically
     * by the FXML loader.
     *
     * The initialization process involves clearing existing children from the movie grid,
     * setting consistent spacing, padding, and layout configurations for the grid, and preparing
     * it to dynamically adjust column counts based on the viewport's available width.
     *
     * Additionally, a listener is added to the viewport bounds property of the scroll pane to
     * automatically recalculate columns as the viewport size changes. On initialization, the grid
     * is immediately configured with the current viewport width if available.
     *
     * Movies obtained from the service are then populated into the grid by calling the
     * {@code populateGrid} method.
     */
    @FXML
    public void initialize() {
        aMovieGridPane.getChildren().clear();
        // Ensure consistent spacing around and between cards
        aMovieGridPane.setHgap(16);
        aMovieGridPane.setVgap(16);
        aMovieGridPane.setPadding(new Insets(10));
        aMovieGridPane.setPrefColumns(DEFAULT_COLUMNS);
        aMovieGridPane.setTileAlignment(Pos.TOP_LEFT);
        // Use tile width to help TilePane wrap dynamically instead of forcing fixed columns/width
        aMovieGridPane.setPrefTileWidth(CARD_TILE_WIDTH);

        // Recalculate columns based on available viewport width to make layout responsive
        if (aMovieScrollPane != null) {
            aMovieScrollPane.viewportBoundsProperty().addListener((obs, oldBounds, newBounds) -> updateColumnsForWidth(newBounds.getWidth()));
            // initialize once with current width if available
            updateColumnsForWidth(aMovieScrollPane.getViewportBounds().getWidth());
        }
        aMovies = aMovieService.getMovies();
        populateGrid(aMovies);
    }

    /**
     * Updates the number of columns in the movie grid based on the available viewport width.
     * Recalculates columns dynamically to optimize layout responsiveness.
     *
     * @param viewportWidth the available width of the viewport
     */
    private void updateColumnsForWidth(double viewportWidth) {
        if (viewportWidth <= 0) return;
        double hgap = aMovieGridPane.getHgap();
        double leftRightPad = aMovieGridPane.getPadding().getLeft() + aMovieGridPane.getPadding().getRight();
        double available = Math.max(0, viewportWidth - leftRightPad);
        int cols = (int) Math.floor((available + hgap) / (CARD_TILE_WIDTH + hgap));
        if (cols < 1) cols = 1;
        aMovieGridPane.setPrefColumns(cols);
    }

    /**
     * Populates the movie grid with a collection of movies. This method clears
     * the existing movie grid content and dynamically generates movie cards for
     * each movie in the provided list. Each card includes actions for editing
     * or removing the movie.
     * <p>
     * If the list of movies is null or empty, the grid remains empty.
     *
     * @param movies the list of movies to populate the grid with. Each movie in
     *               the list is rendered as a card in the grid. If null or empty,
     *               no cards will be displayed.
     */
    private void populateGrid(ObservableList<Movie> movies) {
        aMovieGridPane.getChildren().clear();

        if (movies == null || movies.isEmpty()) return;

        for (Movie movie : movies) {
            try {
                FXMLLoader loader = aUIService.loadFXML("editor-movie-card-view");

                VBox card = loader.getRoot();
                EditorMovieCardController cardController = loader.getController();

                if (cardController != null) {
                    cardController.setData(
                            movie,

                            // EDIT ACTION: switch to Add/Edit view and close Editor view (same window)
                            () -> {
                                try {
                                    FXMLLoader editLoader = aUIService.loadFXML("add-edit-movie-view");
                                    Parent root = editLoader.getRoot();
                                    MovieAddEditViewController editController = editLoader.getController();
                                    if (editController != null) {
                                        editController.setEditingMovie(movie);
                                    }

                                    // Replace current scene so the editor window is effectively closed
                                    Stage stage = (Stage) aMovieGridPane.getScene().getWindow();
                                    stage.setScene(new Scene(root));
                                    stage.show();
                                } catch (IOException ex) {
                                    aUIService.showErrorAlert("Error", "Failed to load movie editor: " + ex.getMessage());
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
                aMovieGridPane.getChildren().add(card);

            } catch (IOException e) {
                aUIService.showErrorAlert("Error", "Failed to load movie card: " + e.getMessage());
            }
        }
    }


    /**
     * Handles the "Add Movie" button click event.
     * Opens a modal dialog for adding a new movie.
     *
     * @param pEvent the action event triggered by the button
     */
    @FXML
    private void onAddMovieButtonClick(ActionEvent pEvent) {
        switchView(pEvent, "add-edit-movie-view");
    }

    /**
     * Handles the "Showrooms View" button click event.
     * Opens the showrooms view in a new window.
     *
     * @param pEvent the action event triggered by the button
     */
    @FXML
    private void onShowroomsViewButtonClick(ActionEvent pEvent) {
        try {
            aUIService.openNewWindow("showrooms-view", "Showrooms", pEvent, 900, 700);
        } catch (IOException e) {
            aUIService.showErrorAlert("Error", e.getMessage());
        }
    }

    /**
     * Handles the "Stats View" button click event.
     * Opens the statistics view in a new window.
     *
     * @param pEvent the action event triggered by the button
     */
    @FXML
    private void onStatsViewButtonClick(ActionEvent pEvent) {
        try {
            aUIService.openNewWindow("stats-view", "Showrooms", pEvent, 700, 500);
        } catch (IOException e) {
            aUIService.showErrorAlert("Error", e.getMessage());
        }
    }


    /**
     * Switches the current view to the view specified by the provided FXML file path.
     * The method loads the specified FXML file, sets it as the scene for the current stage,
     * and displays the updated stage.
     *
     * @param pEvent    the {@code ActionEvent} that triggered the view switch. Used to retrieve
     *                 the current stage from the pEvent source.
     * @param pFileName the path to the FXML file that defines the new view to be loaded.
     *                 It should be a valid path within the application's resources.
     */
    private void switchView(ActionEvent pEvent, String pFileName) {
        try {
            FXMLLoader loader = aUIService.loadFXML(pFileName);
            Parent root = loader.getRoot();

            Stage stage = (Stage) ((Node) pEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            aUIService.showErrorAlert("Error", "Failed to load view: " + e.getMessage());
        }
    }

}
