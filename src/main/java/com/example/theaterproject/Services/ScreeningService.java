package com.example.theaterproject.Services;

import com.example.theaterproject.Models.Screening;
import com.example.theaterproject.Models.Showroom;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.example.theaterproject.Models.Movie;

import java.time.LocalDate;
import java.util.Collection;

public class ScreeningService {

    private static ScreeningService aInstance;

    private ScreeningService() {
    }

    public static ScreeningService getInstance() {
        if (aInstance == null) {
            aInstance = new ScreeningService();
        }
        return aInstance;
    }

    public void createScreening(Showroom pShowroom, Movie pMovie, int pTicketCount, double pPricePerTicket, LocalDate pDate) {
        Screening screening = new Screening(pMovie, pTicketCount, pPricePerTicket, pDate);
        pShowroom.addScreening(screening);
    }

    public void updateScreening(Screening pScreening, Movie pMovie, int pTicketCount, double pPricePerTicket, LocalDate pDate) {
        pScreening.setMovie(pMovie);
        pScreening.setTicketCount(pTicketCount);
        pScreening.setPricePerTicket(pPricePerTicket);
        pScreening.setDate(pDate);
    }
}
