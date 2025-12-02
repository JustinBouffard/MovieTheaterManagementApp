package com.example.theaterproject.Controllers;

import com.example.theaterproject.Models.Screening;
import com.example.theaterproject.Models.Showroom;
import com.example.theaterproject.Services.ShowroomService;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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
        openWindow("main-view", pEvent);
    }

    @FXML
    private void onStatsViewButtonClick(ActionEvent pEvent) {
        openWindow("stats-view", pEvent);
    }

    @FXML
    private void onAddButtonClick(ActionEvent pEvent) {
        System.out.println("Add Button clicked");
        openShowroomAddEditView(null);
    }

    private void openShowroomAddEditView(Showroom pShowroom) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/theaterproject/showroom-add-edit-view.fxml"));
            Parent root = loader.load();

            ShowroomAddEditViewController controller = loader.getController();
            controller.setShowroomEditView(pShowroom);

            Stage modal = new Stage();
            modal.setScene(new Scene(root));
            modal.initModality(Modality.APPLICATION_MODAL);
            modal.setTitle("Showroom Add/Edit");
            modal.showAndWait();
        } catch (IOException e) {
            showAlert(e.getMessage());
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/theaterproject/showroom-card-view.fxml"));
            Parent card = loader.load();

            ShowroomCardController controller = loader.getController();
            controller.setShowroomCard(pShowroom);

            // Listen for changes in screenings so card updates when screenings change
            pShowroom.getShowroomScreenings().addListener((ListChangeListener<Screening>) change -> {
                controller.setShowroomCard(pShowroom);
            });

            this.aMovieGridPane.add(card, pColumn, pRow);
        } catch (Exception e) {
            showAlert(e.getMessage());
        }
    }

    private void openWindow(String pName, ActionEvent pEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/theaterproject/" + pName + ".fxml"));
            Parent root = loader.load();

            Stage newStage = new Stage();
            newStage.setTitle(pName);
            newStage.setScene(new Scene(root, 480, 350));
            newStage.show();

            closeWindow(pEvent);
        } catch (IOException e) {
            showAlert(e.getMessage());
        }
    }

    private void closeWindow(ActionEvent pEvent) {
        Node source = (Node) pEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    private void showAlert(String pMessage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Something went wrong");
        alert.setContentText(pMessage);
        alert.showAndWait();
    }

}