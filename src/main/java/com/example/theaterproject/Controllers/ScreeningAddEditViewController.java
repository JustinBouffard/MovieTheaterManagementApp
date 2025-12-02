package com.example.theaterproject.Controllers;

import com.example.theaterproject.Models.Movie;
import com.example.theaterproject.Models.Screening;
import com.example.theaterproject.Models.Showroom;
import com.example.theaterproject.Services.MovieService;
import com.example.theaterproject.Services.ShowroomService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class ScreeningAddEditViewController {
    @FXML
    private ComboBox<Movie> aMovieComboBox;

    @FXML
    private DatePicker aDatePicker;

    @FXML
    private Spinner<Integer> aHoursSpinner;

    @FXML
    private Spinner<Integer> aMinutesSpinner;

    @FXML
    private TextField aPriceField;

    private Screening aScreening;
    private Screening aOriginalScreening;
    private Showroom aShowroom;
    private Screening aResultScreening;
    private final MovieService aMovieService = MovieService.getInstance();
    private final ShowroomService aShowroomService = ShowroomService.getInstance();

    @FXML
    public void initialize() {
        aPriceField.textProperty().addListener((obs, oldValue, newValue) -> {
            if (!newValue.matches("\\d*(\\.\\d{0,2})?")) {
                aPriceField.setText(oldValue);
            }
        });
        aHoursSpinner.getEditor().textProperty().addListener((obs, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                aHoursSpinner.getEditor().setText(oldValue);
            } else if (!newValue.isEmpty()) {
                int value = Integer.parseInt(newValue);
                if (value > 23) {
                    aHoursSpinner.getEditor().setText(oldValue);
                }
            }
        });

        aMinutesSpinner.getEditor().textProperty().addListener((obs, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                aMinutesSpinner.getEditor().setText(oldValue);
            } else if (!newValue.isEmpty()) {
                int value = Integer.parseInt(newValue);
                if (value > 59) {
                    aMinutesSpinner.getEditor().setText(oldValue);
                }
            }
        });
        this.aHoursSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 0));
        this.aMinutesSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0));
    }

    @FXML
    private void onSaveButtonClick(ActionEvent pEvent) {
        try {
            Movie selectedMovie = aMovieComboBox.getSelectionModel().getSelectedItem();
            if (selectedMovie == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Invalid selection");
                alert.setContentText("Please select a movie");
                alert.showAndWait();
                return;
            }
            int ticketCount = this.aShowroom.getShowroomCapacity();
            double pricePerTicket = Double.parseDouble(this.aPriceField.getText());
            LocalDateTime dateTime = LocalDateTime.of(
                    this.aDatePicker.getValue(),
                    LocalTime.of(this.aHoursSpinner.getValue(), this.aMinutesSpinner.getValue())
            );

            if (this.aScreening == null) {
                // Creating a new screening - don't persist yet
                this.aResultScreening = new Screening(selectedMovie, ticketCount, pricePerTicket, dateTime);
            } else {
                // Editing existing screening - create a modified copy, don't modify the original yet
                this.aResultScreening = new Screening(selectedMovie, ticketCount, pricePerTicket, dateTime);
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

    public Screening getResultScreening() {
        return this.aResultScreening;
    }

    private void fillView() {
        this.aMovieComboBox.getItems().addAll(this.aMovieService.getMovies());

        if (this.aScreening != null) {
            LocalDate screeningDate = this.aScreening.getDateTime().toLocalDate();
            LocalTime screeningTime = this.aScreening.getDateTime().toLocalTime();
            this.aMovieComboBox.getSelectionModel().select(this.aScreening.getMovie());
            this.aDatePicker.setValue(screeningDate);
            this.aHoursSpinner.getValueFactory().setValue(screeningTime.getHour());
            this.aMinutesSpinner.getValueFactory().setValue(screeningTime.getMinute());
            this.aPriceField.setText(String.valueOf(this.aScreening.getPricePerTicket()));
        }
        else
            this.aPriceField.setText(String.valueOf(this.aShowroomService.getaDefaultTicketPrice()));
    }

    private void closeWindow(ActionEvent pEvent) {
        Node source = (Node) pEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
}
