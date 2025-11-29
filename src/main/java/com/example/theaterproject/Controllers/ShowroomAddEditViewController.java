package com.example.theaterproject.Controllers;

import com.example.theaterproject.Models.Screening;
import com.example.theaterproject.Models.Showroom;
import com.example.theaterproject.Services.ShowroomService;
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

            closeWindow(pEvent);
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
            this.aShowroom.removeScreening(selectedScreening);
            this.aScreeningList.getItems().remove(selectedScreening);
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

    private void showAlert(String pMessage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Something went wrong");
        alert.setContentText(pMessage);
        alert.showAndWait();
    }

    private void closeWindow(javafx.event.ActionEvent pEvent) {
        Node source = (Node) pEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
}