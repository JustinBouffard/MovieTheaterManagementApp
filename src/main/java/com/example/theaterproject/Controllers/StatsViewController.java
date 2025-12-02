package com.example.theaterproject.Controllers;

import com.example.theaterproject.Models.Movie;
import com.example.theaterproject.Models.Screening;
import com.example.theaterproject.Services.MovieService;
import com.example.theaterproject.Services.ShowroomService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;

public class StatsViewController {
    // ListView for movies and screenings
    @FXML
    private ListView<Movie> aMoviesList;
    @FXML
    private ListView<Screening> aScreeningList;

    // labels for the stats
    @FXML
    private Label aNumberTicketsLabel;
    @FXML
    private Label aPriceTicketsLabel;
    @FXML
    private Label aRevenueLabel;

    private final MovieService aMovieService = MovieService.getInstance();
    private final ShowroomService aShowroomService = ShowroomService.getInstance();

    @FXML
    private void initialize() {
        // populate movies list. Movie class toString method implicitly called.
        aMoviesList.setItems(aMovieService.getMovies());

        // when movie is selected display its screenings with ScreeningService.getScreeningFor
        aMoviesList.getSelectionModel().selectedItemProperty().addListener((obs, oldMovie, newMovie) -> {
            aScreeningList.getItems().clear();
            clearDetails();

            // populate list of screenings for newly selected movie
            if (newMovie != null) {
                aScreeningList.setItems(aShowroomService.getScreeningFor(newMovie));
            }
        });

        // display stats when screening is selected
        aScreeningList.getSelectionModel().selectedItemProperty().addListener((obs, oldScr, scr) -> {
            // clear details if no screening is selected
            if (scr == null) {
                clearDetails();
                return;
            }
            // calculate revenue for selected screening
            int sold = scr.getTicketCount();
            double price = scr.getPricePerTicket();
            double revenue = sold * price;

            // set stats labels
            aNumberTicketsLabel.setText(Integer.toString(sold));
            aPriceTicketsLabel.setText(String.format("$%.2f", price));
            aRevenueLabel.setText(String.format("$%.2f", revenue));
        });

        // default state
        clearDetails();
    }

    private void clearDetails() {
        aNumberTicketsLabel.setText("-");
        aPriceTicketsLabel.setText("-");
        aRevenueLabel.setText("-");
    }

    @FXML
    private void onHomeButtonClick(ActionEvent pEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("com/example/theaterproject/editor-view.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Home");
            stage.setScene(new Scene(root, 480, 350));
            stage.show();

            // Close current window
            Stage currentStage = (Stage) ((Node) pEvent.getSource()).getScene().getWindow();
            currentStage.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onShowroomsButtonClick(ActionEvent pEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("com/example/theaterproject/showrooms-view.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Showrooms");
            stage.setScene(new Scene(root, 480, 350));
            stage.show();

            // Close current window
            Stage currentStage = (Stage) ((Node) pEvent.getSource()).getScene().getWindow();
            currentStage.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
