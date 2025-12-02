package com.example.theaterproject.Controllers;

import com.example.theaterproject.Models.Screening;
import com.example.theaterproject.Models.Showroom;
import com.example.theaterproject.Services.ShowroomService;
import com.example.theaterproject.Services.UIService;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ShowroomCardController {
    @FXML
    private Label aShowRoomNameLabel;

    @FXML
    private Label aMovieInfoLabel;

    private Showroom aShowRoom;
    private final ShowroomService aShowRoomService = ShowroomService.getInstance();

    @FXML
    private void onMoreButtonClick(ActionEvent pEvent) {
        openShowroomAddEditView();
    }

    @FXML
    private void onDeleteButtonClick(ActionEvent pEvent) {
        aShowRoomService.removeShowroom(aShowRoom);

    }

    public void setShowroomCard(Showroom pShowroom) {
        ObservableList<Screening> screenings = pShowroom.getShowroomScreenings();
        Screening earliestScreening = screenings.stream()
                .min((s1, s2) -> s1.getDateTime().compareTo(s2.getDateTime()))
                .orElse(null);

        this.aShowRoom = pShowroom;
        this.aShowRoomNameLabel.setText(pShowroom.getShowroomName());
        
        if (earliestScreening != null) {
            String formattedTime = earliestScreening.getDateTime().format(DateTimeFormatter.ofPattern("HH:mm"));
            this.aMovieInfoLabel.setText(earliestScreening.getMovie().getTitle() + " showing at " + formattedTime);
        } else {
            this.aMovieInfoLabel.setText("(No screenings available)");
        }
    }

    private void openShowroomAddEditView() {
        if (this.aShowRoom == null) return;
        try {
            FXMLLoader loader = UIService.loadFXML("showroom-add-edit-view");
            Parent root = loader.getRoot();

            ShowroomAddEditViewController controller = loader.getController();
            controller.setShowroomEditView(this.aShowRoom);

            Stage modal = new Stage();
            modal.setScene(new Scene(root));
            modal.initModality(Modality.APPLICATION_MODAL);
            modal.setTitle("Showroom Add/Edit");
            modal.showAndWait();
        } catch (IOException e) {
            UIService.showErrorAlert("Error", e.getMessage());
        }
    }
}
