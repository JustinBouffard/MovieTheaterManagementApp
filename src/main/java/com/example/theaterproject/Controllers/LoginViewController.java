package com.example.theaterproject.Controllers;

import com.example.theaterproject.Models.Account;
import com.example.theaterproject.Services.AccountService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
            Account managerAccount = accountService.getManager();

            if (managerAccount.getUserName().equals(username) && managerAccount.getPassword().equals(password)) {
                FXMLLoader loader = new FXMLLoader(
                        getClass().getResource("/com/example/theaterproject/editor-view.fxml")
                );

                Parent root = loader.load();

                Stage stage = new Stage();
                stage.setTitle("Theater Dashboard");
                stage.setScene(new Scene(root));
                stage.show();

                // close login window
                Stage currentStage = (Stage) ((Node) pEvent.getSource()).getScene().getWindow();
                currentStage.close();
            } else {
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

            }


        } catch (IOException e) {
            e.printStackTrace();
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
