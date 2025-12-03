package com.example.theaterproject.Controllers;

import com.example.theaterproject.Models.Movie;
import com.example.theaterproject.Models.Screening;
import com.example.theaterproject.Services.MovieService;
import com.example.theaterproject.Services.ShowroomService;
import com.example.theaterproject.Services.UIService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Controller for the Stats view.
 *
 * <p>This controller populates a list of {@link Movie} objects and, based on the selected
 * movie, displays the corresponding list of {@link Screening} objects. When a screening
 * is selected, it computes and displays basic statistics:
 * the number of tickets sold, the ticket price, and the revenue (tickets × price).</p>
 *
 * <p>Data sources:
 * <ul>
 *   <li>{@link MovieService}: provides the observable list of movies.</li>
 *   <li>{@link ShowroomService}: provides screenings for a given movie.</li>
 * </ul>
 * </p>
 *
 * <p>Navigation:
 * <ul>
 *   <li>{@link #onHomeButtonClick(ActionEvent)} navigates to the editor (home) view.</li>
 *   <li>{@link #onShowroomsButtonClick(ActionEvent)} navigates to the showrooms view.</li>
 * </ul>
 * </p>
 */
public class StatsViewController {

    /** ListView displaying available movies. Populated from {@link MovieService#getMovies()}. */
    @FXML
    private ListView<Movie> aMoviesList;

    /** ListView displaying screenings for the selected movie. Populated via {@link ShowroomService#getScreeningFor(Movie)}. */
    @FXML
    private ListView<Screening> aScreeningList;

    /** Label showing the number of tickets sold for the selected screening. */
    @FXML
    private Label aNumberTicketsLabel;

    /** Label showing the price per ticket for the selected screening. */
    @FXML
    private Label aPriceTicketsLabel;

    /** Label showing the computed revenue (tickets × price) for the selected screening. */
    @FXML
    private Label aRevenueLabel;

    /** Singleton service providing the observable list of movies. */
    private final MovieService aMovieService = MovieService.getInstance();

    /** Singleton service providing screenings for a given movie. */
    private final ShowroomService aShowroomService = ShowroomService.getInstance();
    private final UIService aUIService = UIService.getInstance();

    /**
     * Initializes the Stats view.
     *
     * <p>Responsibilities:
     * <ul>
     *   <li>Populate {@link #aMoviesList} from {@link MovieService}.</li>
     *   <li>Listen to movie selection changes and update {@link #aScreeningList} with screenings for that movie.</li>
     *   <li>Listen to screening selection changes and update the statistic labels.</li>
     *   <li>Set default label values using {@link #clearDetails()}.</li>
     * </ul>
     * </p>
     *
     * <p>This method is automatically invoked by the JavaFX framework after FXML loading.</p>
     */
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


    /**
     * Resets the statistic labels to a default placeholder value.
     *
     * <p>Called on initialization and when selections are cleared.</p>
     */
    /**
     * Clears the statistics labels by setting them to default placeholder values.
     * Used when no screening is selected or when filtering changes.
     */
    private void clearDetails() {
        aNumberTicketsLabel.setText("-");
        aPriceTicketsLabel.setText("-");
        aRevenueLabel.setText("-");
    }

    /**
     * Handles navigation to the Home (editor) view.
     *
     * <p>Loads the {@code editor-view.fxml}, opens it in a new stage, and closes the current window.</p>
     *
     * @param pEvent the action event fired by the navigation button
     */
    /**
     * Handles the "Home" button click event.
     * Navigates back to the editor (home) view.
     *
     * @param pEvent the action event triggered by the button
     */
    @FXML
    private void onHomeButtonClick(ActionEvent pEvent) {
        try {
            aUIService.openNewWindow("editor-view", "Home", pEvent);
        } catch (IOException e) {
            aUIService.showErrorAlert("Error", e.getMessage());
        }
    }

    /**
     * Handles navigation to the Showrooms view.
     *
     * <p>Loads the {@code showrooms-view.fxml}, opens it in a new stage, and closes the current window.</p>
     *
     * @param pEvent the action event fired by the navigation button
     */
    /**
     * Handles the "Showrooms" button click event.
     * Navigates to the showrooms view.
     *
     * @param pEvent the action event triggered by the button
     */
    @FXML
    private void onShowroomsButtonClick(ActionEvent pEvent) {
        try {
            aUIService.openNewWindow("showrooms-view", "Showrooms", pEvent, 900, 500);
        } catch (IOException e) {
            aUIService.showErrorAlert("Error", e.getMessage());
        }
    }
}
