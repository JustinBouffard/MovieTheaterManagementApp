package com.example.theaterproject.Controllers;

import com.example.theaterproject.Models.Account;
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
import javafx.stage.Stage;
import javafx.scene.control.TextField;

import java.io.IOException;

public class LoginViewController {
    @FXML
    private Button signInButton;

    @FXML
    private Button newAccountButton;

    @FXML
    private TextField usernameTextField;

    @FXML
    private PasswordField passwordField;

    AccountService accountService = AccountService.getInstance();

    @FXML
    private void onSignInButtonClick(ActionEvent pEvent) {
        String username = usernameTextField.getText();
        String password = passwordField.getText();

        try {
            // Example: Authenticate user
            Account managerAccount = accountService.getManager();

            String fxmlPath;

            if (managerAccount.getUserName().equals(username) && managerAccount.getPassword().equals(password)) {
                fxmlPath = "/com/example/theaterproject/editor-view.fxml";
                System.out.println("Manager login detected. Loading editor view...");
            } else {
                fxmlPath = "/com/example/theaterproject/main-view.fxml";
                System.out.println("Regular login detected. Loading main view...");
            }

            // Debug: check if resource exists
            if (getClass().getResource(fxmlPath) == null) {
                System.err.println("FXML file not found: " + fxmlPath);
                return;
            }

            // Load FXML safely
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Theater Dashboard");
            stage.setScene(new Scene(root));
            stage.show();

            // Close current login window
            Stage currentStage = (Stage) ((Node) pEvent.getSource()).getScene().getWindow();
            currentStage.close();

        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Failed to load the view");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }



    @FXML
    private void onNewAccountButtonClick(ActionEvent pEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/theaterproject/new-account-view.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("New Account");
            stage.setScene(new Scene(root, 480, 350));
            stage.show();

            // Close current window
            Stage currentStage = (Stage) ((Node) pEvent.getSource()).getScene().getWindow();
            currentStage.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
