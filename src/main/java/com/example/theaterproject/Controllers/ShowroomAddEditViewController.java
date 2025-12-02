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

public class ShowroomAddEditViewController {
    @FXML
    private TextField aShowroomNameField;

    @FXML
    private TextField aCapacityTextField;

    @FXML
    private ListView<Screening> aScreeningList;

    private Showroom aShowroom;
    private final ShowroomService aShowroomService = ShowroomService.getInstance();

    @FXML
    public void initialize() {
        setupInputValidation();
    }

    private void setupInputValidation() {
        aCapacityTextField.textProperty().addListener((obs, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                aCapacityTextField.setText(oldValue);
            }
        });
    }

    @FXML
    private void onSaveButtonClick(ActionEvent pEvent) {
        try {
            String name = validateAndGetShowroomName();
            if (name == null) return;

            int capacity = validateAndGetCapacity();
            if (capacity <= 0) return;

            saveShowroom(name, capacity, pEvent);
            UIService.closeWindow(pEvent);
        }
        catch (Exception e) {
            UIService.showErrorAlert("Save Error", "Failed to save showroom: " + e.getMessage());
        }
    }

    @FXML
    private void onAddScreeningButtonClick(ActionEvent pEvent) {
        openScreeningView(null);
    }

    @FXML
    private void onEditScreeningButtonClick(ActionEvent pEvent) {
        Screening selectedScreening = this.aScreeningList.getSelectionModel().getSelectedItem();
        if (selectedScreening == null) {
            UIService.showErrorAlert("Selection Error", "Please select a screening to edit.");
            return;
        }
        openScreeningView(selectedScreening);
    }

    @FXML
    private void onRemoveScreeningButtonClick(ActionEvent pEvent) {
        Screening selectedScreening = this.aScreeningList.getSelectionModel().getSelectedItem();
        if (selectedScreening == null) {
            UIService.showErrorAlert("Selection Error", "Please select a screening to remove.");
            return;
        }
        this.aScreeningList.getItems().remove(selectedScreening);
    }

    private String validateAndGetShowroomName() {
        String name = this.aShowroomNameField.getText().trim();
        if (name.isEmpty()) {
            UIService.showErrorAlert("Validation Error", "Please enter a showroom name.");
            return null;
        }
        if (name.length() > 100) {
            UIService.showErrorAlert("Validation Error", "Showroom name cannot exceed 100 characters.");
            return null;
        }
        return name;
    }

    private int validateAndGetCapacity() {
        String capacityText = aCapacityTextField.getText().trim();
        if (capacityText.isEmpty()) {
            UIService.showErrorAlert("Validation Error", "Please enter the showroom capacity.");
            return -1;
        }
        try {
            int capacity = Integer.parseInt(capacityText);
            if (capacity <= 0) {
                UIService.showErrorAlert("Validation Error", "Capacity must be greater than 0.");
                return -1;
            }
            if (capacity > 10000) {
                UIService.showErrorAlert("Validation Error", "Capacity cannot exceed 10,000.");
                return -1;
            }
            return capacity;
        } catch (NumberFormatException e) {
            UIService.showErrorAlert("Validation Error", "Capacity must be a valid integer.");
            return -1;
        }
    }

    private void saveShowroom(String name, int capacity, ActionEvent pEvent) {
        ObservableList<Screening> screenings = aScreeningList.getItems();

        if (this.aShowroom == null) {
            this.aShowroomService.createShowroom(name, capacity, screenings);
            UIService.showInfoAlert("Success", "Showroom created successfully.");
        } else {
            this.aShowroomService.updateShowroom(this.aShowroom, name, capacity, screenings);
            UIService.showInfoAlert("Success", "Showroom updated successfully.");
        }
    }

    public void setShowroomEditView(Showroom pShowroom) {
        this.aShowroom = pShowroom;

        if (this.aShowroom != null) {
            this.aShowroomNameField.setText(pShowroom.getShowroomName());
            this.aCapacityTextField.setText(String.valueOf(pShowroom.getShowroomCapacity()));
            this.aScreeningList.getItems().setAll(pShowroom.getShowroomScreenings());
        }
    }

    private void openScreeningView(Screening pScreening) {
        try {
            FXMLLoader loader = UIService.loadFXML("add-edit-screening-view");
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
            UIService.showErrorAlert("Loading Error", "Failed to open screening dialog: " + e.getMessage());
        }
    }

    private void handleScreeningResult(Screening originalScreening, Screening resultScreening) {
        if (originalScreening == null) {
            this.aScreeningList.getItems().add(resultScreening);
        } else {
            int index = this.aScreeningList.getItems().indexOf(originalScreening);
            this.aScreeningList.getItems().set(index, resultScreening);
        }
    }
}