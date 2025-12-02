package com.example.theaterproject.Controllers;

import com.example.theaterproject.Models.Movie;
import com.example.theaterproject.Models.Screening;
import com.example.theaterproject.Models.Showroom;
import com.example.theaterproject.Services.MovieService;
import com.example.theaterproject.Services.ShowroomService;
import com.example.theaterproject.Services.UIService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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

    @FXML
    private TextField aTicketCountTextField;

    private Screening aScreening;
    private Showroom aShowroom;
    private Screening aResultScreening;
    private final MovieService aMovieService = MovieService.getInstance();
    private final ShowroomService aShowroomService = ShowroomService.getInstance();

    @FXML
    public void initialize() {
        setupInputValidationListeners();
        setupSpinnerFactories();
    }

    private void setupInputValidationListeners() {
        aPriceField.textProperty().addListener((obs, oldValue, newValue) -> {
            if (!newValue.matches("\\d*(\\.\\d{0,2})?")) {
                aPriceField.setText(oldValue);
            }
        });
        aTicketCountTextField.textProperty().addListener((obs, oldValue, newValue) -> {
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
    }

    private void setupSpinnerFactories() {
        this.aHoursSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 0));
        this.aMinutesSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0));
    }

    @FXML
    private void onSaveButtonClick(ActionEvent pEvent) {
        // Validate inputs
        Movie selectedMovie = validateAndGetMovie();
        if (selectedMovie == null) return;

        if (!validateDate()) return;
        if (!validateTicketCount()) return;
        if (!validatePrice()) return;

        try {
            createScreening(selectedMovie, pEvent);
        } catch (Exception e) {
            UIService.showErrorAlert("Unexpected Error", "An error occurred while saving the screening: " + e.getMessage());
        }
    }

    @FXML
    private void onCancelButtonClick(ActionEvent pEvent) {
        UIService.closeWindow(pEvent);
    }

    private Movie validateAndGetMovie() {
        Movie selectedMovie = aMovieComboBox.getSelectionModel().getSelectedItem();
        if (selectedMovie == null) {
            UIService.showErrorAlert("Validation Error", "Please select a movie for the screening.");
        }
        return selectedMovie;
    }

    private boolean validateDate() {
        if (this.aDatePicker.getValue() == null) {
            UIService.showErrorAlert("Validation Error", "Please select a screening date.");
            return false;
        }
        if (this.aDatePicker.getValue().isBefore(LocalDate.now())) {
            UIService.showErrorAlert("Validation Error", "Screening date cannot be in the past.");
            return false;
        }
        return true;
    }

    private boolean validateTicketCount() {
        String ticketCountText = this.aTicketCountTextField.getText().trim();
        if (ticketCountText.isEmpty()) {
            UIService.showErrorAlert("Validation Error", "Please enter the number of tickets.");
            return false;
        }
        try {
            int ticketCount = Integer.parseInt(ticketCountText);
            if (ticketCount <= 0) {
                UIService.showErrorAlert("Validation Error", "Ticket count must be greater than 0.");
                return false;
            }
            if (ticketCount > 10000) {
                UIService.showErrorAlert("Validation Error", "Ticket count cannot exceed 10,000.");
                return false;
            }
            return true;
        } catch (NumberFormatException e) {
            UIService.showErrorAlert("Validation Error", "Ticket count must be a valid integer.");
            return false;
        }
    }

    private boolean validatePrice() {
        String priceText = this.aPriceField.getText().trim();
        if (priceText.isEmpty()) {
            UIService.showErrorAlert("Validation Error", "Please enter the ticket price.");
            return false;
        }
        try {
            double pricePerTicket = Double.parseDouble(priceText);
            if (pricePerTicket < 0) {
                UIService.showErrorAlert("Validation Error", "Ticket price cannot be negative.");
                return false;
            }
            if (pricePerTicket > 9999.99) {
                UIService.showErrorAlert("Validation Error", "Ticket price cannot exceed $9,999.99.");
                return false;
            }
            return true;
        } catch (NumberFormatException e) {
            UIService.showErrorAlert("Validation Error", "Ticket price must be a valid decimal number.");
            return false;
        }
    }

    private void createScreening(Movie selectedMovie, ActionEvent pEvent) {
        int ticketCount = Integer.parseInt(this.aTicketCountTextField.getText());
        double pricePerTicket = Double.parseDouble(this.aPriceField.getText());
        LocalDateTime dateTime = LocalDateTime.of(
                this.aDatePicker.getValue(),
                LocalTime.of(this.aHoursSpinner.getValue(), this.aMinutesSpinner.getValue())
        );

        if (this.aScreening == null) {
            this.aResultScreening = new Screening(selectedMovie, ticketCount, pricePerTicket, dateTime);
        } else {
            this.aResultScreening = new Screening(selectedMovie, ticketCount, pricePerTicket, dateTime);
        }

        UIService.closeWindow(pEvent);
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
            populateExistingScreening();
        } else {
            populateNewScreeningDefaults();
        }
    }

    private void populateExistingScreening() {
        LocalDate screeningDate = this.aScreening.getDateTime().toLocalDate();
        LocalTime screeningTime = this.aScreening.getDateTime().toLocalTime();
        this.aMovieComboBox.getSelectionModel().select(this.aScreening.getMovie());
        this.aDatePicker.setValue(screeningDate);
        this.aHoursSpinner.getValueFactory().setValue(screeningTime.getHour());
        this.aMinutesSpinner.getValueFactory().setValue(screeningTime.getMinute());
        this.aPriceField.setText(String.valueOf(this.aScreening.getPricePerTicket()));
        this.aTicketCountTextField.setText(String.valueOf(this.aScreening.getTicketCount()));
    }

    private void populateNewScreeningDefaults() {
        this.aPriceField.setText(String.valueOf(this.aShowroomService.getaDefaultTicketPrice()));
        this.aTicketCountTextField.setText(String.valueOf(this.aShowroom.getShowroomCapacity()));
    }
}
