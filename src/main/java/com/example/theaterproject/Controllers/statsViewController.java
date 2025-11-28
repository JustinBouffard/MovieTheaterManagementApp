package com.example.theaterproject.Controllers;

import com.example.theaterproject.Models.Movie;
import com.example.theaterproject.Models.Screening;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

public class statsViewController {
    @FXML
    private ListView<Movie> movieList;

    @FXML
    private ListView<Screening> screeningList;

    @FXML
    private Label numberTicketsLabel;

    @FXML
    private Label priceTicketsLabel;

    @FXML
    private Label revenueLabel;
}
