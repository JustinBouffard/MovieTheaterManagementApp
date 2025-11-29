package com.example.theaterproject.Controllers;

import com.example.theaterproject.Models.Showroom;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.io.IOException;

public class ShowroomCardController {
    @FXML
    private Label aShowRoomNameLabel;

    @FXML
    private Label aMovieInfoLabel;

    private Showroom aShowRoom;

    @FXML
    private void onMoreButtonClick(ActionEvent pEvent) {
        openShowroomAddEditView();
    }

    public void setShowroomCard(Showroom pShowroom) {
        this.aShowRoom = pShowroom;
    }

    private void openShowroomAddEditView() {
        if (this.aShowRoom == null) return;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("showroom-add-edit-view.fxml"));
            Parent root = loader.load();

            ShowroomAddEditViewController controller = loader.getController();
            controller.setShowroomEditView(this.aShowRoom);

            Stage modal = new Stage();
            modal.setScene(new Scene(root));
            modal.initModality(Modality.APPLICATION_MODAL);
            modal.setTitle("Showroom Add/Edit");
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
}
