package com.example.theaterproject.Controllers;

import com.example.theaterproject.Models.Screening;
import com.example.theaterproject.Models.Showroom;
import com.example.theaterproject.Services.ShowroomService;
import com.example.theaterproject.Services.UIService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Controller for the showroom add/edit dialog view.
 *
 * <p>
 * This controller manages the UI for adding new showrooms or editing existing showrooms.
 * It handles showroom name and capacity input validation, and manages the list of screenings
 * for the showroom. Users can add, edit, and remove screenings from the showroom.
 * </p>
 */
public class ShowroomAddEditViewController {
    @FXML
    /**
     * Text field for entering or editing the showroom name.
     */
    private TextField aShowroomNameField;

    @FXML
    /**
     * Text field for entering the capacity; numeric-only validation applied.
     */
    private TextField aCapacityTextField;

    @FXML
    /**
     * List view displaying the screenings associated with the showroom.
     */
    private ListView<Screening> aScreeningList;

    private Showroom aShowroom;
    private final ShowroomService aShowroomService = ShowroomService.getInstance();
    private final UIService aUIService = UIService.getInstance();

    /**
     * Initializes the controller after the FXML file has been loaded.
     * Sets up input validation listeners for numeric inputs.
     */
    @FXML
    public void initialize() {
        setupInputValidation();
    }

    /**
     * Sets up input validation for the capacity text field.
     * Restricts input to numeric values only.
     */
    private void setupInputValidation() {
        aCapacityTextField.textProperty().addListener((obs, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                aCapacityTextField.setText(oldValue);
            }
        });
    }

    /**
     * Handles the save button click event.
     * Validates all inputs and creates a new showroom or updates an existing one if validation passes.
     *
     * @param pEvent the action event triggered by the save button
     */
    @FXML
    private void onSaveButtonClick(ActionEvent pEvent) {
        try {
            String name = validateAndGetShowroomName();
            if (name == null) return;

            int capacity = validateAndGetCapacity();
            if (capacity <= 0) return;

            saveShowroom(name, capacity, pEvent);
            aUIService.closeWindow(pEvent);
        }
        catch (Exception e) {
            aUIService.showErrorAlert("Save Error", "Failed to save showroom: " + e.getMessage());
        }
    }

    /**
     * Handles the add screening button click event.
     * Opens the screening dialog for creating a new screening in this showroom.
     *
     * @param pEvent the action event triggered by the add button
     */
    @FXML
    private void onAddScreeningButtonClick(ActionEvent pEvent) {
        openScreeningView(null);
    }

    /**
     * Handles the edit screening button click event.
     * Opens the screening dialog for editing the selected screening.
     * Shows an error if no screening is selected.
     *
     * @param pEvent the action event triggered by the edit button
     */
    @FXML
    private void onEditScreeningButtonClick(ActionEvent pEvent) {
        Screening selectedScreening = this.aScreeningList.getSelectionModel().getSelectedItem();
        if (selectedScreening == null) {
            aUIService.showErrorAlert("Selection Error", "Please select a screening to edit.");
            return;
        }
        openScreeningView(selectedScreening);
    }

    /**
     * Handles the remove screening button click event.
     * Removes the selected screening from the showroom's screening list.
     * Shows an error if no screening is selected.
     *
     * @param pEvent the action event triggered by the remove button
     */
    @FXML
    private void onRemoveScreeningButtonClick(ActionEvent pEvent) {
        Screening selectedScreening = this.aScreeningList.getSelectionModel().getSelectedItem();
        if (selectedScreening == null) {
            aUIService.showErrorAlert("Selection Error", "Please select a screening to remove.");
            return;
        }
        this.aScreeningList.getItems().remove(selectedScreening);
    }

    /**
     * Validates the showroom name input (must be non-empty and not exceed 100 characters).
     *
     * @return the validated showroom name, or null if validation fails
     */
    private String validateAndGetShowroomName() {
        String name = this.aShowroomNameField.getText().trim();
        if (name.isEmpty()) {
            aUIService.showErrorAlert("Validation Error", "Please enter a showroom name.");
            return null;
        }
        if (name.length() > 100) {
            aUIService.showErrorAlert("Validation Error", "Showroom name cannot exceed 100 characters.");
            return null;
        }
        return name;
    }

    /**
     * Validates the showroom capacity input (must be between 1 and 10,000).
     *
     * @return the validated capacity, or -1 if validation fails
     */
    private int validateAndGetCapacity() {
        String capacityText = aCapacityTextField.getText().trim();
        if (capacityText.isEmpty()) {
            aUIService.showErrorAlert("Validation Error", "Please enter the showroom capacity.");
            return -1;
        }
        try {
            int capacity = Integer.parseInt(capacityText);
            if (capacity <= 0) {
                aUIService.showErrorAlert("Validation Error", "Capacity must be greater than 0.");
                return -1;
            }
            if (capacity > 10000) {
                aUIService.showErrorAlert("Validation Error", "Capacity cannot exceed 10,000.");
                return -1;
            }
            return capacity;
        } catch (NumberFormatException e) {
            aUIService.showErrorAlert("Validation Error", "Capacity must be a valid integer.");
            return -1;
        }
    }

    /**
     * Saves the showroom to the service.
     * Creates a new showroom if one doesn't exist, otherwise updates the existing showroom.
     * Displays a success message upon completion.
     *
     * @param name the name of the showroom
     * @param capacity the seating capacity of the showroom
     * @param pEvent the action event (used for closing the dialog)
     */
    private void saveShowroom(String name, int capacity, ActionEvent pEvent) {
        ObservableList<Screening> screenings = aScreeningList.getItems();

        if (this.aShowroom == null) {
            this.aShowroomService.createShowroom(name, capacity, screenings);
            aUIService.showInfoAlert("Success", "Showroom created successfully.");
        } else {
            this.aShowroomService.updateShowroom(this.aShowroom, name, capacity, screenings);
            aUIService.showInfoAlert("Success", "Showroom updated successfully.");
        }
    }

    /**
     * Sets the showroom to be edited and populates the form with its data.
     * If no showroom is provided, initializes the form for creating a new showroom.
     *
     * @param pShowroom the showroom to edit, or null if creating a new showroom
     */
    public void setShowroomEditView(Showroom pShowroom) {
        this.aShowroom = pShowroom;

        if (this.aShowroom != null) {
            this.aShowroomNameField.setText(pShowroom.getShowroomName());
            this.aCapacityTextField.setText(String.valueOf(pShowroom.getShowroomCapacity()));
            this.aScreeningList.getItems().setAll(pShowroom.getShowroomScreenings());
        }
    }

    /**
     * Opens the screening add/edit dialog for creating a new screening or editing an existing one.
     *
     * @param pScreening the screening to edit, or null if creating a new screening
     */
    private void openScreeningView(Screening pScreening) {
        try {
            FXMLLoader loader = aUIService.loadFXML("add-edit-screening-view");
            Parent root = loader.getRoot();

            ScreeningAddEditViewController controller = loader.getController();
            controller.setScreeningView(this.aShowroom, pScreening);

            Stage modal = new Stage();
            modal.setScene(new Scene(root));
            modal.initModality(Modality.APPLICATION_MODAL);
            modal.setTitle(pScreening == null ? "Add Screening" : "Edit Screening");
            modal.showAndWait();

            Screening resultScreening = controller.getResultScreening();
            if (resultScreening != null) {
                handleScreeningResult(pScreening, resultScreening);
            }
        } catch (IOException e) {
            aUIService.showErrorAlert("Loading Error", "Failed to open screening dialog: " + e.getMessage());
        }
    }

    /**
     * Handles the result from the screening dialog.
     * Adds the new screening to the list if it's a new screening, or updates the existing one.
     *
     * @param originalScreening the original screening (null if creating a new one)
     * @param resultScreening the screening returned from the dialog
     */
    private void handleScreeningResult(Screening originalScreening, Screening resultScreening) {
        if (originalScreening == null) {
            this.aScreeningList.getItems().add(resultScreening);
        } else {
            int index = this.aScreeningList.getItems().indexOf(originalScreening);
            this.aScreeningList.getItems().set(index, resultScreening);
        }
    }
}