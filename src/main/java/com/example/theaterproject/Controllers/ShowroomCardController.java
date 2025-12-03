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

/**
 * Controller for displaying and managing individual showroom cards.
 *
 * <p>
 * This controller displays a showroom's name and information about its earliest upcoming screening.
 * It handles user interactions such as editing or deleting the showroom.
 * </p>
 */
public class ShowroomCardController {
    @FXML
    private Label aShowRoomNameLabel;

    @FXML
    private Label aMovieInfoLabel;

    private Showroom aShowRoom;
    private final ShowroomService aShowRoomService = ShowroomService.getInstance();
    private final UIService aUIService = UIService.getInstance();

    /**
     * Handles the "More" button click event.
     * Opens the showroom add/edit dialog for modifying the showroom details.
     *
     * @param pEvent the action event triggered by the button
     */
    @FXML
    private void onMoreButtonClick(ActionEvent pEvent) {
        openShowroomAddEditView();
    }

    /**
     * Handles the "Delete" button click event.
     * Removes the showroom from the system.
     *
     * @param pEvent the action event triggered by the button
     */
    @FXML
    private void onDeleteButtonClick(ActionEvent pEvent) {
        aShowRoomService.removeShowroom(aShowRoom);

    }

    /**
     * Populates the showroom card with data.
     * Displays the showroom name and information about its earliest upcoming screening.
     *
     * @param pShowroom the showroom to display
     */
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

    /**
     * Opens the showroom add/edit dialog modal for editing the current showroom.
     * Shows an error if the dialog fails to load.
     */
    private void openShowroomAddEditView() {
        if (this.aShowRoom == null) return;
        try {
            FXMLLoader loader = aUIService.loadFXML("showroom-add-edit-view");
            Parent root = loader.getRoot();
            
            ShowroomAddEditViewController controller = loader.getController();
            controller.setShowroomEditView(this.aShowRoom);

            Stage modal = new Stage();
            modal.setScene(new Scene(root));
            modal.initModality(Modality.APPLICATION_MODAL);
            modal.setTitle("Showroom Add/Edit");
            modal.showAndWait();
        } catch (IOException e) {
            aUIService.showErrorAlert("Error", "Failed to load showroom dialog: " + e.getMessage());
        }
    }
}
