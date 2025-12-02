package com.example.theaterproject.Controllers;

import com.example.theaterproject.Models.Client;
import com.example.theaterproject.Services.AccountService;
import com.example.theaterproject.Services.UIService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.regex.Pattern;

public class NewAccountViewController {

    @FXML
    private TextField usernameField;
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField confirmPasswordField;
    @FXML
    private Button signUpButton;

    private final Pattern emailPattern = Pattern.compile("^[^@]+@[^@]+\\.[^@]+$");

    @FXML
    private void onSignUpButtonClick(ActionEvent pEvent) {
        handleSignUp(pEvent);
    }

    private void handleSignUp(ActionEvent pEvent) {
        String aUsername = usernameField.getText().trim();
        String aEmail = emailField.getText().trim();
        String aPassword = passwordField.getText().trim();
        String aConfirmPassword = confirmPasswordField.getText();

        // check if any field is empty
        if (aUsername.isEmpty() || aEmail.isEmpty() || aPassword.isEmpty() || aConfirmPassword.isEmpty()) {
            UIService.showErrorAlert("Incomplete field(s)", "Please fill in all the fields.");
            return;
        }

        // check if email is in valid format
        if (!emailPattern.matcher(aEmail).matches()) {
            UIService.showErrorAlert("Invalid Email", "Please enter a valid email address.");
            return;
        }

        // make sure passwords match
        if (!aPassword.equals(aConfirmPassword)) {
            UIService.showErrorAlert("Password Mismatch", "Passwords do not match.");
            return;
        }

        // make sure username isn't manager
        if (aUsername.equalsIgnoreCase("manager")) {
            UIService.showErrorAlert("Reserved Username", "Username cannot be " + aUsername);
            return;
        }

        // create new client account
        try {
            Client newClient = new Client(aUsername,aPassword,aEmail);

            // adding created client to AccountService list
            AccountService.getInstance().addClient(newClient);

            // clear the fields after account creation
            clearFields();

            UIService.showInfoAlert("Account Created", "Account has been successfully created.");

        } catch (IllegalArgumentException e) {
            UIService.showErrorAlert("Invalid account information", e.getMessage());
        }

        // direct to main view after creation
        try {
            UIService.openModalWindow("main-view", "Theater Dashboard", pEvent, 900, 500);
        } catch (IOException e) {
            UIService.showErrorAlert("Error", e.getMessage());
        }
    }

    private void clearFields() {
        usernameField.clear();
        emailField.clear();
        passwordField.clear();
        confirmPasswordField.clear();
    }

}
