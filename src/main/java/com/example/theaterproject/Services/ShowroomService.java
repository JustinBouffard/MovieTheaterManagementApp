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
        return FXCollections.unmodifiableObservableList(aShowrooms);
    }

    public void addShowroom(Showroom pShowroom) {
        aShowrooms.add(pShowroom);
    }

    public void removeShowroom(Showroom pShowroom) {
        aShowrooms.remove(pShowroom);
    }

    public void createShowroom(String pName, int pCapacity, ObservableList<Screening> pScreenings) {
        Showroom showroom = new Showroom(pName, pCapacity, pScreenings);
        this.aShowrooms.add(showroom);
    }

    public void updateShowroom(Showroom pShowroom, String pName, int pCapacity, ObservableList<Screening> pScreenings) {
        pShowroom.setShowroomName(pName);
        pShowroom.setShowroomCapacity(pCapacity);
        pShowroom.resetScreenings();
        pShowroom.setShowroomScreenings(pScreenings);
    }

    public double getaDefaultTicketPrice() {
        return aDefaultTicketPrice;
    }
}
