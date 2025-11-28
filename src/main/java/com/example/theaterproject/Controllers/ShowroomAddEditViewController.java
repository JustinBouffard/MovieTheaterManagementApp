package com.example.theaterproject.Controllers;

import com.example.theaterproject.Models.Screening;
import com.example.theaterproject.Models.Showroom;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.io.IOException;

public class ShowroomAddEditViewController {
    @FXML
    private TextField aShowroomNameField;

    @FXML
    private TextField aCapacityTextField;

    @FXML
    private ListView<Screening> aScreeningList;

    private Showroom aShowroom;

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
        if (aShowroom == null) {
            this.aShowroom = new Showroom(this.aShowroomNameField.getText(), Integer.parseInt(this.aCapacityTextField.getText()));
        } else {
            this.aShowroom.setShowroomName(this.aShowroomNameField.getText());
            this.aShowroom.setShowroomCapacity(Integer.parseInt(this.aCapacityTextField.getText()));
        }

        this.aShowroom.resetScreenings();

        for (Screening screening : this.aScreeningList.getItems()) {
            this.aShowroom.addScreening(screening);
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
        }
        else {
            this.aShowroom.removeScreening(selectedScreening);
            this.aScreeningList.getItems().remove(selectedScreening);
        }
    }

    private void setShowroomEditView(Showroom pShowroom) {
        this.aShowroom = pShowroom;
        this.aShowroomNameField.setText(pShowroom.getShowroomName());
        this.aCapacityTextField.setText(String.valueOf(pShowroom.getShowroomCapacity()));
        this.aScreeningList.getItems().setAll(pShowroom.getShowroomScreenings());
    }

    private void openScreeningView(Screening pScreening) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("add-edit-screening-view.fxml"));
            Parent root = loader.load();

            ScreeningAddEditViewController controller = loader.getController();
            controller.setScreeningView(this.aShowroom, pScreening);

            Stage modal = new Stage();
            modal.setScene(new Scene(root));
            modal.initModality(Modality.APPLICATION_MODAL);
            modal.setTitle("Screening");
            modal.showAndWait();
        } catch (IOException e) {
            showAlert(e.getMessage());
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Something went wrong");
        alert.setContentText(message);
        alert.showAndWait();
    }
}