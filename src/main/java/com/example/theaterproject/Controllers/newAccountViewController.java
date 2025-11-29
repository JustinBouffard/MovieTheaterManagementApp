package com.example.theaterproject.Controllers;

import com.example.theaterproject.Models.Client;
import com.example.theaterproject.Services.AccountService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
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
            showAlert(Alert.AlertType.ERROR, "Incomplete field(s)", "Please fill in all the fields.");
            return;
        }

        // check if email is in valid format
        if (!emailPattern.matcher(aEmail).matches()) {
            showAlert(Alert.AlertType.ERROR, "Invalid Email", "Please enter a valid email address.");
            return;
        }

        // make sure passwords match
        if (!aPassword.equals(aConfirmPassword)) {
            showAlert(Alert.AlertType.ERROR, "Password Mismatch", "Passwords do not match.");
            return;
        }

        // create new client account
        try {
            Client newClient = new Client(aUsername,aPassword,aEmail);

            // adding created client to AccountService list
            AccountService.getInstance().addClient(newClient);

            // clear the fields after account creation
            clearFields();

            showAlert(Alert.AlertType.INFORMATION, "Account Created", "Account has been successfully created.");

        } catch (IllegalArgumentException e) {
            showAlert(Alert.AlertType.ERROR, "Invalid account information", e.getMessage());
        }

        // direct to main view after creation
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/example/theaterproject/main-view.fxml")
            );

            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Theater Dashboard");
            stage.setScene(new Scene(root));
            stage.show();

            // close login window
            Stage currentStage = (Stage) ((Node) pEvent.getSource()).getScene().getWindow();
            currentStage.close();

        } catch (IOException e) {
            e.printStackTrace();
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
