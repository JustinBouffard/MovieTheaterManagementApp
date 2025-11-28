package com.example.theaterproject.Controllers;

import com.example.theaterproject.Models.Client;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.util.regex.Pattern;

public class newAccountViewController {

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

    private final Pattern emailPattern = Pattern.compile("^[^@]+@[^@]+.[^@]+$");

    @FXML
    public void initialize() {
        // sign up event handler
        signUpButton.setOnAction(event -> handleSignUp());
    }

    private void handleSignUp() {
        String aUsername = usernameField.getText().trim();
        String aEmail = emailField.getText().trim();
        String aPassword = passwordField.getText().trim();
        String aConfirmPassword = confirmPasswordField.getText();

        // check if any field is empty
        if (aUsername.isEmpty() || aEmail.isEmpty() || aPassword.isEmpty() || aConfirmPassword.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Incomplete field(s)", "Please fill in all the fields.");
            return;
        }

        // check if email is in valid format
        if (!emailPattern.matcher(aEmail).matches()) {
            showAlert(Alert.AlertType.ERROR, "Invalid Email", "Please enter a valid email address.");
        }

        // make sure passwords match
        if (!aPassword.equals(aConfirmPassword)) {
            showAlert(Alert.AlertType.ERROR, "Password Mismatch", "Passwords do not match.");
        }

        // create new client account
        try {
            Client newClient = new Client(aUsername,aPassword,aEmail);

            // TODO need a way to save clients in a list

            // clear the fields after account creation
            clearFields();

            showAlert(Alert.AlertType.INFORMATION, "Account Created", "Account has been successfully created.");
        } catch (IllegalArgumentException e) {
            showAlert(Alert.AlertType.ERROR, "Invalid account information", e.getMessage());
        }
    }

    private void clearFields() {
        usernameField.clear();
        emailField.clear();
        passwordField.clear();
        confirmPasswordField.clear();
    }

    private void showAlert(Alert.AlertType type,String pTitle, String pMessage) {
        Alert alert = new Alert(type);
        alert.setTitle(pTitle);
        alert.setHeaderText(null);
        alert.setContentText(pMessage);
        alert.showAndWait();
    }

}
