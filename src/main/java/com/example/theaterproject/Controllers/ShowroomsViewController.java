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
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class ShowroomsViewController {

    @FXML
    private GridPane aMovieGridPane;

    private final ShowroomService aShowroomService = ShowroomService.getInstance();

    @FXML
    public void initialize() {
        fillShowroomGridPane();

        this.aShowroomService.getShowrooms().addListener((ListChangeListener<Showroom>) change -> {
            fillShowroomGridPane();
        });
    }

    @FXML
    private void onMoviesViewButtonClick(ActionEvent pEvent) {
        try {
            UIService.openModalWindow("main-view", "Movies", pEvent);
        } catch (IOException e) {
            UIService.showErrorAlert("Error", e.getMessage());
        }
    }

    @FXML
    private void onStatsViewButtonClick(ActionEvent pEvent) {
        try {
            UIService.openModalWindow("stats-view", "Statistics", pEvent);
        } catch (IOException e) {
            UIService.showErrorAlert("Error", e.getMessage());
        }
    }

    @FXML
    private void onAddButtonClick(ActionEvent pEvent) {
        System.out.println("Add Button clicked");
        openShowroomAddEditView(null);
    }

    private void openShowroomAddEditView(Showroom pShowroom) {
        try {
            FXMLLoader loader = UIService.loadFXML("showroom-add-edit-view");
            Parent root = loader.getRoot();

            ShowroomAddEditViewController controller = loader.getController();
            controller.setShowroomEditView(pShowroom);

            Stage modal = new Stage();
            modal.setScene(new Scene(root));
            modal.initModality(Modality.APPLICATION_MODAL);
            modal.setTitle("Showroom Add/Edit");
            modal.showAndWait();
        } catch (IOException e) {
            UIService.showErrorAlert("Error", e.getMessage());
        }
    }

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

    private void addShowroomCard(Showroom pShowroom, int pColumn, int pRow) {
        try {
            FXMLLoader loader = UIService.loadFXML("showroom-card-view");
            Parent card = loader.getRoot();

            ShowroomCardController controller = loader.getController();
            controller.setShowroomCard(pShowroom);

            // Listen for changes in screenings so card updates when screenings change
            pShowroom.getShowroomScreenings().addListener((ListChangeListener<Screening>) change -> {
                controller.setShowroomCard(pShowroom);
            });

            this.aMovieGridPane.add(card, pColumn, pRow);
        } catch (Exception e) {
            UIService.showErrorAlert("Error", e.getMessage());
        }
    }

}