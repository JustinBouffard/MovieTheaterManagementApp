package com.example.theaterproject.Controllers;

import com.example.theaterproject.Models.Movie;
import com.example.theaterproject.Models.Screening;
import com.example.theaterproject.Models.Showroom;
import com.example.theaterproject.Services.MovieService;
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
    private MovieService aMovieService = MovieService.getInstance();

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
                this.aScreening = new Screening(selectedMovie, ticketCount, pricePerTicket, date);
                this.aShowroom.addScreening(this.aScreening);
            } else {
                this.aScreening.setTicketCount(ticketCount);
                this.aScreening.setPricePerTicket(pricePerTicket);
                this.aScreening.setMovie(selectedMovie);
                this.aScreening.setDate(date);
            }

            closeWindow(pEvent);
        }
        catch (Exception e) {
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

    public void setScreeningView(Showroom pShowroom, Screening pScreening){
        this.aScreening = pScreening;
        this.aShowroom = pShowroom;
        fillView();
    }

    private void fillView(){
        this.aMovieComboBox.getItems().addAll(this.aMovieService.getMovies().toString());
        this.aMovieComboBox.getSelectionModel().select(this.aScreening.getMovie().toString());
        this.aDatePicker.setValue(this.aScreening.getDate());
        this.aPriceField.setText(String.valueOf(this.aMovieService.getaDefaultTicketPrice()));
    }

    private void closeWindow(ActionEvent pEvent) {
        Node source = (Node) pEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
}
