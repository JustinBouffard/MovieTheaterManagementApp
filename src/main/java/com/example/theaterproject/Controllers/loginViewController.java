package com.example.theaterproject.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;

public class loginViewController {
    @FXML
    private Button signInButton;

    @FXML
    private Button newAccountButton;

    @FXML
    private TextField usernameTextField;

    @FXML
    private PasswordField passwordField;

    private void onSignInButtonClick() {

    }

    private void onNewAccountButtonClick(ActionEvent pEvent) {
        try {
            // Load the new window
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("new-account-view.fxml"));
            Parent root = fxmlLoader.load();

            // Create the new stage
            Stage newStage = new Stage();
            newStage.setTitle("New Account");
            newStage.setScene(new Scene(root, 480, 350));
            newStage.show();

            // ---- Close current window ----
            Stage currentStage = (Stage) ((Node) pEvent.getSource()).getScene().getWindow();
            currentStage.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
