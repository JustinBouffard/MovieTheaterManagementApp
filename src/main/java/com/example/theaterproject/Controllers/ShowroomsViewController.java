package com.example.theaterproject.Controllers;

import com.example.theaterproject.Models.Showroom;
import com.example.theaterproject.Services.ShowroomService;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.layout.GridPane;

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

    private void fillShowroomGridPane() {
        this.aMovieGridPane.getChildren().clear();

        int columns = 3;
        int col = 0;
        int rows = 0;

        for (Showroom showroom : this.aShowroomService.getShowrooms()) {
            addShowroomCard(showroom, columns, col);

            col++;
            if (col == columns) {
                col = 0;
                rows++;
            }
        }
    }

    private void addShowroomCard(Showroom pShowroom, int pColumn, int pRow) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("showroom-card-view.fxml"));
            Parent card = loader.load();

            ShowroomCardController controller = loader.getController();
            controller.setShowroomCard(pShowroom);

            this.aMovieGridPane.add(card, pColumn, pRow);
        } catch (Exception e) {
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