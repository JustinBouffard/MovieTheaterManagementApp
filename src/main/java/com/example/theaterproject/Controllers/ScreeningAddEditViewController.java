package com.example.theaterproject.Controllers;

import com.example.theaterproject.Models.Movie;
import com.example.theaterproject.Models.Screening;
import com.example.theaterproject.Models.Showroom;
import com.example.theaterproject.Services.MovieService;
import com.example.theaterproject.Services.ScreeningService;
import com.example.theaterproject.Services.ShowroomService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.LocalDate;

public class ScreeningAddEditViewController {
    @FXML
    private ComboBox<String> aMovieComboBox;

    @FXML
    private DatePicker aDatePicker;

    @FXML
    private TextField aPriceField;

    private Screening aScreening;
    private Showroom aShowroom;
    private final MovieService aMovieService = MovieService.getInstance();
    private final ShowroomService aShowroomService = ShowroomService.getInstance();
    private final ScreeningService aScreeningService = ScreeningService.getInstance();

    @FXML
    public void initialize() {
        aPriceField.textProperty().addListener((obs, oldValue, newValue) -> {
            if (!newValue.matches("\\d*(\\.\\d{0,2})?")) {
                aPriceField.setText(oldValue);
            }
        });
    }

    @FXML
    private void onSaveButtonClick(ActionEvent pEvent) {
        try {
            Movie selectedMovie = this.aMovieService.getMovies().get(aMovieComboBox.getSelectionModel().getSelectedIndex());
            int ticketCount = this.aShowroom.getShowroomCapacity();
            double pricePerTicket = Double.parseDouble(this.aPriceField.getText());
            LocalDate date = this.aDatePicker.getValue();

            if (this.aScreening == null) {
                this.aScreeningService.createScreening(this.aShowroom, selectedMovie, ticketCount, pricePerTicket, date);
            } else {
                this.aScreeningService.updateScreening(this.aScreening, selectedMovie, ticketCount, pricePerTicket, date);
            }

            closeWindow(pEvent);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Something went wrong");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    private void onCancelButtonClick(ActionEvent pEvent) {
        closeWindow(pEvent);
    }

    public void setScreeningView(Showroom pShowroom, Screening pScreening) {
        this.aScreening = pScreening;
        this.aShowroom = pShowroom;
        fillView();
    }

    private void fillView() {
        this.aMovieComboBox.getItems().addAll(this.aMovieService.getMovies().toString());

        if (this.aScreening != null) {
            this.aMovieComboBox.getSelectionModel().select(this.aScreening.getMovie().toString());
            this.aDatePicker.setValue(this.aScreening.getDate());
            this.aPriceField.setText(String.valueOf(this.aShowroomService.getaDefaultTicketPrice()));
        }
    }

    private void closeWindow(ActionEvent pEvent) {
        Node source = (Node) pEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
}
