package com.example.theaterproject.Controllers;

import com.example.theaterproject.Models.Account;
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
                FXMLLoader loader = UIService.loadFXML("editor-view");
                Parent root = loader.getRoot();

                Stage stage = new Stage();
                stage.setTitle("Theater Dashboard");
                stage.setScene(new Scene(root));
                stage.show();

                // close login window
                Stage currentStage = (Stage) ((javafx.scene.Node) pEvent.getSource()).getScene().getWindow();
                currentStage.close();
            } else {
                FXMLLoader loader = UIService.loadFXML("main-view");
                Parent root = loader.getRoot();

                Stage stage = new Stage();
                stage.setTitle("Theater Dashboard");
                stage.setScene(new Scene(root, 700, 400));
                stage.show();

                // close login window
                Stage currentStage = (Stage) ((javafx.scene.Node) pEvent.getSource()).getScene().getWindow();
                currentStage.close();

            }


        } catch (IOException e) {
            UIService.showErrorAlert("Error", e.getMessage());
        }
    }

    @FXML
    private void onNewAccountButtonClick(ActionEvent pEvent) {
        try {
            UIService.openModalWindow("new-account-view", "New Account", pEvent);
        } catch (IOException e) {
            UIService.showErrorAlert("Error", e.getMessage());
        }

    }
}
