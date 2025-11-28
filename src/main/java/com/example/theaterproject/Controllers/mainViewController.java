package com.example.theaterproject.Controllers;

import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class mainViewController {
    @FXML
    private Button moviesViewButton;

    @FXML
    private Button showroomsViewButton;

    @FXML
    private Button statsViewButton;

    public void initialize() {

    }

    private void moviesViewButtonClick() {
        System.out.println("Movies View Button Clicked");
    }

    private void showroomsViewButtonClick() {
        System.out.println("Showrooms View Button Clicked");
    }

    private void statsViewButtonClick() {
        System.out.println("Stats View Button Clicked");
    }
}
