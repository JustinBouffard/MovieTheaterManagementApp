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
        ObservableList<Showroom> showrooms = DummyDataHelper.getDummyShowrooms();
        ObservableList<Client> clients = DummyDataHelper.getDummyClients();

        movieService.setMovies(movies);
        showroomService.setShowrooms(showrooms);
        accountService.setClients(new java.util.ArrayList<>(clients));

        FXMLLoader fxmlLoader = new FXMLLoader(TheaterApplication.class.getResource("login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 300);
        stage.setTitle("Theatre");
        stage.setScene(scene);
        stage.show();
    }

    public AccountService getAccountService() {
        return accountService;
    }
}
