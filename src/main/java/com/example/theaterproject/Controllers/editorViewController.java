package com.example.theaterproject.Controllers;

import com.example.theaterproject.Models.Movie;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class editorViewController {
    @FXML
    private Button moviesViewButton;

    @FXML
    private Button showroomsViewButton;

    @FXML
    private Button statsViewButton;

    @FXML
    private Button addMovieButton;

    @FXML
    private ObservableList<Movie> aMovies;

    private void onMoviesViewButtonClick() {

    }

    private void onShowroomsViewButtonClick(ActionEvent pEvent) {
        try {
            // Load the new window
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("showroom-add-edit-view.fxml"));
            Parent root = fxmlLoader.load();

            // Create the new stage
            Stage newStage = new Stage();
            newStage.setTitle("New Account");
            newStage.setScene(new Scene(root, 480, 350));
            newStage.show();

            // ---- Close current window ----
            Stage currentStage = (Stage) ((Node) pEvent.getSource()).getScene().getWindow();
            currentStage.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void onStatsViewButtonClick(ActionEvent pEvent) {
        try {
            // Load the new window
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("stats-view.fxml"));
            Parent root = fxmlLoader.load();

            // Create the new stage
            Stage newStage = new Stage();
            newStage.setTitle("New Account");
            newStage.setScene(new Scene(root, 480, 350));
            newStage.show();

            // ---- Close current window ----
            Stage currentStage = (Stage) ((Node) pEvent.getSource()).getScene().getWindow();
            currentStage.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
