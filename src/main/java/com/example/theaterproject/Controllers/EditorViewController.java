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

/**
 * The EditorViewController class provides the logic for managing and interacting
 * with the movie editor view in the application. It enables dynamic rendering
 * and manipulation of movie data within the editor interface.
 *
 * Features include:
 * - Displaying a grid of movie cards
 * - Adjusting the number of columns in the grid dynamically based on viewport size
 * - Adding, editing, or removing movies from the list via user interactions
 * - Switching between different application views like add/edit movie, showrooms view, and statistics view
 *
 * This class uses JavaFX components like Button, TilePane, ScrollPane, and VBox
 * to achieve the UI rendering and handles user events with FXML integration.
 */
public class EditorViewController {
    /**
     * A button in the user interface that allows the user to initiate the process
     * of adding a new movie. When clicked, this button triggers the corresponding
     * event handler to perform the action associated with adding a movie to the
     * system.
     *
     * This button is linked to an action event handler method that is expected to
     * handle the logic for what occurs when the button is clicked.
     */
    @FXML private Button addMovieButton;
    /**
     * A button within the EditorViewController that, when clicked, navigates the user
     * to the Showrooms View. This button is part of the JavaFX FXML structure.
     */
    @FXML private Button showroomsViewButton;
    /**
     * A button associated with the "Stats View" feature in the application.
     * This button, when triggered, is expected to navigate the user to
     * a view displaying statistical data or analytics related to the application's features.
     *
     * This field is linked with an FXML file and is injected at runtime by the JavaFX framework.
     */
    @FXML private Button statsViewButton;
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

    @FXML private TilePane movieGridPane;
    /**
     * ScrollPane used to display movies in the editor view.
     * The content of this ScrollPane is dynamically managed to show a grid of movie cards.
     * Allows users to scroll through the list of movies.
     */
    @FXML private ScrollPane movieScrollPane;
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
    /**
     * Defines the default number of columns used to arrange movie cards
     * in the grid layout within the {@code EditorViewController}.
     *
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

    /**
     * Updates the number of columns in the movie grid based on the available viewport width.
     * The method calculates the number of columns that can fit within the given width,
     * taking into account horizontal gaps and padding. If the calculated number of columns
     * is less than 1, it sets the grid to have at least one column.
     *
     * @param viewportWidth the current width of the viewport used to determine the number of columns
     *                      in the movie grid. A non-positive width results in no changes being made.
     */
    private void updateColumnsForWidth(double viewportWidth) {
        if (viewportWidth <= 0) return;
        double hgap = movieGridPane.getHgap();
        double leftRightPad = movieGridPane.getPadding().getLeft() + movieGridPane.getPadding().getRight();
        double available = Math.max(0, viewportWidth - leftRightPad);
        int cols = (int) Math.floor((available + hgap) / (CARD_TILE_WIDTH + hgap));
        if (cols < 1) cols = 1;
        movieGridPane.setPrefColumns(cols);
    }

    /**
     * Populates the movie grid with a collection of movies. This method clears
     * the existing movie grid content and dynamically generates movie cards for
     * each movie in the provided list. Each card includes actions for editing
     * or removing the movie.
     *
     * If the list of movies is null or empty, the grid remains empty.
     *
     * @param movies the list of movies to populate the grid with. Each movie in
     *               the list is rendered as a card in the grid. If null or empty,
     *               no cards will be displayed.
     */
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


    /**
     * Handles the event triggered by clicking the "Add Movie" button.
     * This method switches the current scene to the Add/Edit Movie view,
     * allowing users to create or edit movie entries.
     *
     * @param event the {@code ActionEvent} triggered by the button click.
     */
    @FXML
    private void onAddMovieButtonClick(ActionEvent event) {
        switchView(event, "/com/example/theaterproject/add-edit-movie-view.fxml");
    }

    /**
     * Handles the event triggered by clicking the "Showrooms View" button.
     * This method switches the current scene to the Showrooms View,
     * allowing users to view and manage theater showrooms.
     *
     * @param event the {@code ActionEvent} triggered by the button click.
     */
    @FXML
    private void onShowroomsViewButtonClick(ActionEvent event) {
        switchView(event, "/com/example/theaterproject/showrooms-view.fxml");
    }

    /**
     * Handles the event triggered by clicking the "Statistics View" button.
     * This method switches the current scene to the Statistics View,
     * allowing users to view theater-related statistical data.
     *
     * @param event the {@code ActionEvent} triggered by the button click.
     */
    @FXML
    private void onStatsViewButtonClick(ActionEvent event) {
        switchView(event, "/com/example/theaterproject/stats-view.fxml");
    }


    /**
     * Switches the current view to the view specified by the provided FXML file path.
     * The method loads the specified FXML file, sets it as the scene for the current stage,
     * and displays the updated stage.
     *
     * @param event    the {@code ActionEvent} that triggered the view switch. Used to retrieve
     *                 the current stage from the event source.
     * @param fxmlPath the path to the FXML file that defines the new view to be loaded.
     *                 It should be a valid path within the application's resources.
     */
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

    /**
     * Displays an alert dialog with the specified type, title, and message. The dialog is
     * modal and waits for the user's acknowledgement before proceeding. The header of the
     * alert is not displayed.
     *
     * @param type    the type of alert to display, such as information, warning, error, or confirmation
     * @param title   the title of the alert dialog
     * @param message the message to display in the content area of the alert dialog
     */
    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert a = new Alert(type);
        a.setTitle(title);
        a.setHeaderText(null);
        a.setContentText(message);
        a.initModality(Modality.APPLICATION_MODAL);
        a.showAndWait();
    }
}
