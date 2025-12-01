package com.example.theaterproject.Services;

import com.example.theaterproject.Models.Screening;
import com.example.theaterproject.Models.Showroom;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.example.theaterproject.Models.Movie;
import java.time.LocalDateTime;

public class ScreeningService {

    private static ScreeningService aInstance;

    // list of screenings
    private final ObservableList<Screening> aAllScreenings = FXCollections.observableArrayList();

    private ScreeningService() {
    }

    public static ScreeningService getInstance() {
        if (aInstance == null) {
            aInstance = new ScreeningService();
        }
        return aInstance;
    }

    public void createScreening(Showroom pShowroom, Movie pMovie, int pTicketCount, double pPricePerTicket, LocalDateTime pDateTime) {
        Screening screening = new Screening(pMovie, pTicketCount, pPricePerTicket, pDateTime);
        pShowroom.addScreening(screening);
        // add to screenings list
        aAllScreenings.add(screening);
    }

    public void updateScreening(Screening pScreening, Movie pMovie, int pTicketCount, double pPricePerTicket, LocalDateTime pDateTime) {
        pScreening.setMovie(pMovie);
        pScreening.setTicketCount(pTicketCount);
        pScreening.setPricePerTicket(pPricePerTicket);
        pScreening.setDateTime(pDateTime);
    }

    // filter screening list by movie. for stats view
    public ObservableList<Screening> getScreeningFor(Movie pMovie) {
        ObservableList<Screening> result = FXCollections.observableArrayList();

        // go through screening list. add to result list every screening that matches pMovie
        for (Screening s : aAllScreenings) {
            if(s.getMovie().equals(pMovie)) {
                result.add(s);
            }
        }
        return result;
    }
}
