package com.example.theaterproject.Controllers;

import com.example.theaterproject.Models.Screening;
import com.example.theaterproject.Models.Showroom;
import com.example.theaterproject.Services.ShowroomService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
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
    private ObservableList<Screening> aOriginalScreenings;
    private final ShowroomService aShowroomService = ShowroomService.getInstance();

    @FXML
    public void initialize() {
        aCapacityTextField.textProperty().addListener((obs, oldValue, newValue) -> {
            if (!newValue.matches("\\d*(\\.\\d{0,2})?")) {
                aCapacityTextField.setText(oldValue);
            }
        });
    }

    @FXML
    private void onSaveButtonClick(ActionEvent pEvent) {
        try {
            String name = this.aShowroomNameField.getText();
            int capacity = Integer.parseInt(aCapacityTextField.getText());
            ObservableList<Screening> screenings = aScreeningList.getItems();

            if (this.aShowroom == null)
                this.aShowroomService.createShowroom(name, capacity, screenings);
            else
                this.aShowroomService.updateShowroom(this.aShowroom, name, capacity, screenings);

            closeWindow(pEvent, true);
        }
        catch (Exception e) {
            showAlert(e.getMessage());
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
            showAlert("You need to select a screening first");
            return;
        } else {
            openScreeningView(selectedScreening);
        }
    }

    @FXML
    private void onRemoveScreeningButtonClick(ActionEvent pEvent) {
        Screening selectedScreening = this.aScreeningList.getSelectionModel().getSelectedItem();
        if (selectedScreening == null) {
            showAlert("You need to select a screening first");
            return;
        } else {
            this.aScreeningList.getItems().remove(selectedScreening);
        }
    }

    public void setShowroomEditView(Showroom pShowroom) {
        this.aShowroom = pShowroom;

        if (this.aShowroom != null) {
            this.aShowroomNameField.setText(pShowroom.getShowroomName());
            this.aCapacityTextField.setText(String.valueOf(pShowroom.getShowroomCapacity()));
            // Store a copy of the original screenings for potential rollback
            this.aOriginalScreenings = FXCollections.observableArrayList(pShowroom.getShowroomScreenings());
            this.aScreeningList.getItems().setAll(pShowroom.getShowroomScreenings());
        }
    }

    private void openScreeningView(Screening pScreening) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/theaterproject/add-edit-screening-view.fxml"));
            Parent root = loader.load();

            ScreeningAddEditViewController controller = loader.getController();
            controller.setScreeningView(this.aShowroom, pScreening);

            Stage modal = new Stage();
            modal.setScene(new Scene(root));
            modal.initModality(Modality.APPLICATION_MODAL);
            modal.setTitle("Screening");
            modal.showAndWait();
            
            // Get the result screening from the controller
            Screening resultScreening = controller.getResultScreening();
            if (resultScreening != null) {
                if (pScreening == null) {
                    // If this is a new screening, add it to the list
                    this.aScreeningList.getItems().add(resultScreening);
                } else {
                    // If editing, replace the old screening with the new one in the ListView
                    int index = this.aScreeningList.getItems().indexOf(pScreening);
                    this.aScreeningList.getItems().set(index, resultScreening);
                }
            }
        } catch (IOException e) {
            showAlert(e.getMessage());
        }
    }

    private void showAlert(String pMessage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Something went wrong");
        alert.setContentText(pMessage);
        alert.showAndWait();
    }

    private void closeWindow(ActionEvent pEvent) {
        closeWindow(pEvent, false);
    }

    private void closeWindow(ActionEvent pEvent, boolean pSaved) {
        // If editing an existing showroom and user clicked cancel (not save), rollback screening changes
        if (!pSaved && this.aShowroom != null && this.aOriginalScreenings != null) {
            this.aShowroom.getShowroomScreenings().setAll(this.aOriginalScreenings);
        }
        
        Node source = (Node) pEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
}