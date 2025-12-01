package com.example.theaterproject;

import com.example.theaterproject.Helpers.DummyDataHelper;
import com.example.theaterproject.Models.*;
import com.example.theaterproject.Services.AccountService;
import com.example.theaterproject.Services.MovieService;
import com.example.theaterproject.Services.ShowroomService;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class TheaterApplication extends Application {
    private final MovieService movieService = MovieService.getInstance();
    private final AccountService accountService = AccountService.getInstance();
    private final ShowroomService showroomService = ShowroomService.getInstance();

    @Override
    public void start(Stage stage) throws IOException {
        ObservableList<Movie> movies = DummyDataHelper.getDummyMovies();
        List<Account> accounts = DummyDataHelper.getDummyAccounts();
        List<Client> clients = DummyDataHelper.getDummyClients();
        ObservableList<Showroom> showrooms = DummyDataHelper.getDummyShowrooms();
        List<Ticket> tickets = DummyDataHelper.getDummyTickets();

        movieService.setMovies(movies);
        showroomService.setShowrooms(showrooms);

        FXMLLoader fxmlLoader = new FXMLLoader(TheaterApplication.class.getResource("login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 400);
        stage.setTitle("Theatre");
        stage.setScene(scene);
        stage.show();
    }
}
