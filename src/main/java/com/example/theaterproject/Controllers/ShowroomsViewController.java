package com.example.theaterproject.Controllers;

import com.example.theaterproject.Services.UIService;
import com.example.theaterproject.Models.Screening;
import com.example.theaterproject.Models.Showroom;
import com.example.theaterproject.Services.ShowroomService;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Controller for the showrooms view.
 *
 * <p>
 * Manages the display and interaction with showroom cards in a grid layout.
 * Provides navigation to other views and handles showroom creation/editing.
 * Automatically updates the grid when showrooms are added, modified, or removed.
 * </p>
 */
public class ShowroomsViewController {

    @FXML
    /**
     * Grid pane used to display showroom cards in a tiled layout.
     */
    private GridPane aMovieGridPane;
    @FXML
    /**
     * Navigation button returning to the editor (movies) view.
     */
    private Button homeViewButton;
    @FXML
    /**
     * Navigation button opening the statistics view.
     */
    private Button statsViewButton;
    @FXML
    /**
     * Button to open the add showroom dialog.
     */
    private Button addButton;

    private final ShowroomService aShowroomService = ShowroomService.getInstance();
    private final UIService aUIService = UIService.getInstance();

    /**
     * Initializes the showrooms view.
     * Populates the grid with showroom cards and adds a listener to update the grid when showrooms change.
     */
    @FXML
    public void initialize() {
        fillShowroomGridPane();

        this.aShowroomService.getShowrooms().addListener((ListChangeListener<Showroom>) change -> {
            fillShowroomGridPane();
        });
    }

    /**
     * Handles the "Home View" button click event.
     * Navigates back to the editor view (movies view).
     *
     * @param pEvent the action event triggered by the button
     */
    @FXML
    private void onHomeViewButtonClick(ActionEvent pEvent) {
        try {
            aUIService.openNewWindow("editor-view", "Movies", pEvent, 900, 700);
        } catch (IOException e) {
            aUIService.showErrorAlert("Error", "Failed to load editor view: " + e.getMessage());
        }
    }

    /**
     * Handles the "Stats View" button click event.
     * Navigates to the statistics view.
     *
     * @param pEvent the action event triggered by the button
     */
    @FXML
    private void onStatsViewButtonClick(ActionEvent pEvent) {
        try {
            aUIService.openNewWindow("stats-view", "Statistics", pEvent, 700, 500);
        } catch (IOException e) {
            aUIService.showErrorAlert("Error", "Failed to load stats view: " + e.getMessage());
        }
    }

    /**
     * Handles the "Add" button click event.
     * Opens a dialog to create a new showroom.
     *
     * @param pEvent the action event triggered by the button
     */
    @FXML
    private void onAddButtonClick(ActionEvent pEvent) {
        openShowroomAddEditView(null);
    }

    /**
     * Opens the showroom add/edit dialog.
     *
     * @param pShowroom the showroom to edit, or null to create a new showroom
     */
    private void openShowroomAddEditView(Showroom pShowroom) {
        try {
            FXMLLoader loader = aUIService.loadFXML("showroom-add-edit-view");
            Parent root = loader.getRoot();

            ShowroomAddEditViewController controller = loader.getController();
            controller.setShowroomEditView(pShowroom);

            Stage modal = new Stage();
            modal.setScene(new Scene(root));
            modal.initModality(Modality.APPLICATION_MODAL);
            modal.setTitle("Showroom Add/Edit");
            modal.showAndWait();
        } catch (IOException e) {
            aUIService.showErrorAlert("Error", "Failed to load showroom dialog: " + e.getMessage());
        }
    }

    /**
     * Populates the grid pane with showroom cards.
     * Clears existing cards and creates new ones for all showrooms in the service.
     */
    private void fillShowroomGridPane() {
        this.aMovieGridPane.getChildren().clear();

        int columns = 3;
        int col = 0;
        int rows = 0;

        for (Showroom showroom : this.aShowroomService.getShowrooms()) {
            addShowroomCard(showroom, col, rows);

            col++;
            if (col == columns) {
                col = 0;
                rows++;
            }
        }
    }

    /**
     * Adds a showroom card to the grid at the specified position.
     * Sets up a listener to update the card when the showroom's screenings change.
     *
     * @param pShowroom the showroom to display
     * @param pColumn the column position in the grid
     * @param pRow the row position in the grid
     */
    private void addShowroomCard(Showroom pShowroom, int pColumn, int pRow) {
        try {
            FXMLLoader loader = aUIService.loadFXML("showroom-card-view");
            Parent card = loader.getRoot();

            ShowroomCardController controller = loader.getController();
            controller.setShowroomCard(pShowroom);

            // Listen for changes in screenings so card updates when screenings change
            pShowroom.getShowroomScreenings().addListener((ListChangeListener<Screening>) change -> {
                controller.setShowroomCard(pShowroom);
            });

            this.aMovieGridPane.add(card, pColumn, pRow);
        } catch (Exception e) {
            aUIService.showErrorAlert("Error", "Failed to load showroom card: " + e.getMessage());
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
            aUIService.openNewWindow("login-view", "Log In", pEvent, 600, 300);
        } catch (IOException e) {
            aUIService.showErrorAlert("Error", e.getMessage());
        }
    }

}