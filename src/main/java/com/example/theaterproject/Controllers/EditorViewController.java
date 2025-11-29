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

/**
 * EditorViewController
 *
 * - Attempts to load a reusable card FXML for each Movie.
 * - Supports cards that have a dedicated controller (EditorMovieCardController) or that do not.
 * - Adds a modal dialog to add a movie and immediately refreshes the grid.
 * - Provides a small compatibility shim for a typo that exists in the editor-view.fxml
 *   (onAction="#onAddMovieButtonCLick") so FXML doesn't need to be changed immediately.
 */
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

    /**
     * Compatibility shim to match the typo in the FXML:
     * editor-view.fxml had onAction="#onAddMovieButtonCLick"
     * (capital 'L' in CLick). This method simply forwards to the correctly spelled handler.
     *
     * This avoids changing the FXML immediately.
     */
    @FXML
    private void onAddMovieButtonCLick(ActionEvent pEvent) {
        onAddMovieButtonClick(pEvent);
    }

    /**
     * Open a modal dialog to collect movie details, validate, add to service and refresh grid.
     */
    @FXML
    private void onAddMovieButtonClick(ActionEvent pEvent) {
        Dialog<Movie> dialog = new Dialog<>();
        dialog.setTitle("Add Movie");
        dialog.setHeaderText("Enter movie details");
        dialog.initModality(Modality.APPLICATION_MODAL);

        ButtonType addButtonType = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButtonType, ButtonType.CANCEL);

        // Create form fields
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField genreField = new TextField();
        genreField.setPromptText("Genre");

        TextField titleField = new TextField();
        titleField.setPromptText("Title");

        TextField directorField = new TextField();
        directorField.setPromptText("Director");

        TextField yearField = new TextField();
        yearField.setPromptText("Year (e.g. 2024)");

        TextArea descriptionArea = new TextArea();
        descriptionArea.setPromptText("Short description");
        descriptionArea.setPrefRowCount(3);

        TextField runtimeField = new TextField();
        runtimeField.setPromptText("Runtime (minutes)");

        grid.add(new Label("Genre:"), 0, 0);
        grid.add(genreField, 1, 0);
        grid.add(new Label("Title:"), 0, 1);
        grid.add(titleField, 1, 1);
        grid.add(new Label("Director:"), 0, 2);
        grid.add(directorField, 1, 2);
        grid.add(new Label("Year:"), 0, 3);
        grid.add(yearField, 1, 3);
        grid.add(new Label("Runtime (min):"), 0, 4);
        grid.add(runtimeField, 1, 4);
        grid.add(new Label("Description:"), 0, 5);
        grid.add(descriptionArea, 1, 5);

        dialog.getDialogPane().setContent(grid);

        // Enable/Disable Add button based on mandatory fields (title must be filled)
        Node addButton = dialog.getDialogPane().lookupButton(addButtonType);
        addButton.setDisable(true);

        titleField.textProperty().addListener((obs, oldVal, newVal) -> {
            addButton.setDisable(newVal == null || newVal.trim().isEmpty());
        });

        // Convert the result to a Movie when the Add button is clicked.
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addButtonType) {
                String genre = genreField.getText().trim();
                String title = titleField.getText().trim();
                String director = directorField.getText().trim();
                String yearText = yearField.getText().trim();
                String description = descriptionArea.getText().trim();
                String runtimeText = runtimeField.getText().trim();

                // Basic validation
                if (title.isEmpty()) {
                    showAlert(Alert.AlertType.ERROR, "Validation error", "Title is required.");
                    return null;
                }
                int year;
                int runtime;
                try {
                    year = Integer.parseInt(yearText);
                } catch (NumberFormatException e) {
                    showAlert(Alert.AlertType.ERROR, "Validation error", "Year must be an integer.");
                    return null;
                }
                try {
                    runtime = Integer.parseInt(runtimeText);
                } catch (NumberFormatException e) {
                    showAlert(Alert.AlertType.ERROR, "Validation error", "Runtime must be an integer (minutes).");
                    return null;
                }

                try {
                    return new Movie(
                            genre.isEmpty() ? "Unknown" : genre,
                            title,
                            director.isEmpty() ? "Unknown" : director,
                            year,
                            description.isEmpty() ? "No description" : description,
                            runtime
                    );
                } catch (IllegalArgumentException ex) {
                    showAlert(Alert.AlertType.ERROR, "Validation error", ex.getMessage());
                    return null;
                }
            }
            return null;
        });

        // Show dialog and wait
        dialog.initOwner(((Node) pEvent.getSource()).getScene().getWindow());
        dialog.showAndWait().ifPresent(movie -> {
            // Add movie to service and refresh grid
            aMovieService.addMovie(movie);
            // if aMovies is the unmodifiable view, re-read from service to ensure up-to-date
            aMovies = aMovieService.getMovies();
            populateGrid(aMovies);
        });
    }

    @FXML
    private void onShowroomsViewButtonClick(ActionEvent pEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("showroom-add-edit-view.fxml"));
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
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("stats-view.fxml"));
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

    /**
     * Loads an editor-style card for a movie. This method is resilient:
     * - tries to load several likely resource names for the card
     * - if the card has a controller instance of EditorMovieCardController, it calls setData(...)
     * - otherwise it falls back to node lookups (title label, buttons) and wires actions
     */
    private VBox loadCardForMovie(Movie movie) {
        List<String> candidates = List.of(
                "editor-movie-card-view.fxml",
                "editor-movie-card.fxml",
                "movie-card-view.fxml",
                "movie-card.fxml"
        );

        for (String candidate : candidates) {
            try {
                URL fxmlUrl = getClass().getResource(candidate);
                if (fxmlUrl == null) {
                    // try absolute path as a fallback
                    fxmlUrl = getClass().getResource("/com/example/theaterproject/" + candidate);
                }
                if (fxmlUrl == null) continue;

                FXMLLoader loader = new FXMLLoader(fxmlUrl);
                Parent root = loader.load();

                Object controller = loader.getController();
                Runnable editAction = () -> openEditorFor(movie);
                Runnable removeAction = () -> {
                    aMovieService.removeMovie(movie);
                    populateGrid(aMovies);
                };

                // If we have the strongly-typed controller, use it.
                if (controller instanceof EditorMovieCardController) {
                    EditorMovieCardController cardCtrl = (EditorMovieCardController) controller;
                    cardCtrl.setData(movie, editAction, removeAction);
                    return (VBox) root;
                }

                // Otherwise fallback to lookup-based wiring so the card works without a controller.
                // Try to set the title label. Attempt multiple possible fx:id names.
                Label titleLabel = (Label) root.lookup("#movieTitleLabel");
                if (titleLabel == null) titleLabel = (Label) root.lookup("#movieTitleLabel1");
                if (titleLabel == null) titleLabel = (Label) root.lookup(".label"); // last resort
                if (titleLabel != null) titleLabel.setText(movie != null ? movie.getTitle() : "");

                // Wire the buttons: find Buttons by CSS selector (".button") and wire edit/remove actions.
                var found = root.lookupAll(".button");
                List<Button> buttons = new ArrayList<>();
                for (var n : found) {
                    if (n instanceof Button) buttons.add((Button) n);
                }
                // Heuristic: first button -> Edit, second -> Remove (matches many card layouts)
                if (buttons.size() >= 1) buttons.get(0).setOnAction(e -> editAction.run());
                if (buttons.size() >= 2) buttons.get(1).setOnAction(e -> removeAction.run());

                return (VBox) root;
            } catch (IOException e) {
                // try the next candidate
                e.printStackTrace();
            } catch (ClassCastException cce) {
                // The loaded root was not a VBox; skip candidate
                cce.printStackTrace();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        // nothing worked
        return null;
    }

    private void openEditorFor(Movie movie) {
        // TODO: wire to your movie-edit FXML and pass the movie instance into that controller.
        // For now we just print to console to show it was invoked.
        System.out.println("Open editor for movie: " + (movie != null ? movie.getTitle() : "unknown"));
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