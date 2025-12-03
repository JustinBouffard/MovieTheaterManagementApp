package com.example.theaterproject.Services;

import com.example.theaterproject.Models.Screening;
import com.example.theaterproject.Models.Showroom;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.example.theaterproject.Models.Movie;

import java.util.List;

/**
 * Service class for managing showrooms and their screenings in the theater system.
 *
 * <p>
 * This singleton service maintains an observable list of showrooms and provides
 * methods for managing showroom CRUD operations and screening management.
 * It also handles retrieving all screenings across all showrooms and filtering
 * screenings by movie.
 * </p>
 */
public class ShowroomService {

    private static ShowroomService aInstance;

    private final ObservableList<Showroom> aShowrooms =
            FXCollections.observableArrayList();

    private final double aDefaultTicketPrice = 12;

    private ShowroomService() { }

    /**
     * Returns the singleton instance of ShowroomService, creating it if necessary.
     *
     * @return the singleton ShowroomService instance
     */
    public static ShowroomService getInstance() {
        if (aInstance == null) {
            aInstance = new ShowroomService();
        }
        return aInstance;
    }

    /**
     * Retrieves the observable list of all showrooms.
     *
     * @return an ObservableList containing all showrooms
     */
    public ObservableList<Showroom> getShowrooms() {
        return aShowrooms;
    }

    /**
     * Adds a new showroom to the showroom collection.
     *
     * @param pShowroom the showroom to add; cannot be null
     */
    public void addShowroom(Showroom pShowroom) {
        aShowrooms.add(pShowroom);
    }

    /**
     * Removes a showroom from the showroom collection.
     *
     * @param pShowroom the showroom to remove
     */
    public void removeShowroom(Showroom pShowroom) {
        aShowrooms.remove(pShowroom);
    }

    /**
     * Replaces the current showroom collection with a new collection.
     *
     * @param pShowrooms the new collection of showrooms to set
     */
    public void setShowrooms(ObservableList<Showroom> pShowrooms) {
        aShowrooms.clear();
        aShowrooms.addAll(pShowrooms);
    }

    /**
     * Creates and adds a new showroom with the specified details.
     *
     * @param pName       the name of the showroom
     * @param pCapacity   the seating capacity of the showroom
     * @param pScreenings the initial list of screenings for the showroom
     */
    public void createShowroom(String pName, int pCapacity, ObservableList<Screening> pScreenings) {
        Showroom showroom = new Showroom(pName, pCapacity, pScreenings);
        this.aShowrooms.add(showroom);
    }

    /**
     * Updates an existing showroom with new details.
     *
     * @param pShowroom   the showroom to update
     * @param pName       the new name for the showroom
     * @param pCapacity   the new seating capacity for the showroom
     * @param pScreenings the new list of screenings for the showroom
     */
    public void updateShowroom(Showroom pShowroom, String pName, int pCapacity, ObservableList<Screening> pScreenings) {
        pShowroom.setShowroomName(pName);
        pShowroom.setShowroomCapacity(pCapacity);
        // Instead of resetting and replacing, clear and add items to trigger listener
        ObservableList<Screening> existingScreenings = pShowroom.getShowroomScreenings();
        existingScreenings.clear();
        existingScreenings.addAll(pScreenings);
    }

    /**
     * Retrieves the default ticket price for screenings.
     *
     * @return the default ticket price
     */
    public double getaDefaultTicketPrice() {
        return aDefaultTicketPrice;
    }

    /**
     * Retrieves all screenings across all showrooms.
     *
     * @return an ObservableList containing all screenings from all showrooms
     */
    public ObservableList<Screening> getAllScreenings() {
        ObservableList<Screening> screenings = FXCollections.observableArrayList();
        for (Showroom showroom : this.aShowrooms) {
            screenings.addAll(showroom.getShowroomScreenings());
        }
        return screenings;
    }

    /**
     * Retrieves all screenings for a specific movie across all showrooms.
     *
     * <p>
     * Filters the complete list of screenings to return only those that match
     * the provided movie by title comparison.
     * </p>
     *
     * @param pMovie the movie to filter screenings by
     * @return an ObservableList containing all screenings of the specified movie
     */
    public ObservableList<Screening> getScreeningFor(Movie pMovie) {
        ObservableList<Screening> result = FXCollections.observableArrayList();

        // go through screening list. add to result list every screening that matches pMovie
        if (pMovie == null) {
            return result;
        }
        String title = pMovie.getTitle();
        for (Screening s : getAllScreenings()) {
            Movie m = s.getMovie();
            if (m != null && m.getTitle() != null && m.getTitle().equals(title)) {
                result.add(s);
            }
        }
        return result;
    }
}
