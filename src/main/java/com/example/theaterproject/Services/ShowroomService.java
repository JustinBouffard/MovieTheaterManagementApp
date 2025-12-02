package com.example.theaterproject.Services;

import com.example.theaterproject.Models.Screening;
import com.example.theaterproject.Models.Showroom;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.example.theaterproject.Models.Movie;

import java.util.List;

public class ShowroomService {

    private static ShowroomService aInstance;

    private final ObservableList<Showroom> aShowrooms =
            FXCollections.observableArrayList();

    private final double aDefaultTicketPrice = 12;

    private ShowroomService() { }

    public static ShowroomService getInstance() {
        if (aInstance == null) {
            aInstance = new ShowroomService();
        }
        return aInstance;
    }

    public ObservableList<Showroom> getShowrooms() {
        return aShowrooms;
    }

    public void addShowroom(Showroom pShowroom) {
        aShowrooms.add(pShowroom);
    }

    public void removeShowroom(Showroom pShowroom) {
        aShowrooms.remove(pShowroom);
    }

    public void setShowrooms(ObservableList<Showroom> pShowrooms) {
        aShowrooms.clear();
        aShowrooms.addAll(pShowrooms);
    }

    public void createShowroom(String pName, int pCapacity, ObservableList<Screening> pScreenings) {
        Showroom showroom = new Showroom(pName, pCapacity, pScreenings);
        this.aShowrooms.add(showroom);
    }

    public void updateShowroom(Showroom pShowroom, String pName, int pCapacity, ObservableList<Screening> pScreenings) {
        pShowroom.setShowroomName(pName);
        pShowroom.setShowroomCapacity(pCapacity);
        // Instead of resetting and replacing, clear and add items to trigger listener
        ObservableList<Screening> existingScreenings = pShowroom.getShowroomScreenings();
        existingScreenings.clear();
        existingScreenings.addAll(pScreenings);
    }

    public double getaDefaultTicketPrice() {
        return aDefaultTicketPrice;
    }

    public ObservableList<Screening> getAllScreenings() {
        ObservableList<Screening> screenings = FXCollections.observableArrayList();
        for (Showroom showroom : this.aShowrooms) {
            screenings.addAll(showroom.getShowroomScreenings());
        }
        return screenings;
    }

    // filter screening list by movie. for stats view
    public ObservableList<Screening> getScreeningFor(Movie pMovie) {
        ObservableList<Screening> result = FXCollections.observableArrayList();

        // go through screening list. add to result list every screening that matches pMovie
        for (Screening s : getAllScreenings()) {
            if(s.getMovie().equals(pMovie)) {
                result.add(s);
            }
        }
        return result;
    }
}
