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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Controller for the screening add/edit dialog view.
 *
 * <p>
 * Manages creation and editing of screenings for a given showroom.
 * Provides input validation for date, time, ticket count, and price,
 * and returns the resulting {@code Screening} to the caller.
 * </p>
 */
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
    private final UIService aUIService = UIService.getInstance();

    /**
     * Initializes the controller after FXML loading.
     * Sets up validation listeners and spinner value factories.
     */
    @FXML
    public void initialize() {
        setupInputValidationListeners();
        setupSpinnerFactories();
    }

    /**
     * Registers input validation listeners for price, ticket count, and time spinners.
     * Ensures numeric formats and bounds are respected in text fields and spinner editors.
     */
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

    /**
     * Configures spinner factories for hours (0-23) and minutes (0-59).
     */
    private void setupSpinnerFactories() {
        this.aHoursSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 0));
        this.aMinutesSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0));
    }

    /**
     * Handles the save button click.
     * Validates inputs and creates the result screening if all checks pass.
     *
     * @param pEvent the action event from the save button
     */
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
            aUIService.showErrorAlert("Unexpected Error", "An error occurred while saving the screening: " + e.getMessage());
        }
    }

    /**
     * Handles the cancel action by closing the dialog window.
     *
     * @param pEvent the action event from the cancel button
     */
    @FXML
    private void onCancelButtonClick(ActionEvent pEvent) {
        aUIService.closeWindow(pEvent);
    }

    /**
     * Validates and retrieves the selected movie for the screening.
     * Shows an alert if no movie is selected.
     *
     * @return the selected {@code Movie}, or {@code null} if invalid
     */
    private Movie validateAndGetMovie() {
        Movie selectedMovie = aMovieComboBox.getSelectionModel().getSelectedItem();
        if (selectedMovie == null) {
            aUIService.showErrorAlert("Validation Error", "Please select a movie for the screening.");
        }
        return selectedMovie;
    }

    /**
     * Validates the screening date.
     * Ensures a date is selected and is not in the past.
     *
     * @return {@code true} if valid; otherwise {@code false}
     */
    private boolean validateDate() {
        if (this.aDatePicker.getValue() == null) {
            aUIService.showErrorAlert("Validation Error", "Please select a screening date.");
            return false;
        }
        if (this.aDatePicker.getValue().isBefore(LocalDate.now())) {
            aUIService.showErrorAlert("Validation Error", "Screening date cannot be in the past.");
            return false;
        }
        return true;
    }

    /**
     * Validates the ticket count input.
     * Ensures the value is a positive integer within reasonable bounds.
     *
     * @return {@code true} if valid; otherwise {@code false}
     */
    private boolean validateTicketCount() {
        String ticketCountText = this.aTicketCountTextField.getText().trim();
        if (ticketCountText.isEmpty()) {
            aUIService.showErrorAlert("Validation Error", "Please enter the number of tickets.");
            return false;
        }
        try {
            int ticketCount = Integer.parseInt(ticketCountText);
            if (ticketCount <= 0) {
                aUIService.showErrorAlert("Validation Error", "Ticket count must be greater than 0.");
                return false;
            }
            if (ticketCount > 10000) {
                aUIService.showErrorAlert("Validation Error", "Ticket count cannot exceed 10,000.");
                return false;
            }
            return true;
        } catch (NumberFormatException e) {
            aUIService.showErrorAlert("Validation Error", "Ticket count must be a valid integer.");
            return false;
        }
    }

    /**
     * Validates the ticket price input.
     * Ensures the value is a non-negative decimal under a set maximum.
     *
     * @return {@code true} if valid; otherwise {@code false}
     */
    private boolean validatePrice() {
        String priceText = this.aPriceField.getText().trim();
        if (priceText.isEmpty()) {
            aUIService.showErrorAlert("Validation Error", "Please enter the ticket price.");
            return false;
        }
        try {
            double pricePerTicket = Double.parseDouble(priceText);
            if (pricePerTicket < 0) {
                aUIService.showErrorAlert("Validation Error", "Ticket price cannot be negative.");
                return false;
            }
            if (pricePerTicket > 9999.99) {
                aUIService.showErrorAlert("Validation Error", "Ticket price cannot exceed $9,999.99.");
                return false;
            }
            return true;
        } catch (NumberFormatException e) {
            aUIService.showErrorAlert("Validation Error", "Ticket price must be a valid decimal number.");
            return false;
        }
    }

    /**
     * Creates the resulting {@code Screening} from validated inputs and stores it.
     * Closes the dialog on success.
     *
     * @param selectedMovie the validated selected movie
     * @param pEvent the action event used to close the window
     */
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

        aUIService.closeWindow(pEvent);
    }

    /**
     * Sets up the view for creating or editing a screening in the given showroom.
     * Populates UI controls based on the current context.
     *
     * @param pShowroom the showroom associated with the screening
     * @param pScreening the screening to edit; {@code null} for a new screening
     */
    public void setScreeningView(Showroom pShowroom, Screening pScreening) {
        this.aScreening = pScreening;
        this.aShowroom = pShowroom;
        fillView();
    }

    /**
     * Retrieves the screening created or edited by this dialog.
     *
     * @return the resulting {@code Screening}, or {@code null} if none
     */
    public Screening getResultScreening() {
        return this.aResultScreening;
    }

    /**
     * Fills the view controls with movies and default or existing screening data.
     */
    private void fillView() {
        this.aMovieComboBox.getItems().addAll(this.aMovieService.getMovies());

        if (this.aScreening != null) {
            populateExistingScreening();
        } else {
            populateNewScreeningDefaults();
        }
    }

    /**
     * Populates controls with the values from an existing screening being edited.
     */
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

    /**
     * Populates default values for a new screening based on showroom and service settings.
     */
    private void populateNewScreeningDefaults() {
        this.aPriceField.setText(String.valueOf(this.aShowroomService.getaDefaultTicketPrice()));
        // If editing an existing showroom, use its capacity; otherwise use a default value
        if (this.aShowroom != null) {
            this.aTicketCountTextField.setText(String.valueOf(this.aShowroom.getShowroomCapacity()));
        } else {
            this.aTicketCountTextField.setText("100"); // Default ticket count for new screenings
        }
    }
}
